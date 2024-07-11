package com.timetracker.tracker.exceptions;

public class InvalidRecordPeriod extends RuntimeException {
    public InvalidRecordPeriod() {
        super("Invalid record period");
    }
}
