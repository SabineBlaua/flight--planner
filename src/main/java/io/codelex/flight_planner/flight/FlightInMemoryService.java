package io.codelex.flight_planner.flight;

import io.codelex.flight_planner.airport.AirportRepository;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


public class FlightInMemoryService implements FlightService{
    private final FlightInMemoryRepository flightRepository;
    private final AirportRepository airportRepository;
    private final Lock lock = new ReentrantLock();

    public FlightInMemoryService(FlightInMemoryRepository flightRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.airportRepository = airportRepository;
    }

    public Flight addFlight(AddFlightRequest request) {
        lock.lock();
        try {
            validateAddFlightRequest(request);

            Flight flight = new Flight(
                    flightRepository.getNewId(),
                    request.getFrom(),
                    request.getTo(),
                    request.getCarrier(),
                    request.getDepartureTime(),
                    request.getArrivalTime()
            );

            if (flightRepository.containsFlight(flight)) {
                throw new FlightAlreadyExistsException("Flight already exists");
            }

            Flight savedFlight = flightRepository.add(flight);

            airportRepository.addAirport(flight.getFrom());
            airportRepository.addAirport(flight.getTo());

            return savedFlight;
        } finally {
            lock.unlock();
        }
    }


    public void clearFlights() {
        lock.lock();
        try {
            flightRepository.clearFlights();
        } finally {
            lock.unlock();
        }
    }

    public Optional<Flight> getFlightById(Integer id) {
        lock.lock();
        try {
            return flightRepository.findFlightById(id);
        } finally {
            lock.unlock();
        }
    }

    public void deleteFlightById(Integer id) {
        lock.lock();
        try {
            flightRepository.deleteFlightById(id);
        } finally {
            lock.unlock();
        }
    }

    public PageResult<Flight> searchFlights(String from, String to, LocalDate departureDate) {
        lock.lock();
        try {
            if (from.equalsIgnoreCase(to)) {
                throw new IllegalArgumentException("Departure and destination airports cannot be the same");
            }

            List<Flight> matchingFlights = flightRepository.getAllFlights().stream()
                    .filter(flight -> flight.getFrom().getAirport().equalsIgnoreCase(from))
                    .filter(flight -> flight.getTo().getAirport().equalsIgnoreCase(to))
                    .filter(flight -> flight.getDepartureTime().toLocalDate().equals(departureDate))
                    .collect(Collectors.toList());

            return new PageResult<>(0, matchingFlights.size(), matchingFlights);
        } finally {
            lock.unlock();
        }
    }

    public void compareAirports(AddFlightRequest request) {
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
