package ch.hearc.ig.business;

import lombok.*;
import lombok.extern.java.Log;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "weather_stations")

@Log
public class WeatherStation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ça c'est la PK, pas l'ID de OWM !! Pas touche on garde les deux 🩷

    @Column(name = "owm_id", nullable = false)
    private int idOWM;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @ManyToOne
    @JoinColumn(name = "fk_country", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "weatherStation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Weather> weather;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    public WeatherStation(int id, String name, Country country, Double longitude, Double latitude) {
        this(id, name, longitude, latitude);
        this.country = country;
    }
    public WeatherStation(int id, String name, Double longitude, Double latitude) {
        this.idOWM = id;
        this.name = name;
        this.weather = new ArrayList<Weather>();
        this.longitude = longitude;
        this.latitude = latitude;
    }
}