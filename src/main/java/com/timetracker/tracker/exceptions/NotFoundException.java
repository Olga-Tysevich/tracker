package com.timetracker.tracker.exceptions;

import static com.timetracker.tracker.utils.Constants.NOT_FOUND;

/**
 * This exception is thrown when an attempt is made to retrieve an object that does not exist on the system.
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs a new NotFoundException with a default error message.
     */
    public NotFoundException() {
        super(NOT_FOUND);
    }

    /**
     * Constructs a new NotFoundException with a custom error message.
     *
     * @param message The custom error message to display.
     */
    public NotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new NotFoundException with a custom error message and a cause.
     *
     * @param message The custom error message to display.
     * @param cause   The cause of the exception.
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new NotFoundException with a cause.
     *
     * @param cause The cause of the exception.
     */
    public NotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new NotFoundException with a custom error message, cause, enable suppression and writable stack trace.
     *
     * @param message            The custom error message to display.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable or not.
     */
    public NotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
