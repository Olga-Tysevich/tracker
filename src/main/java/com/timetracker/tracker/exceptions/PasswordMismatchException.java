package com.timetracker.tracker.exceptions;

import static com.timetracker.tracker.utils.Constants.PASSWORDS_MISMATCH;

/**
 * This exception is thrown if the two passwords being checked do not match.
 */
public class PasswordMismatchException extends RuntimeException {

    /**
     * Constructs a new PasswordMismatchException with a default error message.
     */
    public PasswordMismatchException() {
        super(PASSWORDS_MISMATCH);
    }

    /**
     * Constructs a new PasswordMismatchException with a custom error message.
     *
     * @param message The custom error message to display.
     */
    public PasswordMismatchException(String message) {
        super(message);
    }

    /**
     * Constructs a new PasswordMismatchException with a custom error message and a cause.
     *
     * @param message The custom error message to display.
     * @param cause   The cause of the exception.
     */
    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new PasswordMismatchException with a cause.
     *
     * @param cause The cause of the exception.
     */
    public PasswordMismatchException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new PasswordMismatchException with a custom error message, cause, enable suppression and writable stack trace.
     *
     * @param message            The custom error message to display.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable or not.
     */
    public PasswordMismatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
