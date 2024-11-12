package com.it.backend.exception.assessment_process;

import com.it.backend.exception.base.ApplicationBadRequestException;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class AssessmentProcessStatusException extends ApplicationBadRequestException {

    public AssessmentProcessStatusException(String message, Long id) {
        super(message, new Object[]{id});
    }
}
