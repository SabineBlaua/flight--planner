package io.codelex.flight_planner.airport;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "AIRPORT")
public class Airport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "airport", nullable = false, unique = true)
    private String airport;

    public Airport(String country, String city, String code) {
        this.country = country;
        this.city = city;
        this.airport = code;
    }

    public Airport() {

    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAirport() {
        return airport;
    }

    public void setAirport(String code) {
        this.airport = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airport airport = (Airport) o;
        return Objects.equals(country, airport.country) && Objects.equals(city, airport.city) && Objects.equals(airport.getAirport(), airport.airport);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, city, airport);
    }
}
