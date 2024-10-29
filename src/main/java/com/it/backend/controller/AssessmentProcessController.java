package com.it.backend.controller;

import com.it.backend.dto.request.AssessmentProcessRequest;
import com.it.backend.dto.request.AssessorSkillRatesRequest;
import com.it.backend.dto.request.SkillLevelsRequest;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.dto.response.QuestionResponse;
import com.it.backend.dto.response.SkillLevelResponse;
import com.it.backend.service.assessment_process.CreatorAssessmentProcessService;
import com.it.backend.service.assessment_process.UserAssessmentProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/assessment-processes")
public class AssessmentProcessController {
    private final UserAssessmentProcessService userAssessmentProcessService;
    private final CreatorAssessmentProcessService creatorAssessmentProcessService;

    @PostMapping
    public Set<AssessmentProcessResponse> createAssessmentProcess(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AssessmentProcessRequest request) {
        return creatorAssessmentProcessService.createAssessmentProcess(userDetails, request);
    }

    @GetMapping("created")
    public Set<AssessmentProcessResponse> getAssessmentProcesses(
            @AuthenticationPrincipal UserDetails userDetails) {
        return creatorAssessmentProcessService.getCreatedAssessmentProcesses(userDetails);
    }

    @GetMapping("{id}/results")
    public Set<SkillLevelResponse> getResultsByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return creatorAssessmentProcessService.getResultsByAssessmentProcessId(id, userDetails);
    }

    @PostMapping("{id}/results")
    public Set<AssessmentProcessResponse> approveResultsByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SkillLevelsRequest request) {
        return creatorAssessmentProcessService.approveResultsByAssessmentProcessId(id, userDetails, request);
    }

    @GetMapping
    public Set<AssessmentProcessResponse> getAssignedAssessmentProcesses(@AuthenticationPrincipal UserDetails userDetails) {
        return userAssessmentProcessService.getAssignedAssessmentProcesses(userDetails);
    }

    @GetMapping("{id}")
    public Page<QuestionResponse> getQuestionsByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return userAssessmentProcessService.getQuestionsByAssessmentProcessId(id, userDetails, page, size);
    }

    @PostMapping("{id}")
    public Set<AssessmentProcessResponse> saveRatesByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AssessorSkillRatesRequest request) {
        return userAssessmentProcessService.saveRatesByAssessmentProcessId(id, userDetails, request);
    }
}
