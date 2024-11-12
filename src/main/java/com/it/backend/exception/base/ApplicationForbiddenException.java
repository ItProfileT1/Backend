package com.it.backend.exception.base;

import com.it.backend.exception.ApplicationRuntimeException;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper=true)
public abstract class ApplicationForbiddenException extends ApplicationRuntimeException {

    public ApplicationForbiddenException(String message, Object[] args) {
        super(message, HttpStatus.FORBIDDEN, args);
    }
}
