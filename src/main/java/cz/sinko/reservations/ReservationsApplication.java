package cz.sinko.reservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main class for the Reservations Spring Boot application.
 *
 * @author Radovan Šinko
 */
@SpringBootApplication
public class ReservationsApplication {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private ReservationsApplication() {
        // Utility class constructor
    }

    /**
     * Main method to start the Spring Boot application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ReservationsApplication.class, args);
    }

}
