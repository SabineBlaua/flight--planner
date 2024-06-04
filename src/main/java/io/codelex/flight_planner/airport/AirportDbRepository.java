package io.codelex.flight_planner.airport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AirportDbRepository extends JpaRepository<Airport, Integer> {

    List<Airport> findByCountryContainingIgnoreCaseOrCityContainingIgnoreCaseOrAirportContainingIgnoreCase(String country, String city, String airport);

    default List<Airport> searchAirports(String search) {
        String searchTerm = search.trim().toLowerCase();
        return findByCountryContainingIgnoreCaseOrCityContainingIgnoreCaseOrAirportContainingIgnoreCase(searchTerm, searchTerm, searchTerm);
    }
}
