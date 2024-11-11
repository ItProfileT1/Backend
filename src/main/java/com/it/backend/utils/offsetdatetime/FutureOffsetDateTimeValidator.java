package com.it.backend.utils.offsetdatetime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.OffsetDateTime;

public class FutureOffsetDateTimeValidator implements ConstraintValidator<FutureOffsetDateTime, OffsetDateTime> {
    @Override
    public boolean isValid(OffsetDateTime value, ConstraintValidatorContext context) {
        return value != null && value.isAfter(OffsetDateTime.now());
    }
}
