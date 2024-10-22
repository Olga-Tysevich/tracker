package com.timetracker.tracker;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

/**
 * The TrackerApplication class is the main class of the program. It is responsible for running the Spring Boot application
 * and initiating the tracking functionality. The main method starts the Spring application context and ensures that
 * the TrackerApplication class is used as the configuration class. This class serves as the entry point for the
 * Tracker application, where all components are initialized and the application is ready to track data.
 */
@SpringBootApplication
public class TrackerApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("JWT_ACCESS_KEY_SECRET", Objects.requireNonNull(dotenv.get("JWT_ACCESS_KEY_SECRET")));
        System.setProperty("JWT_ACCESS_KEY_EXPIRATION_TIME", Objects.requireNonNull(dotenv.get("JWT_ACCESS_KEY_EXPIRATION_TIME")));
        System.setProperty("JWT_REFRESH_KEY_SECRET", Objects.requireNonNull(dotenv.get("JWT_REFRESH_KEY_SECRET")));
        System.setProperty("JWT_REFRESH_KEY_EXPIRATION_TIME", Objects.requireNonNull(dotenv.get("JWT_REFRESH_KEY_EXPIRATION_TIME")));
        System.setProperty("TRACKER_DB_EPORT", Objects.requireNonNull(dotenv.get("TRACKER_DB_EPORT")));
        System.setProperty("TRACKER_DB_NAME", Objects.requireNonNull(dotenv.get("TRACKER_DB_NAME")));
        System.setProperty("TRACKER_DB_USER", Objects.requireNonNull(dotenv.get("TRACKER_DB_USER")));
        System.setProperty("TRACKER_DB_PASSWORD", Objects.requireNonNull(dotenv.get("TRACKER_DB_PASSWORD")));
        SpringApplication.run(TrackerApplication.class, args);
    }


}
