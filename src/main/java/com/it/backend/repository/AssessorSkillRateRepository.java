package com.it.backend.repository;

import com.it.backend.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface AssessorSkillRateRepository extends JpaRepository<AssessorSkillRate, Long> {
    boolean existsByAssessorAndAssessmentProcess(User assessor, AssessmentProcess assessmentProcess);

    boolean existsByAssessmentProcessAndSkill(AssessmentProcess assessmentProcess, Skill skill);

    void removeByAssessmentProcess(AssessmentProcess assessmentProcess);

    @Query(value = "SELECT asr FROM AssessorSkillRate asr " +
            "INNER JOIN Rate r " +
            "INNER JOIN AssessmentProcessAssessorStatus s " +
            "WHERE asr.assessmentProcess = :assessmentProcess AND s.status = :status AND r.numericValue != 0")
    Set<AssessorSkillRate> findAllAssessorSkillRatesByAssessmentProcess(
            @Param("assessmentProcess") AssessmentProcess assessmentProcess,
            @Param("status") Status status);
}
