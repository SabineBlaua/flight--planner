package io.codelex.flight_planner.flight;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface FlightService {

    Flight addFlight(AddFlightRequest request);

    public void clearFlights();

    public Optional<Flight> getFlightById(Integer id);
    public void deleteFlightById(Integer id);

    PageResult<Flight> searchFlights(String from, String to, LocalDate departureDate);

    void compareAirports(AddFlightRequest request);

    void validateAddFlightRequest(AddFlightRequest request);

    void validateSearchRequest(SearchFlightsRequest request);


}
