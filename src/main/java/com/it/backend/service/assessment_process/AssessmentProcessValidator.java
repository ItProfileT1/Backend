package com.it.backend.service.assessment_process;

import com.it.backend.entity.*;
import com.it.backend.exception.assessment_process.AssessmentProcessAccessException;
import com.it.backend.exception.assessment_process.AssessmentProcessInconsistencyException;
import com.it.backend.exception.assessment_process.AssessmentProcessStatusException;
import com.it.backend.exception.entity.DuplicateEntityException;
import com.it.backend.repository.AssessmentProcessAssessorStatusRepository;
import com.it.backend.repository.AssessmentProcessRepository;
import com.it.backend.repository.AssessorSkillRateRepository;
import com.it.backend.repository.SpecialistSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AssessmentProcessValidator {
    private final SpecialistSkillRepository specialistSkillRepository;
    private final AssessmentProcessRepository assessmentProcessRepository;
    private final AssessorSkillRateRepository assessorSkillRateRepository;
    private final AssessmentProcessAssessorStatusRepository assessmentProcessAssessorStatusRepository;

    public void validateAssessmentProcessSkill(Skill skill, AssessmentProcess assessmentProcess) {
        if (!assessorSkillRateRepository.existsByAssessmentProcessAndSkill(assessmentProcess, skill)) {
            throw new AssessmentProcessInconsistencyException(
                    "assessment_process.does.not.include.skill", skill.getId(), assessmentProcess.getId());
        }
    }

    public void validateSkillRate(Rate rate, Skill skill) {
        if (!rate.getScale().getId().equals(skill.getScale().getId())) {
            throw new AssessmentProcessInconsistencyException(
                    "rate_scale.differs.from.skill_scale", rate.getScale().getId(), skill.getScale().getId());
        }
    }

    public void checkCreatorAccessToAssessmentProcess(User user, AssessmentProcess assessmentProcess) {
        if (!user.equals(assessmentProcess.getCreator())) {
            throw new AssessmentProcessAccessException(
                    "user.has.no.access.to.assessment_process", user.getId(), assessmentProcess.getId());
        }

        if (!assessmentProcess.isExpired()) {
            throw new AssessmentProcessStatusException(
                    "assessment_process.not.completed.yet", assessmentProcess.getId());
        }
    }

    public void checkAssessorAccessToAssessmentProcess(User user, AssessmentProcess assessmentProcess) {
        if (!assessorSkillRateRepository.existsByAssessorAndAssessmentProcess(user, assessmentProcess)) {
            throw new AssessmentProcessAccessException(
                    "user.has.no.access.to.assessment_process", user.getId(), assessmentProcess.getId());
        }

        if (assessmentProcess.isExpired()) {
            throw new AssessmentProcessStatusException(
                    "assessment_process.already.completed", assessmentProcess.getId());
        }

        AssessmentProcessAssessorStatus assessmentProcessAssessorStatus =
                assessmentProcessAssessorStatusRepository.findByAssessmentProcessAndAssessor(assessmentProcess, user);

        if (assessmentProcessAssessorStatus.isCompleted()) {
            throw new AssessmentProcessInconsistencyException(
                    "user.has.already.completed.assessment_process", user.getId(), assessmentProcess.getId());
        }
    }

    public void validateSpecialistSkill(Specialist specialist, Skill skill) {
        if (!specialistSkillRepository.existsBySpecialistAndSkill(specialist, skill)) {
            throw new AssessmentProcessInconsistencyException(
                    "specialist.has.no.such.skill", specialist.getId(), skill.getId());
        }
    }

    public void validateSpecialist(Specialist specialist) {
        if (assessmentProcessRepository.existsBySpecialist(specialist)) {
            throw new DuplicateEntityException("assessment_process.already_exists", specialist.getId());
        }
    }
}