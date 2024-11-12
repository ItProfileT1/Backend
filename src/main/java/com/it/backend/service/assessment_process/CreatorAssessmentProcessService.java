package com.it.backend.service.assessment_process;

import com.it.backend.dto.request.AssessmentProcessRequest;
import com.it.backend.dto.request.SkillLevelRequest;
import com.it.backend.dto.request.SkillLevelsRequest;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.dto.response.ResultResponse;
import com.it.backend.entity.*;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.AssessmentProcessAssessorStatusMapper;
import com.it.backend.mapper.AssessmentProcessMapper;
import com.it.backend.mapper.AssessorSkillRateMapper;
import com.it.backend.mapper.SpecialistSkillMapper;
import com.it.backend.repository.*;
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
    private final UserRepository userRepository;
    private final SpecialistSkillRepository specialistSkillRepository;
    private final AssessmentProcessRepository assessmentProcessRepository;
    private final AssessorSkillRateRepository assessorSkillRateRepository;
    private final SkillRepository skillRepository;
    private final AssessmentProcessAssessorStatusRepository assessmentProcessAssessorStatusRepository;
    private final LevelRepository levelRepository;
    private final SpecialistRepository specialistRepository;
    private final AssessmentProcessValidator assessmentProcessValidator;
    private final AssessmentProcessSummarizer assessmentProcessSummarizer;
    private final SkillLevelRepository skillLevelRepository;
    private final SpecialistSkillMapper specialistSkillMapper;
    private final AssessmentProcessMapper assessmentProcessMapper;
    private final AssessorSkillRateMapper assessorSkillRateMapper;
    private final AssessmentProcessAssessorStatusMapper assessmentProcessAssessorStatusMapper;
    private final UserService userService;

    @Transactional
    public Set<AssessmentProcessResponse> createAssessmentProcess(AssessmentProcessRequest request) {
        User user = userService.getCurrentUser();
        Specialist specialist = getSpecialistById(request.specialistId());
        assessmentProcessValidator.validateSpecialist(specialist);

        AssessmentProcess assessmentProcess = assessmentProcessMapper.toAssessmentProcess(
                request, specialist, user, OffsetDateTime.now());

        Set<Skill> skillsForAssessment = getSkillsValidatedBySpecialist(request.skillsIds(), specialist);
        Set<Skill> unratedSkills = specialistSkillRepository.findAllUnratedSkillsBySpecialist(specialist);
        skillsForAssessment.addAll(unratedSkills);

        Set<AssessorSkillRate> assessorSkillRates = new HashSet<>();
        Set<AssessmentProcessAssessorStatus> assessmentProcessAssessorStatuses = new HashSet<>();

        for (Long assessorId : request.assessorsIds()) {
            User assessor = getUserById(assessorId);
            AssessmentProcessAssessorStatus assessmentProcessAssessorStatus = getAssessmentProcessAssessorStatus(
                    assessmentProcess, user, Status.AWAITING);
            assessmentProcessAssessorStatuses.add(assessmentProcessAssessorStatus);
            for (Skill skill : skillsForAssessment) {
                assessorSkillRates.add(getAssessmentProcessAssessorSkillRate(assessmentProcess, assessor, skill));
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
        AssessmentProcess assessmentProcess = getAssessmentProcessById(id);
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
    public Set<AssessmentProcessResponse> approveResultsByAssessmentProcessId(
            Long id, SkillLevelsRequest request
    ) {
        User user = userService.getCurrentUser();
        AssessmentProcess assessmentProcess = getAssessmentProcessById(id);
        assessmentProcessValidator.checkCreatorAccessToAssessmentProcess(user, assessmentProcess);

        Specialist specialist = assessmentProcess.getSpecialist();
        for (SkillLevelRequest subRequest : request.skillLevels()) {
            Skill skill = getSkillById(subRequest.skillId());
            assessmentProcessValidator.validateAssessmentProcessSkill(skill, assessmentProcess);
            Level level = getLevelById(subRequest.levelId());
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

    public void closeAssessmentProcessById(Long id) {
        AssessmentProcess assessmentProcess = getAssessmentProcessById(id);
        Set<AssessmentProcessAssessorStatus> assessmentProcessAssessorStatuses
                = assessmentProcessAssessorStatusRepository.findByAssessmentProcess(assessmentProcess);
        for (AssessmentProcessAssessorStatus assessmentProcessAssessorStatus : assessmentProcessAssessorStatuses) {
            assessmentProcessAssessorStatus.setStatus(Status.IGNORED);
        }
    }

    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("user.not.found", id));
    }

    private Specialist getSpecialistById(Long id) {
        return specialistRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("specialist.not.found", id));
    }

    private AssessmentProcess getAssessmentProcessById(Long id) {
        return assessmentProcessRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("assessment_process.not.found", id));
    }

    private Level getLevelById(Long id) {
        return levelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("level.not.found", id));
    }

    private Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("skill.not.found", id));
    }

    private Set<Skill> getSkillsValidatedBySpecialist(Set<Long> ids, Specialist specialist) {
        Set<Skill> skills = new HashSet<>();
        for (Long id : ids) {
            Skill skill = getSkillById(id);
            assessmentProcessValidator.validateSpecialistSkill(specialist, skill);
            skills.add(skill);
        }
        return skills;
    }

    private AssessorSkillRate getAssessmentProcessAssessorSkillRate(
            AssessmentProcess assessmentProcess, User assessor, Skill skill
    ) {
        return assessorSkillRateMapper.toAssessorSkillRate(assessmentProcess, assessor, skill);
    }

    private AssessmentProcessAssessorStatus getAssessmentProcessAssessorStatus(
            AssessmentProcess assessmentProcess, User user, Status status
    ) {
        return assessmentProcessAssessorStatusMapper.toAssessmentProcessAssessorStatus(assessmentProcess, user, status);
    }
}
