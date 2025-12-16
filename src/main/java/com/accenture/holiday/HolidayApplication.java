package com.accenture.holiday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Holiday Spring Boot application.
 *
 * <p>This class boots the Spring context and starts the embedded server.</p>
 */
@SpringBootApplication
public class HolidayApplication {

    /**
     * Main method used to run the Spring Boot application.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(HolidayApplication.class, args);
    }

}
