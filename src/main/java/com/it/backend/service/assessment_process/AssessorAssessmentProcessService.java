package com.it.backend.service.assessment_process;

import com.it.backend.dto.request.AssessorSkillRateRequest;
import com.it.backend.dto.request.AssessorSkillRatesRequest;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.dto.response.QuestionResponse;
import com.it.backend.entity.*;
import com.it.backend.mapper.AssessmentProcessMapper;
import com.it.backend.mapper.AssessorSkillRateMapper;
import com.it.backend.mapper.QuestionMapper;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.repository.AssessmentProcessAssessorStatusRepository;
import com.it.backend.repository.AssessmentProcessRepository;
import com.it.backend.repository.AssessorSkillRateRepository;
import com.it.backend.repository.SpecialistSkillRepository;
import com.it.backend.service.RateService;
import com.it.backend.service.SkillService;
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
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssessorAssessmentProcessService {
    private final AssessmentProcessService assessmentProcessService;
    private final SkillService skillService;
    private final RateService rateService;
    private final SpecialistSkillRepository specialistSkillRepository;
    private final AssessmentProcessRepository assessmentProcessRepository;
    private final AssessorSkillRateRepository assessorSkillRateRepository;
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
        AssessmentProcess assessmentProcess = assessmentProcessService.findById(id);
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
    public Set<AssessmentProcessResponse> saveRatesByAssessmentProcessId(Long id, AssessorSkillRatesRequest request) {
        User user = userService.getCurrentUser();
        AssessmentProcess assessmentProcess = assessmentProcessService.findById(id);
        assessmentProcessValidator.checkAssessorAccessToAssessmentProcess(user, assessmentProcess);

        for (AssessorSkillRateRequest subRequest : request.assessorSkillRates()) {
            Skill skill = skillService.findById(subRequest.skillId());
            assessmentProcessValidator.validateAssessmentProcessSkill(skill, assessmentProcess);

            Rate rate = rateService.findById(subRequest.rateId());
            assessmentProcessValidator.validateSkillRate(rate, skill);

            AssessorSkillRate assessorSkillRate = assessorSkillRateRepository.findByAssessorAndSkill(user, skill);
            assessorSkillRateMapper.updateAssessorSkillRate(assessorSkillRate, rate, subRequest.comment());
            System.out.println(assessorSkillRate.getRate().getId());
        }

        AssessmentProcessAssessorStatus assessmentProcessAssessorStatus =
                assessmentProcessAssessorStatusRepository.findByAssessmentProcessAndAssessor(assessmentProcess, user);
        assessmentProcessAssessorStatus.setStatus(Status.COMPLETED);

        return getAssignedAssessmentProcesses();
    }
}
