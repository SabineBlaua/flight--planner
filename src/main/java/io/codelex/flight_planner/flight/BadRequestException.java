package io.codelex.flight_planner.flight;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}