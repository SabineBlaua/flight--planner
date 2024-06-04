package io.codelex.flight_planner.flight;

import io.codelex.flight_planner.airport.AirportRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlightModeConfiguration {

    @Value("${myapp.flight-storage-mode}")

    @Bean
    @ConditionalOnProperty(prefix = "myapp", name = "flight-storage-mode", havingValue = "database")
    public FlightDbService createFlightDbService(FlightDbRepository flightDbRepository,
                                                 AirportRepository airportRepository) {
        return new FlightDbService(flightDbRepository, airportRepository);
    }

    @Bean
    @ConditionalOnProperty(prefix = "myapp", name = "flight-storage-mode", havingValue = "in-memory")
    public FlightInMemoryService createFlightInMemoryService(FlightInMemoryRepository flightInMemoryRepository, AirportRepository airportRepository) {
        return new FlightInMemoryService(flightInMemoryRepository, airportRepository);
    }
}
