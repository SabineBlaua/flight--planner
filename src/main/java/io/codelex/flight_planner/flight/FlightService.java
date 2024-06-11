package io.codelex.flight_planner.flight;

import java.time.LocalDate;
import java.util.Optional;

public interface FlightService {

    Flight addFlight(AddFlightRequest request);

    void clearFlights();

    Optional<Flight> getFlightById(Integer id);
    void deleteFlightById(Integer id);

    PageResult<Flight> searchFlights(String from, String to, LocalDate departureDate);

}
