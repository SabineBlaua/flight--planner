package io.codelex.flight_planner.flight;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FlightValidationService {

    public void validateAirports(AddFlightRequest request) {
        String fromCode = request.getFrom().getAirport().trim().toLowerCase();
        String toCode = request.getTo().getAirport().trim().toLowerCase();
        if (fromCode.equals(toCode)) {
            throw new IllegalArgumentException("Departure and destination airports cannot be the same");
        }
    }

    public void validateAddFlightRequest(AddFlightRequest request) {
        if (request == null || request.getFrom() == null || request.getTo() == null || request.getCarrier() == null
                || request.getDepartureTime() == null || request.getArrivalTime() == null) {
            throw new IllegalArgumentException("From, to, carrier, departure time, and arrival time cannot be null");
        }

        if (request.getFrom().getCountry() == null || request.getFrom().getCity() == null || request.getFrom().getAirport() == null
                || request.getTo().getCountry() == null || request.getTo().getCity() == null || request.getTo().getAirport() == null) {
            throw new IllegalArgumentException("Airport details (country, city, airport) cannot be null");
        }

        if (request.getFrom().getCountry().isEmpty() || request.getFrom().getCity().isEmpty() || request.getFrom().getAirport().isEmpty()
                || request.getTo().getCountry().isEmpty() || request.getTo().getCity().isEmpty() || request.getTo().getAirport().isEmpty()) {
            throw new IllegalArgumentException("Airport details (country, city, airport) cannot be empty");
        }

        if (!StringUtils.hasText(request.getCarrier())) {
            throw new IllegalArgumentException("Carrier must be specified");
        }

        if (request.getDepartureTime() == null || request.getArrivalTime() == null) {
            throw new IllegalArgumentException("Departure and arrival times must be specified");
        }

        if (request.getFrom().equals(request.getTo())) {
            throw new IllegalArgumentException("Departure and arrival airports cannot be the same");
        }

        if (!request.getArrivalTime().isAfter(request.getDepartureTime())) {
            throw new IllegalArgumentException("Arrival time must be after departure time");
        }
    }

    public void validateSearchRequest(SearchFlightsRequest request) {
        if (request.getFrom() == null || request.getTo() == null || request.getDepartureDate() == null) {
            throw new IllegalArgumentException("Invalid search request");
        }
    }
}
