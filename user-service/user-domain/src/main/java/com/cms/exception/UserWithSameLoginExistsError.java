package com.cms.exception;

public class UserWithSameLoginExistsError extends RuntimeException {

    public UserWithSameLoginExistsError(String message) {
        super(message);
    }
}
