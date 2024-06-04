package io.codelex.flight_planner.flight.inMemory;

import io.codelex.flight_planner.flight.Flight;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class FlightInMemoryRepository{

    private final Set<Flight> flightList = new HashSet<>();
    private final AtomicInteger id = new AtomicInteger(0);

    public Flight add(Flight flight) {
        flightList.add(flight);
        return flight;
    }

    public void clearFlights() {
        flightList.clear();
    }

    public boolean containsFlight(Flight flight) {
        return flightList.contains(flight);
    }

    public Optional<Flight> findFlightById(Integer id) {
        return flightList.stream().filter(flight -> flight.getId().equals(id)).findFirst();
    }

    public void deleteFlightById(Integer id) {
        flightList.removeIf(flight -> flight.getId().equals(id));
    }

    public Integer getNewId() {
        return id.incrementAndGet();
    }

    public Set<Flight> getAllFlights() {
        return flightList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightInMemoryRepository that = (FlightInMemoryRepository) o;
        return Objects.equals(flightList, that.flightList) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightList, id);
    }
}
