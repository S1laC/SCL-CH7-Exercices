package ch.hearc.heg.scl.rmiObj;

import java.util.List;

public interface IOpenWeather {
    void setLatitude(double latitude);
    void setLongitude(double longitude);
    String getWeather();
    List<String> getStations();
}
