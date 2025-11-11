package ch.hearc.ig.service;

import ch.hearc.ig.business.Weather;
import ch.hearc.ig.business.WeatherStation;
import ch.hearc.ig.tools.Log;

import java.util.Date;

public class OWMMapper extends AbstractMapper<WeatherStation> {

    public OWMMapper(String jsonAsStr) {
        super(jsonAsStr);
        this.createdAt = new Date(root.get("dt").asLong() * 1000);
    }

    public WeatherStation create() throws NoDataFoundException {
        if (root.get("name").asText() == "") {
            throw new NoDataFoundException("Il n'existe pas de station météo à cet endroit");
        }
        try {
        WeatherStation ws = new WeatherStation(root.get("id").asInt(),
                                            root.get("name").asText(),
                                            root.get("coord").get("lon").asDouble(),
                                            root.get("coord").get("lat").asDouble());
        return ws;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Weather getWeather(WeatherStation ws){
        Weather retrievedWeather = null;
        double rain;
        if (ws.getIdOWM() != root.get("id").asInt()){
            Log.warn("La mesure reçue ne correspond pas à la station météo fournie.");
        }
        try {
            if (root.has("rain") && root.get("rain").has("1h")){
                rain = root.get("rain").get("1h").asDouble();
            } else {
                rain = 0.0;
            }
            retrievedWeather = new Weather(
                    ws,
                    this.createdAt,
                    root.get("weather").get(0).get("description").asText(),
                    root.get("main").get("temp").asDouble(),
                    root.get("main").get("pressure").asInt(),
                    root.get("main").get("humidity").asInt(),
                    root.get("wind").get("speed").asDouble(),
                    root.get("wind").get("deg").asInt(),
                    root.get("visibility").asInt(),
                    root.get("clouds").get("all").asInt(),
                    rain
                    );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return retrievedWeather;
    }
    public String getCountryCode(){
        return root.get("sys").get("country").asText();
    }
}
