package io.codelex.flight_planner.airport;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AirportService {

    private final AirportRepository airportRepository;

    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> searchAirports(String search) {
        return airportRepository.searchAirports(search);
    }

}
