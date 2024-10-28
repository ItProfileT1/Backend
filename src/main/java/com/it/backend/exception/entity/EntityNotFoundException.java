package com.it.backend.exception.entity;

import com.it.backend.exception.ApplicationRuntimeException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class EntityNotFoundException extends ApplicationRuntimeException {
    private final Long id;

    public EntityNotFoundException(String message, Long id) {
        super(message);
        this.id = id;
    }
}
