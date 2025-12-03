package ru.yandex.practicum.filmorate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The entry point of the Filmorate application.
 * <p>
 * This class triggers the Spring Boot auto-configuration, component scanning,
 * and starts the embedded web server (Tomcat).
 * </p>
 */
@SpringBootApplication
public class FilmorateApplication {

    /**
     * The main method that initializes and launches the application.
     *
     * @param args command line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(FilmorateApplication.class, args);
    }
}