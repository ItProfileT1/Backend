package com.it.backend.exception.base;

import com.it.backend.exception.ApplicationRuntimeException;
import org.springframework.http.HttpStatus;

public class ApplicationNotFoundException extends ApplicationRuntimeException {

    public ApplicationNotFoundException(String message, Object[] args) {
        super(message, HttpStatus.NOT_FOUND, args);
    }
}
