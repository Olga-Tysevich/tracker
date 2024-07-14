package com.timetracker.tracker.exceptions;

import static com.timetracker.tracker.utils.Constants.INVALID_USER_ROLE;

/**
 * Exception is thrown when the user's role is invalid or not selected.
 */
public class InvalidRole extends RuntimeException {

    /**
     * Constructs a new InvalidRole with a default message.
     */
    public InvalidRole() {
        super(INVALID_USER_ROLE);
    }

    /**
     * Constructs a new InvalidRole with the specified message.
     *
     * @param message The detail message.
     */
    public InvalidRole(String message) {
        super(message);
    }

    /**
     * Constructs a new InvalidRole with the specified message and cause.
     *
     * @param message The detail message.
     * @param cause   The cause of the exception.
     */
    public InvalidRole(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new InvalidRole with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public InvalidRole(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new InvalidRole with the specified message, cause, enableSuppression flag, and writableStackTrace flag.
     *
     * @param message            The detail message.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable.
     */
    public InvalidRole(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
