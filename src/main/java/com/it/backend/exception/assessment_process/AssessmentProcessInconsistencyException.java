package com.it.backend.exception.assessment_process;

import com.it.backend.exception.base.ApplicationBadRequestException;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class AssessmentProcessInconsistencyException extends ApplicationBadRequestException {

    public AssessmentProcessInconsistencyException(String message, Long firstEntityId, Long secondEntityId) {
        super(message, new Object[]{firstEntityId, secondEntityId});
    }
}
