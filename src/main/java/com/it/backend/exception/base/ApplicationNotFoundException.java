package com.it.backend.exception.base;

import com.it.backend.exception.ApplicationRuntimeException;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper=true)
public abstract class ApplicationNotFoundException extends ApplicationRuntimeException {

    public ApplicationNotFoundException(String message, Object[] args) {
        super(message, HttpStatus.NOT_FOUND, args);
    }
}
