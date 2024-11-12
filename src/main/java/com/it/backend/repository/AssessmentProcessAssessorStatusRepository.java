package com.it.backend.repository;

import com.it.backend.entity.AssessmentProcess;
import com.it.backend.entity.AssessmentProcessAssessorStatus;
import com.it.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface AssessmentProcessAssessorStatusRepository extends JpaRepository<AssessmentProcessAssessorStatus, Long> {
    AssessmentProcessAssessorStatus findByAssessmentProcessAndAssessor(AssessmentProcess assessmentProcess, User assessor);

    Set<AssessmentProcessAssessorStatus> findByAssessmentProcess(AssessmentProcess assessmentProcess);

    void removeByAssessmentProcess(AssessmentProcess assessmentProcess);
}
