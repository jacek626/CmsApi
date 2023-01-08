package com.cms.exception;

public class UserNotExistsError extends RuntimeException {

    public UserNotExistsError(String message) {
        super(message);
    }


}
