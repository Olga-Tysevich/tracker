package com.timetracker.tracker.exceptions;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super("User not found!");
    }

}
