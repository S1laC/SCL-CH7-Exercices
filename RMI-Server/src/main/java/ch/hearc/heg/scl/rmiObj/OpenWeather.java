package ch.hearc.heg.scl.rmiObj;

import ch.hearc.ig.business.WeatherStation;
import ch.hearc.ig.service.OWMManager;

import java.util.List;

public class OpenWeather implements IOpenWeather{
    private Double latitude;
    private Double longitude;
    private static OWMManager manager;

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getWeather() {
        String response;
        if (this.latitude == null || this.longitude == null){
            response = "Vous devez d'abord définir des coordonnées GPS pour obtenir des données météo";
        } else {
            if (this.manager == null) {
                manager = new OWMManager();
            }
            try {
                WeatherStation station = manager.call(this.latitude, this.longitude);
                // ... créer un String unique avec toutes les données que l'on veut retourner
                // response = "gnagnagna" + station.getGnagnagna + ...
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }
    @Override
    public List<String> getStations() {
        // Retourner les stations, soit une liste des noms ou une map avec les ids... whatever
        return List.of();
    }
}
