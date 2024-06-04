package io.codelex.flight_planner.flight;

import io.codelex.flight_planner.airport.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;


public class FlightDbService implements FlightService{
    private final FlightDbRepository flightDbRepository;
    private final AirportRepository airportRepository;

    @Autowired
    public FlightDbService(FlightDbRepository flightDbRepository, AirportRepository airportRepository) {
        this.flightDbRepository = flightDbRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public Flight addFlight(AddFlightRequest request) {
        return null;
    }

    @Override
    public void clearFlights() {

    }

    @Override
    public Optional<Flight> getFlightById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void deleteFlightById(Integer id) {

    }

    @Override
    public PageResult<Flight> searchFlights(String from, String to, LocalDate departureDate) {
        return null;
    }

    @Override
    public void compareAirports(AddFlightRequest request) {

    }

    @Override
    public void validateAddFlightRequest(AddFlightRequest request) {

    }

    @Override
    public void validateSearchRequest(SearchFlightsRequest request) {

    }
}
