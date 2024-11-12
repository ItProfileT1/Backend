package com.it.backend.exception.base;

import com.it.backend.exception.ApplicationRuntimeException;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
public abstract class ApplicationBadRequestException extends ApplicationRuntimeException {

    public ApplicationBadRequestException(String message, Object[] args) {
        super(message, HttpStatus.BAD_REQUEST, args);
    }
}
