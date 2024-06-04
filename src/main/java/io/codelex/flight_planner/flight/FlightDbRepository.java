package io.codelex.flight_planner.flight;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightDbRepository extends JpaRepository<Flight, Long> {
}
