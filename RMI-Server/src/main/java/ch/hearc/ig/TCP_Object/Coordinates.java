package ch.hearc.ig.TCP_Object;

import java.io.Serializable;

class Coordinates implements Serializable {
    private Double latitude;
    private Double longitude;

    public Coordinates(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "latitude='" + latitude + "'," +
                "longitude='" + longitude + '\'' +
                '}';
    }
}