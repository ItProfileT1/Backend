package com.it.backend.exception.assessment_process;

import com.it.backend.exception.base.ApplicationInternalServerError;

public class AssessmentProcessSchedulerException extends ApplicationInternalServerError {

    public AssessmentProcessSchedulerException() {
        super("assessment_process.scheduler.error", new Object[]{});
    }
}
