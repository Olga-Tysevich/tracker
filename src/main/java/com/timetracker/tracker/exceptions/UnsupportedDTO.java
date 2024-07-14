package com.timetracker.tracker.exceptions;

import static com.timetracker.tracker.utils.Constants.Unsupported_DTO_MESSAGE;

/**
 * This exception is thrown when there is a pass to a method that is not supported by the method.
 */
public class UnsupportedDTO extends RuntimeException {

    /**
     * Constructs a new UnsupportedDTO with a default error message.
     */
    public UnsupportedDTO() {
        super(Unsupported_DTO_MESSAGE);
    }

    /**
     * Constructs a new UnsupportedDTO with a custom error message.
     *
     * @param message The custom error message to display.
     */
    public UnsupportedDTO(String message) {
        super(message);
    }

    /**
     * Constructs a new UnsupportedDTO with a custom error message and a cause.
     *
     * @param message The custom error message to display.
     * @param cause   The cause of the exception.
     */
    public UnsupportedDTO(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new UnsupportedDTO with a cause.
     *
     * @param cause The cause of the exception.
     */
    public UnsupportedDTO(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new UnsupportedDTO with a custom error message, cause, enable suppression and writable stack trace.
     *
     * @param message            The custom error message to display.
     * @param cause              The cause of the exception.
     * @param enableSuppression  Whether suppression is enabled or disabled.
     * @param writableStackTrace Whether the stack trace should be writable or not.
     */
    public UnsupportedDTO(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
