package com.it.backend.repository;

import com.it.backend.entity.AssessmentProcess;
import com.it.backend.entity.Specialist;
import com.it.backend.entity.Status;
import com.it.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Set;

public interface AssessmentProcessRepository extends JpaRepository<AssessmentProcess, Long> {
    boolean existsBySpecialist(Specialist specialist);

    @Query(value = "SELECT ap FROM AssessmentProcess ap WHERE ap.creator = :creator")
    Set<AssessmentProcess> findAssessmentProcessesByCreator(User creator);

    @Query(value = "SELECT DISTINCT ap FROM AssessmentProcess ap " +
            "INNER JOIN AssessorSkillRate asr ON ap.id = asr.assessmentProcess.id " +
            "INNER JOIN AssessmentProcessAssessorStatus s ON ap.id = s.assessmentProcess.id " +
            "WHERE asr.assessor = :assessor AND s.status = :status " +
            "AND ap.deadline >= :timestamp")
    Set<AssessmentProcess> findAllAssessmentProcessesByAssessor(User assessor, Status status, OffsetDateTime timestamp);
}