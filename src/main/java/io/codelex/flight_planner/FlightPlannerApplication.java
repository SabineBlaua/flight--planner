package io.codelex.flight_planner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class FlightPlannerApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightPlannerApplication.class, args);
	}
}
