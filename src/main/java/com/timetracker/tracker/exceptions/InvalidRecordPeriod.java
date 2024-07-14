package com.timetracker.tracker.exceptions;

import static com.timetracker.tracker.utils.Constants.INVALID_RECORD_PERIOD;

/**
 * Indicates an incorrect time period for a record entity.
 */
public class InvalidRecordPeriod extends RuntimeException {

    /**
     * Constructs a new InvalidRecordPeriod with a default message.
     */
    public InvalidRecordPeriod() {
        super(INVALID_RECORD_PERIOD);
    }

    /**
     * Constructs a new InvalidRecordPeriod with the specified message.
     *
     * @param message The detail message.
     */
    public InvalidRecordPeriod(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidRecordPeriod with the specified message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause of the exception.
     */
    public InvalidRecordPeriod(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InvalidRecordPeriod with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public InvalidRecordPeriod(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new InvalidRecordPeriod with the specified message, cause, enableSuppression flag, and writableStackTrace flag.
     *
     * @param message            The detail message.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable.
     */
    public InvalidRecordPeriod(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
