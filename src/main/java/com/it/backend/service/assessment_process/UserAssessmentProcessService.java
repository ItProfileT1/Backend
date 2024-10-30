package com.it.backend.service.assessment_process;

import com.it.backend.dto.request.*;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.dto.response.QuestionResponse;
import com.it.backend.entity.*;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.*;
import com.it.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserAssessmentProcessService {
    private final SpecialistSkillRepository specialistSkillRepository;
    private final AssessmentProcessRepository assessmentProcessRepository;
    private final AssessorSkillRateRepository assessorSkillRateRepository;
    private final SkillRepository skillRepository;
    private final RateRepository rateRepository;
    private final AssessmentProcessAssessorStatusRepository assessmentProcessAssessorStatusRepository;
    private final AssessmentProcessValidator assessmentProcessValidator;

    public Set<AssessmentProcessResponse> getAssignedAssessmentProcesses(UserDetails userDetails) {
        User user = (User) userDetails;
        Set<AssessmentProcess> assessmentProcesses = assessmentProcessRepository
                .findAllAssessmentProcessesByAssessorId(user, Status.AWAITING);
        return AssessmentProcessMapper.INSTANCE.toAssessmentProcessesResponse(assessmentProcesses);
    }

    public Page<QuestionResponse> getQuestionsByAssessmentProcessId(Long id, UserDetails userDetails, int page, int size) {
        User user = (User) userDetails;
        AssessmentProcess assessmentProcess = assessmentProcessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("assessment_process.not.found", id));
        assessmentProcessValidator.checkAssessorAccessToAssessmentProcess(user, assessmentProcess);

        Pageable pageable = PageRequest.of(page, size);
        Page<SpecialistSkill> specialistSkills = specialistSkillRepository.findBySpecialist(
                assessmentProcess.getSpecialist(), pageable);

        Set<Skill> skills = SkillMapper.INSTANCE.toSkills(specialistSkills);

        List<QuestionResponse> questionsResponse = new ArrayList<>();
        for (Skill skill : skills) {
            Set<Rate> rates = skill.getScale().getRates();
            questionsResponse.add(QuestionMapper.INSTANCE.toQuestionResponse(skill, rates));
        }
        return new PageImpl<>(questionsResponse, pageable, specialistSkills.getTotalElements());
    }

    @Transactional
    public Set<AssessmentProcessResponse> saveRatesByAssessmentProcessId(
            Long id, UserDetails userDetails, AssessorSkillRatesRequest request
    ) {
        User user = (User) userDetails;
        AssessmentProcess assessmentProcess = assessmentProcessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("assessment_process.not.found", id));
        assessmentProcessValidator.checkAssessorAccessToAssessmentProcess(user, assessmentProcess);

        Set<AssessorSkillRate> assessorSkillRates = new HashSet<>();
        for (AssessorSkillRateRequest subRequest : request.assessorSkillRatesRequest()) {
            Skill skill = skillRepository.findById(subRequest.skillId())
                    .orElseThrow(() -> new EntityNotFoundException("skill.not.found", subRequest.skillId()));
            assessmentProcessValidator.validateAssessmentProcessSkill(skill, assessmentProcess);

            Rate rate = rateRepository.findById(subRequest.rateId())
                    .orElseThrow(() -> new EntityNotFoundException("rate.not.found", subRequest.rateId()));
            assessmentProcessValidator.validateSkillRate(rate, skill);

            AssessorSkillRate assessorSkillRate = AssessorSkillRateMapper.INSTANCE.toAssessorSkillRate(
                    assessmentProcess, user, skill, rate);
            assessorSkillRates.add(assessorSkillRate);
        }
        assessorSkillRateRepository.saveAll(assessorSkillRates);

        AssessmentProcessAssessorStatus assessmentProcessAssessorStatus =
                assessmentProcessAssessorStatusRepository.findByAssessmentProcessAndAssessor(assessmentProcess, user);
        assessmentProcessAssessorStatus.setStatus(Status.COMPLETED);

        return getAssignedAssessmentProcesses(userDetails);
    }
}
