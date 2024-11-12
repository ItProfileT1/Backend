package com.it.backend.service.assessment_process;

import com.it.backend.entity.AssessmentProcess;
import com.it.backend.entity.AssessmentProcessAssessorStatus;
import com.it.backend.entity.Status;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.AssessmentProcessAssessorStatusRepository;
import com.it.backend.repository.AssessmentProcessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssessmentProcessService {
    private final AssessmentProcessRepository assessmentProcessRepository;
    private final AssessmentProcessAssessorStatusRepository assessmentProcessAssessorStatusRepository;

    public AssessmentProcess findById(Long id) {
        return assessmentProcessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("assessment_process.not.found", id));
    }

    public void closeAssessmentProcessById(Long id) {
        AssessmentProcess assessmentProcess = findById(id);
        Set<AssessmentProcessAssessorStatus> assessmentProcessAssessorStatuses
                = assessmentProcessAssessorStatusRepository.findByAssessmentProcess(assessmentProcess);
        for (AssessmentProcessAssessorStatus assessmentProcessAssessorStatus : assessmentProcessAssessorStatuses) {
            assessmentProcessAssessorStatus.setStatus(Status.IGNORED);
        }
    }
}
