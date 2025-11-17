package ch.hearc.ig.business;

import lombok.*;
import lombok.extern.java.Log;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@Log
@NoArgsConstructor //obligée de le mettre pour Jakarta
//j'ai retiré le builder parce que ça marchait plus (je suppose à cause de le FK, merci 🙃

@Entity
@Table(name = "weathers")

public class Weather implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weather_seq")
    @SequenceGenerator(name = "weather_seq", sequenceName = "seq_weathers", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "measure_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    @Column(name = "temp", nullable = false)
    private Double temp;

    @Column(name = "pressure", nullable = false)
    private int pressure;

    @Column(name = "humidity", nullable = false)
    private int humidity;

    @Column(name = "wind_speed")
    private Double windSpeed;

    @Column(name = "wind_direction")
    private int windDirection;

    @Column(name = "visibility")
    private int visibility;

    @Column(name = "cloudiness")
    private int cloudiness;

    @Column(name = "rain")
    private double rain;

    //ajouté pour gérer la FK qui va à weather stations
    @ManyToOne
    @JoinColumn(name = "fk_ws", nullable = false)
    private WeatherStation weatherStation;


    public Weather(WeatherStation ws,Date dt, String description, Double temp, int pressure, int humidity, Double windSpeed, int windDirection, int visibility, int cloudiness, double rain) {
        this.weatherStation = ws;
        this.dt = dt;
        this.description = description;
        this.temp = temp;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.visibility = visibility;
        this.cloudiness = cloudiness;
        this.rain = rain;
    }

    public void addRain(Weather weather, double rain ) {
        this.rain = rain;
    }

    @Override
    public String toString() {
        return String.format("Le %s : %s, %.2f°C, %d hPa, %d%% humidité", dt.toString(), description, temp, pressure, humidity);
    }

}


