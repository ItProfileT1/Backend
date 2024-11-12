package com.it.backend.utils.offsetdatetime;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = FutureOffsetDateTimeValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface FutureOffsetDateTime {
    String message() default "The OffsetDateTime must be a future date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
