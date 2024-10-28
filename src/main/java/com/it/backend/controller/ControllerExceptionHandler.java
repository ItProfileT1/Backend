package com.it.backend.controller;

import com.it.backend.exception.entity.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;
import java.util.Objects;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException exception, Locale locale) {
        return createProblemDetailResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                new Object[]{exception.getId()},
                locale
        );
    }

    private ResponseEntity<ProblemDetail> createProblemDetailResponse(
            HttpStatus status,
            String messageKey,
            Object[] args,
            Locale locale
    ) {
        var problemDetail = createProblemDetail(status, messageKey, args, locale);
        return ResponseEntity.status(status).body(problemDetail);
    }

    private ProblemDetail createProblemDetail(HttpStatus status, String messageKey, Object[] args, Locale locale) {
        return ProblemDetail.forStatusAndDetail(
                status,
                Objects.requireNonNull(
                        messageSource.getMessage(messageKey, args, messageKey, locale)
                )
        );
    }
}
