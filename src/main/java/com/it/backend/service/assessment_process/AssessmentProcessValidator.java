package com.it.backend.service.assessment_process;

import com.it.backend.entity.*;
import com.it.backend.repository.*;
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
            // TODO: компетенция с id= не оценивается в рамках опроса с id=
        }
    }

    public void validateSkillRate(Rate rate, Skill skill) {
        if (!rate.getScale().getId().equals(skill.getScale().getId())) {
            // TODO: шкала оценки не соответствует шкале оценивания компетенции
        }
    }

    public void checkCreatorAccessToAssessmentProcess(User user, AssessmentProcess assessmentProcess) {
        if (user.equals(assessmentProcess.getCreator())) {
            // TODO: у пользователя с id= нет доступа к опросу с id=
        }

        if (!assessmentProcess.isExpired()) {
            // TODO: опрос с id= еще не завершен
        }
    }

    public void checkAssessorAccessToAssessmentProcess(User user, AssessmentProcess assessmentProcess) {
        if (!assessorSkillRateRepository.existsByAssessorAndAssessmentProcess(user, assessmentProcess)) {
            // TODO: у пользователя с id= нет доступа к опросу с id=
        }

        if (assessmentProcess.isExpired()) {
            // TODO: опрос с id= завершен
        }

        AssessmentProcessAssessorStatus assessmentProcessAssessorStatus =
                assessmentProcessAssessorStatusRepository.findByAssessmentProcessAndAssessor(assessmentProcess, user);

        if (assessmentProcessAssessorStatus.isCompleted()) {
            // TODO: пользователь с id= уже завершил опрос с id=
        }
    }

    public void validateSpecialistSkill(Specialist specialist, Skill skill) {
        if (!specialistSkillRepository.existsBySpecialistAndSkill(specialist, skill)) {
            // TODO: У специалиста с id= отсутствует компетенция с id=
        }
    }

    public void validateSpecialist(Specialist specialist) {
        if (assessmentProcessRepository.existsBySpecialist(specialist)) {
            // TODO: Процесс оценки специалиста с id= уже запущен
        }
    }
}