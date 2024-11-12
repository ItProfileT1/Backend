package com.it.backend.exception.assessment_process;

import com.it.backend.exception.base.ApplicationForbiddenException;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=true)
public class AssessmentProcessAccessException extends ApplicationForbiddenException {

    public AssessmentProcessAccessException(String message, Long accessorId, Long resourceId) {
        super(message, new Object[]{accessorId, resourceId});
    }
}
