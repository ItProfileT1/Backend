package com.it.backend.service.assessment_process;

import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@RequiredArgsConstructor
public class AssessmentProcessClosingJob extends QuartzJobBean {

    private final CreatorAssessmentProcessService creatorAssessmentProcessService;

    @Override
    protected void executeInternal(JobExecutionContext context) {
        Long assessmentProcessId = context.getJobDetail().getJobDataMap().getLong("assessmentProcessId");
        creatorAssessmentProcessService.closeAssessmentProcessById(assessmentProcessId);
    }
}
