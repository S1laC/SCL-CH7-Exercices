package ch.hearc.heg.scl.rmiObj;

import java.util.List;

public class OpenWeather implements IOpenWeather{
    private double latitude;
    private double longitude;

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public String getWeather() {
        return "";
    }
    @Override
    public List<String> getStations() {
        return List.of();
    }
}
