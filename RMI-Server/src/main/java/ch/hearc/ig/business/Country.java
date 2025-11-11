package ch.hearc.ig.business;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.java.Log;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Log

@Entity
@Table(name = "countries",
        uniqueConstraints={@UniqueConstraint(columnNames={"id"})})
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false) //un peu redondant avec la partie uniqueConstraints
    private Long id; //je suis obligée de faire ça pour matcher l'entité jakarta, sorry 😔

    @Column(name = "code", length = 2, unique = false, nullable = false)
    @JsonProperty("code")
    private String countryCode;

    @Column(name = "name", length = 100,unique = false, nullable = false)
    @JsonProperty("name")
    private String countryName;
    //private List<WeatherStation> cities;



    public Country(String countryCode) {
        this.countryCode = countryCode;
    }

    //j'ai du rajouter ce constructeur vu qu'on a crée un ID en plus, le allargs il marchait plus
    public Country(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }
}




