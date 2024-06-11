package io.codelex.flight_planner.airport;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class AirportRepository {

    Set<Airport> airportList = new HashSet<>();

    public Set<Airport> getAirportList() {
        return airportList;
    }

    public void addAirport(Airport airport) {
        airportList.add(airport);
    }

    public List<Airport> searchAirports(String search) {
        String searchTerm = search.trim().toLowerCase();
        return airportList.stream()
                .filter(airport -> airport.getAirport().toLowerCase().contains(searchTerm)
                        || airport.getCity().toLowerCase().contains(searchTerm)
                        || airport.getCountry().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }
}