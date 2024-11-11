package com.it.backend.controller;

import com.it.backend.dto.request.AssessmentProcessRequest;
import com.it.backend.dto.request.AssessorSkillRatesRequest;
import com.it.backend.dto.request.SkillLevelsRequest;
import com.it.backend.dto.response.AssessmentProcessResponse;
import com.it.backend.dto.response.QuestionResponse;
import com.it.backend.dto.response.ResultResponse;
import com.it.backend.service.assessment_process.AssessorAssessmentProcessService;
import com.it.backend.service.assessment_process.CreatorAssessmentProcessService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    private final AssessorAssessmentProcessService assessorAssessmentProcessService;
    private final CreatorAssessmentProcessService creatorAssessmentProcessService;

    @PostMapping
    @PreAuthorize("hasRole('MASTER')")
    @Operation(summary = "Создание опроса, доступно только руководителю")
    public Set<AssessmentProcessResponse> createAssessmentProcess(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody AssessmentProcessRequest request) {
        return creatorAssessmentProcessService.createAssessmentProcess(userDetails, request);
    }

    @GetMapping("created")
    @PreAuthorize("hasRole('MASTER')")
    @Operation(summary = "Получение всех действующих опросов, созданных запрашивающим руководителем")
    public Set<AssessmentProcessResponse> getAssessmentProcesses(
            @AuthenticationPrincipal UserDetails userDetails) {
        return creatorAssessmentProcessService.getCreatedAssessmentProcesses(userDetails);
    }

    @GetMapping("{id}/results")
    @PreAuthorize("hasRole('MASTER')")
    @Operation(summary = "Получение результатов опроса по id, доступно только руководителю, создавшему этот опрос")
    public Set<ResultResponse> getResultsByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return creatorAssessmentProcessService.getResultsByAssessmentProcessId(id, userDetails);
    }

    @PostMapping("{id}/results")
    @PreAuthorize("hasRole('MASTER')")
    @Operation(summary = "Подтверждение результатов опроса по id, доступно только руководителю, создавшему этот опрос")
    public Set<AssessmentProcessResponse> approveResultsByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SkillLevelsRequest request) {
        return creatorAssessmentProcessService.approveResultsByAssessmentProcessId(id, userDetails, request);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'MASTER')")
    @Operation(summary = "Получение всех назначенных запрашивающему пользователю опросов")
    public Set<AssessmentProcessResponse> getAssignedAssessmentProcesses(@AuthenticationPrincipal UserDetails userDetails) {
        return assessorAssessmentProcessService.getAssignedAssessmentProcesses(userDetails);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'MASTER')")
    @Operation(summary = "Получение вопросов по id опроса, доступно только специалисту, которому назначен данный опрос")
    public Page<QuestionResponse> getQuestionsByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        return assessorAssessmentProcessService.getQuestionsByAssessmentProcessId(id, userDetails, page, size);
    }

    @PostMapping("{id}")
    @PreAuthorize("hasAnyRole('USER', 'MASTER')")
    @Operation(summary = "Сохранение ответов по id опроса, доступно только специалисту, которому назначен данный опрос")
    public Set<AssessmentProcessResponse> saveRatesByAssessmentProcessId(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AssessorSkillRatesRequest request) {
        return assessorAssessmentProcessService.saveRatesByAssessmentProcessId(id, userDetails, request);
    }
}
