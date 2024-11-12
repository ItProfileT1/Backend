package com.it.backend.exception.entity;

import com.it.backend.exception.base.ApplicationConflictException;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class DuplicateEntityException extends ApplicationConflictException {

    public DuplicateEntityException(String message, Long id) {
        super(message, new Object[]{id});
    }
    public DuplicateEntityException(String message, String stringParam) {
        super(message, new Object[]{stringParam});
    }
}
