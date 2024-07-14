package com.timetracker.tracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The TrackerApplication class is the main class of the program. It is responsible for running the Spring Boot application
 * and initiating the tracking functionality. The main method starts the Spring application context and ensures that
 * the TrackerApplication class is used as the configuration class. This class serves as the entry point for the
 * Tracker application, where all components are initialized and the application is ready to track data.
 */
@SpringBootApplication
public class TrackerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrackerApplication.class, args);
    }

}
