package com.timetracker.tracker.exceptions;

import static com.timetracker.tracker.utils.Constants.USER_ALREADY_EXIST;

/**
 * This exception is thrown when trying to create or add a user that already exists in the system.
 */
public class UserAlreadyExist extends RuntimeException {

    /**
     * Constructs a new UserAlreadyExist with a default error message.
     */
    public UserAlreadyExist() {
        super(USER_ALREADY_EXIST);
    }

    /**
     * Constructs a new UserAlreadyExist with a custom error message.
     *
     * @param message The custom error message to display.
     */
    public UserAlreadyExist(String message) {
        super(message);
    }

    /**
     * Constructs a new UserAlreadyExist with a custom error message and a cause.
     *
     * @param message The custom error message to display.
     * @param cause   The cause of the exception.
     */
    public UserAlreadyExist(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UserAlreadyExist with a cause.
     *
     * @param cause The cause of the exception.
     */
    public UserAlreadyExist(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UserAlreadyExist with a custom error message, cause, enable suppression and writable stack trace.
     *
     * @param message            The custom error message to display.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable or not.
     */
    public UserAlreadyExist(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
