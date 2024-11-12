package com.it.backend.exception.base;

import com.it.backend.exception.ApplicationRuntimeException;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

@EqualsAndHashCode(callSuper=true)
public abstract class ApplicationConflictException extends ApplicationRuntimeException {

    public ApplicationConflictException(String message, Object[] args) {
        super(message, HttpStatus.CONFLICT, args);
    }
}
