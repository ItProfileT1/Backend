package com.it.backend.exception.entity;

import com.it.backend.exception.base.ApplicationNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
public class EntityNotFoundException extends ApplicationNotFoundException {

    public EntityNotFoundException(String message, Long id) {
        super(message, new Object[]{id});
    }

    public EntityNotFoundException(String message, String stringParam) {
        super(message, new Object[]{stringParam});
    }
}
