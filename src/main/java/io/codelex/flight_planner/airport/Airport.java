package io.codelex.flight_planner.airport;

import java.util.Objects;

public class Airport {
    private String country;
    private String city;
    private String airport;

    public Airport(String country, String city, String code) {
        this.country = country;
        this.city = city;
        this.airport = code;
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
