package io.codelex.flight_planner.flight;

import io.codelex.flight_planner.flight.exceptions.BadRequestException;
import io.codelex.flight_planner.flight.exceptions.FlightAlreadyExistsException;
import io.codelex.flight_planner.flight.exceptions.FlightNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
public class FlightController {
    private final FlightService flightService;
    private final FlightValidationService flightValidationService;

    @Autowired
    public FlightController(FlightService flightService, FlightValidationService flightValidationService) {
        this.flightService = flightService;
        this.flightValidationService = flightValidationService;
    }

    @PostMapping("/testing-api/clear")
    public void clearFlights() {
        flightService.clearFlights();
    }

    @PutMapping("/admin-api/flights")
    @ResponseStatus(HttpStatus.CREATED)
    public Flight addFlight(@RequestBody AddFlightRequest request) {
        flightValidationService.validateAirports(request);
        flightValidationService.validateAddFlightRequest(request);
        return flightService.addFlight(request);
    }

    @GetMapping("/admin-api/flights/{id}")
    public Flight fetchFlight(@PathVariable Integer id) {
        return flightService.getFlightById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
    }

    @DeleteMapping("/admin-api/flights/{id}")
    public void deleteFlight(@PathVariable Integer id) {
        flightService.deleteFlightById(id);
    }

    @PostMapping("/api/flights/search")
    @ResponseStatus(HttpStatus.OK)
    public PageResult<Flight> searchFlights(@RequestBody SearchFlightsRequest request) {
        flightValidationService.validateSearchRequest(request);
        return flightService.searchFlights(request.getFrom(), request.getTo(), request.getDepartureDate());
    }

    @GetMapping("/api/flights/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Flight findFlightById(@PathVariable Integer id) {
        return flightService.getFlightById(id)
                .orElseThrow(() -> new FlightNotFoundException("Flight not found"));
    }

    @ExceptionHandler(FlightAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleFlightAlreadyExistsException(FlightAlreadyExistsException e) {
        return e.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }

    @ExceptionHandler(FlightNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleFlightNotFoundException(FlightNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequestException(BadRequestException e) {
        return e.getMessage();
    }
}
