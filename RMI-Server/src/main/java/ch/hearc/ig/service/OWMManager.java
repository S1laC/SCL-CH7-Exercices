package ch.hearc.ig.service;

import ch.hearc.ig.business.Country;
import ch.hearc.ig.business.Weather;
import ch.hearc.ig.business.WeatherStation;
import ch.hearc.ig.tools.Env;
import ch.hearc.ig.tools.HibernateUtil;
import ch.hearc.ig.tools.Log;
import ch.hearc.ig.tools.Request;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OWMManager {

    private Session session;

    public OWMManager() {
        this.session = HibernateUtil.getSessionFactory().openSession();
        Log.info("Hibernate session is open: " + session.isOpen());
    }
    public WeatherStation call(Double latitude, Double longitude) throws Exception {
        try {
            // construire les paramètres
            Map<String, String> params = new HashMap<>();
            params.put("appid", Env.get("OPENWEATHERMAP_APIKEY"));
            params.put("lat", String.valueOf(latitude));
            params.put("lon", String.valueOf(longitude));
            params.put("units", "metric");

            //construire l'appel et appeler l'API
            Request openWeatherMapRequest = new Request("https://api.openweathermap.org/data/2.5/weather?", params);
            String jsonResponse = openWeatherMapRequest.call();
            //creation du mapper pour la station météo a partir de la réponse
            OWMMapper mapper = new OWMMapper(jsonResponse);
            // récupérer la station de OWM, créée le mapper
            WeatherStation retrievedStation = mapper.create();
            // Récupérer le pays
            Country country = this.loadOrCreateCountry(mapper.getCountryCode());
            retrievedStation.setCountry(country);
            // Définir si on travail avec la station reçue ou si elle existe deja en DB
            WeatherStation currentStation = this.loadOrCreate(retrievedStation);
            // ajouter le dernier relevé météo à la fin de la liste
            currentStation.getWeather().add(mapper.getWeather(currentStation));

            // persister dans la DB
            this.saveWeathers(currentStation);

            return currentStation;
        } catch (Exception e) {
            throw e;
        }
    }
    public List<WeatherStation> getAllWeatherStations() {
        return (List<WeatherStation>) this.session.createQuery(
                        "from WeatherStation order by name", WeatherStation.class)
                .getResultList();
    }
    private WeatherStation loadOrCreate(WeatherStation station) {
        // on cherche si la station météo existe deja dans la DB par l'id fourni par OWM
        WeatherStation loadedStation = (WeatherStation) this.session.createQuery(
                "from WeatherStation where idOWM = :owmID", WeatherStation.class)
                .setParameter("owmID", station.getIdOWM())
                .uniqueResult(); // c'est une colonne UNIQUE
        if (loadedStation == null) {
            session.beginTransaction();
            session.persist(station);
            session.getTransaction().commit();
            return station;
        } else {
            return loadedStation;
        }
    }
    private void saveWeathers(WeatherStation station) {
        session.beginTransaction();
        for (Weather weather : station.getWeather()) {
            session.persist(weather);// persister chaque relevé météo, ne crée pas de doublons
        }
        session.getTransaction().commit();
    }
    public void shutdown() {
        session.close();
        HibernateUtil.shutdown();
    }
    private Country loadOrCreateCountry(String countryCode) {
        // on cherche si le pays existe deja dans la DB
        Country loadedCountry = (Country) this.session.createQuery(
                        "from Country where countryCode = :code", Country.class)
                .setParameter("code", countryCode.toLowerCase())
                .uniqueResult(); // c'est une colonne UNIQUE
        if (loadedCountry == null) {
            Request countryRequest = new Request("https://db.ig.he-arc.ch/ens/scl/ws/country/" + countryCode);
            String jsonResponse = countryRequest.call();
            CountryMapper countryMapper = new CountryMapper(jsonResponse);
            loadedCountry = countryMapper.create();
            session.beginTransaction();
            session.persist(loadedCountry);
            session.getTransaction().commit();
        }
        return loadedCountry;
    }
}
