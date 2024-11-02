package com.it.backend.controller;

import com.it.backend.dto.request.AssessmentProcessRequest;
import com.it.backend.dto.request.AssessorSkillRatesRequest;
import com.it.backend.dto.request.SkillLevelsRequest;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.dto.response.QuestionResponse;
import com.it.backend.dto.response.SkillLevelResponse;
import com.it.backend.service.assessment_process.CreatorAssessmentProcessService;
import com.it.backend.service.assessment_process.UserAssessmentProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/assessment-processes")
@Tag(name = "Оценка 360")

public class AssessmentProcessController {
    private final UserAssessmentProcessService userAssessmentProcessService;
    private final CreatorAssessmentProcessService creatorAssessmentProcessService;

    @PostMapping
    @PreAuthorize("hasRole('MASTER')")
    @Operation(summary = "Создание опроса, доступно только руководителю")
    public Set<AssessmentProcessResponse> createAssessmentProcess(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AssessmentProcessRequest request) {
        return creatorAssessmentProcessService.createAssessmentProcess(userDetails, request);
    }

    @GetMapping("created")
    @PreAuthorize("hasRole('MASTER')")
    @Operation(summary = "Получение всех действующих опросов, доступно только руководителю")
    public Set<AssessmentProcessResponse> getAssessmentProcesses(
            @AuthenticationPrincipal UserDetails userDetails) {
        return creatorAssessmentProcessService.getCreatedAssessmentProcesses(userDetails);
    }

    @GetMapping("{id}/results")
    @PreAuthorize("hasRole('MASTER')")
    @Operation(summary = "Получение результатов опроса по айди, доступно только руководителю")
    public Set<SkillLevelResponse> getResultsByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return creatorAssessmentProcessService.getResultsByAssessmentProcessId(id, userDetails);
    }

    @PostMapping("{id}/results")
    @PreAuthorize("hasRole('MASTER')")
    @Operation(summary = "Подтверждение результатов опроса по айди, доступно только руководителю")
    public Set<AssessmentProcessResponse> approveResultsByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SkillLevelsRequest request) {
        return creatorAssessmentProcessService.approveResultsByAssessmentProcessId(id, userDetails, request);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Получение всех назначенных опросов, доступно только специалисту")
    public Set<AssessmentProcessResponse> getAssignedAssessmentProcesses(@AuthenticationPrincipal UserDetails userDetails) {
        return userAssessmentProcessService.getAssignedAssessmentProcesses(userDetails);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Получение вопросов по айди опроса, доступно только специалисту")
    public Page<QuestionResponse> getQuestionsByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return userAssessmentProcessService.getQuestionsByAssessmentProcessId(id, userDetails, page, size);
    }

    @PostMapping("{id}")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Сохранение ответов по айди опроса, доступно только специалисту")
    public Set<AssessmentProcessResponse> saveRatesByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AssessorSkillRatesRequest request) {
        return userAssessmentProcessService.saveRatesByAssessmentProcessId(id, userDetails, request);
    }
}
