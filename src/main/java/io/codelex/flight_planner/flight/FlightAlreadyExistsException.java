package io.codelex.flight_planner.flight;

public class FlightAlreadyExistsException extends RuntimeException {
    public FlightAlreadyExistsException(String message) {
        super(message);
    }
}
