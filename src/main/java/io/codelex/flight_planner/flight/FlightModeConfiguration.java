package io.codelex.flight_planner.flight;

import io.codelex.flight_planner.airport.AirportDbRepository;
import io.codelex.flight_planner.airport.AirportRepository;
import io.codelex.flight_planner.flight.database.FlightDbRepository;
import io.codelex.flight_planner.flight.database.FlightDbService;
import io.codelex.flight_planner.flight.inMemory.FlightInMemoryRepository;
import io.codelex.flight_planner.flight.inMemory.FlightInMemoryService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightModeConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "myapp", name = "flight-storage-mode", havingValue = "database")
    public FlightDbService createFlightDbService(FlightDbRepository flightDbRepository,
                                                 AirportDbRepository airportDbRepository) {
        return new FlightDbService(flightDbRepository, airportDbRepository);
    }

    @Bean
    @ConditionalOnProperty(prefix = "myapp", name = "flight-storage-mode", havingValue = "in-memory")
    public FlightInMemoryService createFlightInMemoryService(FlightInMemoryRepository flightInMemoryRepository, AirportRepository airportRepository) {
        return new FlightInMemoryService(flightInMemoryRepository, airportRepository);
    }
}
