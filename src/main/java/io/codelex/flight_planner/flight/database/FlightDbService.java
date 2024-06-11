package io.codelex.flight_planner.flight.database;

import io.codelex.flight_planner.airport.Airport;
import io.codelex.flight_planner.airport.AirportDbRepository;
import io.codelex.flight_planner.flight.AddFlightRequest;
import io.codelex.flight_planner.flight.Flight;
import io.codelex.flight_planner.flight.FlightService;
import io.codelex.flight_planner.flight.PageResult;
import io.codelex.flight_planner.flight.exceptions.FlightAlreadyExistsException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FlightDbService implements FlightService {
    private final FlightDbRepository flightDbRepository;
    private final AirportDbRepository airportDbRepository;
    private final Lock lock = new ReentrantLock();

    public FlightDbService(FlightDbRepository flightDbRepository, AirportDbRepository airportDbRepository) {
        this.flightDbRepository = flightDbRepository;
        this.airportDbRepository = airportDbRepository;
    }

    @Override
    @Transactional
    public Flight addFlight(AddFlightRequest request) {
        lock.lock();
        try {
            Flight flight = new Flight(
                    null,
                    request.getFrom(),
                    request.getTo(),
                    request.getCarrier(),
                    request.getDepartureTime(),
                    request.getArrivalTime()
            );

            airportDbRepository.save(flight.getFrom());
            airportDbRepository.save(flight.getTo());

            List<Flight> existingFlights = flightDbRepository.findByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(
                    flight.getFrom(),
                    flight.getTo(),
                    flight.getCarrier(),
                    flight.getDepartureTime(),
                    flight.getArrivalTime()
            );


            if (!existingFlights.isEmpty()) {
                throw new FlightAlreadyExistsException("Flight already exists");
            }

            return flightDbRepository.save(flight);

        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clearFlights() {
        flightDbRepository.deleteAll();
    }

    @Override
    public Optional<Flight> getFlightById(Integer id) {
        return flightDbRepository.findById(id);
    }

    @Override
    public void deleteFlightById(Integer id) {
        flightDbRepository.deleteById(id);
    }

    @Override
    public PageResult<Flight> searchFlights(String from, String to, LocalDate departureDate) {
        List<Airport> fromAirports = airportDbRepository.searchAirports(from);
        List<Airport> toAirports = airportDbRepository.searchAirports(to);

        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.atTime(LocalTime.MAX);

        List<Flight> matchingFlights = flightDbRepository.findByFromInAndToInAndDepartureTimeBetween(
                fromAirports, toAirports, startOfDay, endOfDay);

        return new PageResult<>(0, matchingFlights.size(), matchingFlights);
    }
}
