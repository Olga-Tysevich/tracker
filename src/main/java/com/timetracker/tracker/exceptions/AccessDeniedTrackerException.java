package com.timetracker.tracker.exceptions;

import static com.timetracker.tracker.utils.Constants.ACCESS_DENIED;

/**
 * The exception class for tracking access denied errors.
 */
public class AccessDeniedTrackerException extends RuntimeException {

    /**
     * Constructs a new AccessDeniedTrackerException with a default message.
     */
    public AccessDeniedTrackerException() {
        super(ACCESS_DENIED);
    }

    /**
     * Constructs a new AccessDeniedTrackerException with the specified message.
     *
     * @param e the message for the exception.
     */
    public AccessDeniedTrackerException(String e) {
        super(e);
    }

    /**
     * Constructs a new AccessDeniedTrackerException with the specified cause.
     *
     * @param cause the cause of the exception.
     */
    public AccessDeniedTrackerException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new AccessDeniedTrackerException with the specified message, cause, suppression
     * enabled or disabled, and writable stack trace.
     *
     * @param message            the detail message.
     * @param cause              the cause (which is saved for later retrieval by the getCause() method).
     * @param enableSuppression  whether or not suppression is enabled or disabled.
     * @param writableStackTrace whether or not the stack trace should be writable.
     */
    public AccessDeniedTrackerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}