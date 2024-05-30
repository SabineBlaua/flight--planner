package io.codelex.flight_planner.flight;

import io.codelex.flight_planner.airport.Airport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AddFlightRequest {

    private final Airport from;
    private final Airport to;
    private final String carrier;
    private final LocalDateTime departureTime;
    private final LocalDateTime arrivalTime;

    public AddFlightRequest(Airport from, Airport to, String carrier, String departureTime, String arrivalTime) {
        this.from = from;
        this.to = to;
        this.carrier = carrier;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.departureTime = LocalDateTime.parse(departureTime, formatter);
        this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter);
    }

    public Airport getFrom() {
        return from;
    }

    public Airport getTo() {
        return to;
    }

    public String getCarrier() {
        return carrier;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public Flight requestToFlight(Integer newId) {
        return new Flight(newId, from, to, carrier, departureTime, arrivalTime);
    }
}