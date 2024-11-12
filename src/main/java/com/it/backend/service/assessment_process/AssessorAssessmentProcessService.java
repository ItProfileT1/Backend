package com.it.backend.service.assessment_process;

import com.it.backend.dto.request.AssessorSkillRateRequest;
import com.it.backend.dto.request.AssessorSkillRatesRequest;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.dto.response.QuestionResponse;
import com.it.backend.entity.*;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.AssessmentProcessMapper;
import com.it.backend.mapper.AssessorSkillRateMapper;
import com.it.backend.mapper.QuestionMapper;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.repository.*;
import com.it.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssessorAssessmentProcessService {
    private final SpecialistSkillRepository specialistSkillRepository;
    private final AssessmentProcessRepository assessmentProcessRepository;
    private final AssessorSkillRateRepository assessorSkillRateRepository;
    private final SkillRepository skillRepository;
    private final RateRepository rateRepository;
    private final AssessmentProcessAssessorStatusRepository assessmentProcessAssessorStatusRepository;
    private final AssessmentProcessValidator assessmentProcessValidator;
    private final SkillMapper skillMapper;
    private final QuestionMapper questionMapper;
    private final AssessmentProcessMapper assessmentProcessMapper;
    private final AssessorSkillRateMapper assessorSkillRateMapper;
    private final UserService userService;

    public Set<AssessmentProcessResponse> getAssignedAssessmentProcesses() {
        User user = userService.getCurrentUser();
        user.setAssessmentProcesses(assessmentProcessRepository.findAllAssessmentProcessesByAssessor(user, Status.AWAITING, OffsetDateTime.now()));
        return assessmentProcessMapper.toAssessmentProcessesResponse(user.getAssessmentProcesses());
    }

    public Page<QuestionResponse> getQuestionsByAssessmentProcessId(Long id, int page, int size) {
        User user = userService.getCurrentUser();
        AssessmentProcess assessmentProcess = assessmentProcessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("assessment_process.not.found", id));
        assessmentProcessValidator.checkAssessorAccessToAssessmentProcess(user, assessmentProcess);

        Pageable pageable = PageRequest.of(page, size);
        Page<SpecialistSkill> specialistSkills = specialistSkillRepository.findBySpecialist(
                assessmentProcess.getSpecialist(), pageable);

        Set<Skill> skills = skillMapper.toSkills(specialistSkills);

        List<QuestionResponse> questionsResponse = new ArrayList<>();
        for (Skill skill : skills) {
            Set<Rate> rates = skill.getScale().getRates();
            questionsResponse.add(questionMapper.toQuestionResponse(skill, rates));
        }
        return new PageImpl<>(questionsResponse, pageable, specialistSkills.getTotalElements());
    }

    @Transactional
    public Set<AssessmentProcessResponse> saveRatesByAssessmentProcessId(
            Long id, AssessorSkillRatesRequest request
    ) {
        User user = userService.getCurrentUser();
        AssessmentProcess assessmentProcess = assessmentProcessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("assessment_process.not.found", id));
        assessmentProcessValidator.checkAssessorAccessToAssessmentProcess(user, assessmentProcess);

        Set<AssessorSkillRate> assessorSkillRates = new HashSet<>();
        for (AssessorSkillRateRequest subRequest : request.assessorSkillRates()) {
            Skill skill = skillRepository.findById(subRequest.skillId())
                    .orElseThrow(() -> new EntityNotFoundException("skill.not.found", subRequest.skillId()));
            assessmentProcessValidator.validateAssessmentProcessSkill(skill, assessmentProcess);

            Rate rate = rateRepository.findById(subRequest.rateId())
                    .orElseThrow(() -> new EntityNotFoundException("rate.not.found", subRequest.rateId()));
            assessmentProcessValidator.validateSkillRate(rate, skill);

            AssessorSkillRate assessorSkillRate = assessorSkillRateMapper.toAssessorSkillRate(
                    assessmentProcess, user, skill, rate);
            assessorSkillRates.add(assessorSkillRate);
        }
        assessorSkillRateRepository.saveAll(assessorSkillRates);

        AssessmentProcessAssessorStatus assessmentProcessAssessorStatus =
                assessmentProcessAssessorStatusRepository.findByAssessmentProcessAndAssessor(assessmentProcess, user);
        assessmentProcessAssessorStatus.setStatus(Status.COMPLETED);

        return getAssignedAssessmentProcesses();
    }
}
