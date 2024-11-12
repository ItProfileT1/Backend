package com.it.backend.service.assessment_process;

import com.it.backend.dto.request.AssessmentProcessRequest;
import com.it.backend.dto.request.SkillLevelRequest;
import com.it.backend.dto.request.SkillLevelsRequest;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.dto.response.ResultResponse;
import com.it.backend.entity.*;
import com.it.backend.mapper.AssessmentProcessAssessorStatusMapper;
import com.it.backend.mapper.AssessmentProcessMapper;
import com.it.backend.mapper.AssessorSkillRateMapper;
import com.it.backend.mapper.SpecialistSkillMapper;
import com.it.backend.repository.*;
import com.it.backend.service.LevelService;
import com.it.backend.service.SkillService;
import com.it.backend.service.SpecialistService;
import com.it.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CreatorAssessmentProcessService {
    private final Scheduler scheduler;
    private final SpecialistSkillRepository specialistSkillRepository;
    private final AssessmentProcessRepository assessmentProcessRepository;
    private final AssessorSkillRateRepository assessorSkillRateRepository;
    private final SkillService skillService;
    private final AssessmentProcessAssessorStatusRepository assessmentProcessAssessorStatusRepository;
    private final UserService userService;
    private final LevelService levelService;
    private final SpecialistService specialistService;
    private final AssessmentProcessService assessmentProcessService;
    private final AssessmentProcessValidator assessmentProcessValidator;
    private final AssessmentProcessSummarizer assessmentProcessSummarizer;
    private final SkillLevelRepository skillLevelRepository;
    private final SpecialistSkillMapper specialistSkillMapper;
    private final AssessmentProcessMapper assessmentProcessMapper;
    private final AssessorSkillRateMapper assessorSkillRateMapper;
    private final AssessmentProcessAssessorStatusMapper assessmentProcessAssessorStatusMapper;

    @Transactional
    public Set<AssessmentProcessResponse> createAssessmentProcess(AssessmentProcessRequest request) {
        User user = userService.getCurrentUser();
        Specialist specialist = specialistService.findById(request.specialistId());
        assessmentProcessValidator.validateSpecialist(specialist);

        AssessmentProcess assessmentProcess = assessmentProcessMapper.toAssessmentProcess(
                request, specialist, user, OffsetDateTime.now());

        Set<Skill> skillsForAssessment = getSkillsValidatedBySpecialist(request.skillsIds(), specialist);
        Set<Skill> unratedSkills = specialistSkillRepository.findAllUnratedSkillsBySpecialist(specialist);
        skillsForAssessment.addAll(unratedSkills);

        Set<AssessorSkillRate> assessorSkillRates = new HashSet<>();
        Set<AssessmentProcessAssessorStatus> assessmentProcessAssessorStatuses = new HashSet<>();

        for (Long assessorId : request.assessorsIds()) {
            User assessor = userService.getById(assessorId);
            AssessmentProcessAssessorStatus assessmentProcessAssessorStatus =
                    assessmentProcessAssessorStatusMapper.toAssessmentProcessAssessorStatus(assessmentProcess, user, Status.AWAITING);
            assessmentProcessAssessorStatuses.add(assessmentProcessAssessorStatus);
            for (Skill skill : skillsForAssessment) {
                assessorSkillRates.add(assessorSkillRateMapper.toAssessorSkillRate(assessmentProcess, assessor, skill));
            }
        }
        assessmentProcessRepository.save(assessmentProcess);
        assessorSkillRateRepository.saveAll(assessorSkillRates);
        assessmentProcessAssessorStatusRepository.saveAll(assessmentProcessAssessorStatuses);

        createAssessmentProcessClosingTask(assessmentProcess);
        return getCreatedAssessmentProcesses();
    }

    public Set<AssessmentProcessResponse> getCreatedAssessmentProcesses() {
        User user = userService.getCurrentUser();
        Set<AssessmentProcess> createdAssessmentProcesses = assessmentProcessRepository.findAssessmentProcessesByCreator(user);
        return assessmentProcessMapper.toAssessmentProcessesResponse(createdAssessmentProcesses);
    }

    public Set<ResultResponse> getResultsByAssessmentProcessId(Long id) {
        User user = userService.getCurrentUser();
        AssessmentProcess assessmentProcess = assessmentProcessService.findById(id);
        assessmentProcessValidator.checkCreatorAccessToAssessmentProcess(user, assessmentProcess);
        Set<SpecialistSkill> specialistSkillLevels = assessmentProcessSummarizer.summarizeResults(assessmentProcess);
        Set<ResultResponse> resultsResponse = new HashSet<>();
        for (SpecialistSkill specialistSkillLevel : specialistSkillLevels) {
            SkillLevel skillLevel = skillLevelRepository.findBySkillAndLevel(specialistSkillLevel.getSkill(), specialistSkillLevel.getLevel());
            Set<String> comments = assessorSkillRateRepository.findCommentsBySkill(specialistSkillLevel.getSkill());
            resultsResponse.add(specialistSkillMapper.toResultResponse(specialistSkillLevel, skillLevel, comments));
        }
        return resultsResponse;
    }

    @Transactional
    public Set<AssessmentProcessResponse> approveResultsByAssessmentProcessId(Long id, SkillLevelsRequest request) {
        User user = userService.getCurrentUser();
        AssessmentProcess assessmentProcess = assessmentProcessService.findById(id);
        assessmentProcessValidator.checkCreatorAccessToAssessmentProcess(user, assessmentProcess);

        Specialist specialist = assessmentProcess.getSpecialist();
        for (SkillLevelRequest subRequest : request.skillLevels()) {
            Skill skill = skillService.findById(subRequest.skillId());
            assessmentProcessValidator.validateAssessmentProcessSkill(skill, assessmentProcess);
            Level level = levelService.findById(subRequest.levelId());
            SpecialistSkill specialistSkillLevel = specialistSkillRepository.findBySpecialistAndSkill(specialist, skill);
            specialistSkillMapper.updateSpecialistSkill(specialistSkillLevel, level, request.date());
        }
        assessorSkillRateRepository.removeByAssessmentProcess(assessmentProcess);
        assessmentProcessAssessorStatusRepository.removeByAssessmentProcess(assessmentProcess);
        assessmentProcessRepository.delete(assessmentProcess);

        return getCreatedAssessmentProcesses();
    }

    private void createAssessmentProcessClosingTask(AssessmentProcess assessmentProcess) {
        try {
            Date date = Date.from(assessmentProcess.getDeadline().toInstant());
            JobDetail jobDetail = JobBuilder.newJob(AssessmentProcessClosingJob.class)
                    .usingJobData("assessmentProcessId", assessmentProcess.getId())
                    .build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .startAt(date)
                    .build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            // TODO
        }
    }

    private Set<Skill> getSkillsValidatedBySpecialist(Set<Long> ids, Specialist specialist) {
        Set<Skill> skills = new HashSet<>();
        for (Long id : ids) {
            Skill skill = skillService.findById(id);
            assessmentProcessValidator.validateSpecialistSkill(specialist, skill);
            skills.add(skill);
        }
        return skills;
    }
}
