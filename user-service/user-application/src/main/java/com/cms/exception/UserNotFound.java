package com.cms.exception;

public class UserNotFound extends RuntimeException {

    public UserNotFound(final String message) {
        super(message);
    }
}
