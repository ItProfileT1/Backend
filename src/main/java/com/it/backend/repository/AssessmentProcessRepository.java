package com.it.backend.repository;

import com.it.backend.entity.AssessmentProcess;
import com.it.backend.entity.Specialist;
import com.it.backend.entity.Status;
import com.it.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AssessmentProcessRepository extends JpaRepository<AssessmentProcess, Long> {
    boolean existsBySpecialist(Specialist specialist);

    @Query(value = "SELECT DISTINCT ap FROM AssessmentProcess ap " +
            "INNER JOIN AssessorSkillRate asr " +
            "INNER JOIN AssessmentProcessAssessorStatus s " +
            "WHERE asr.assessor = :assessor AND s.status = :status " +
            "AND ap.deadline >= CURRENT_TIMESTAMP")
    Set<AssessmentProcess> findAllAssessmentProcessesByAssessorId(@Param("assessor") User assessor, Status status);
}