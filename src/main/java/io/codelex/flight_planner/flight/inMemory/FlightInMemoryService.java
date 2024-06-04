package io.codelex.flight_planner.flight.inMemory;

import io.codelex.flight_planner.airport.AirportRepository;
import io.codelex.flight_planner.flight.AddFlightRequest;
import io.codelex.flight_planner.flight.Flight;
import io.codelex.flight_planner.flight.FlightService;
import io.codelex.flight_planner.flight.PageResult;
import io.codelex.flight_planner.flight.exceptions.FlightAlreadyExistsException;
import io.codelex.flight_planner.flight.inMemory.FlightInMemoryRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;


public class FlightInMemoryService implements FlightService {
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

}
