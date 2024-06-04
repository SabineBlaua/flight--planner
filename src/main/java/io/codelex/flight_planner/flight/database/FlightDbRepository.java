package io.codelex.flight_planner.flight.database;

import io.codelex.flight_planner.airport.Airport;
import io.codelex.flight_planner.flight.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightDbRepository extends JpaRepository<Flight, Integer> {
    boolean existsByFromAndToAndCarrierAndDepartureTimeAndArrivalTime(Airport from, Airport to, String carrier, LocalDateTime departureTime, LocalDateTime arrivalTime);

    List<Flight> findByFromInAndToInAndDepartureTimeBetween(List<Airport> from, List<Airport> to, LocalDateTime start, LocalDateTime end);
}
