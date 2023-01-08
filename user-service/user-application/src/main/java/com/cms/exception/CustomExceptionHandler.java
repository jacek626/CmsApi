package com.cms.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserWithSameLoginExistsError.class)
    public final ResponseEntity<UserWithSameLoginExistsError> userWithSameLoginExistsError(UserWithSameLoginExistsError ex) {
        return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
    }
}
