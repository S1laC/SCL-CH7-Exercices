package ch.hearc.heg.scl.rmiObj;

import java.util.List;

public interface IOpenWeather {
    void setLatitude(Double latitude);
    void setLongitude(Double longitude);
    String getWeather();
    List<String> getStations();
}
