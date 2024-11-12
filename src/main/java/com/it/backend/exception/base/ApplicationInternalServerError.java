package com.it.backend.exception.base;

import com.it.backend.exception.ApplicationRuntimeException;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper=true)
public abstract class ApplicationInternalServerError extends ApplicationRuntimeException {

    public ApplicationInternalServerError(String message, Object[] args) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, args);
    }
}
