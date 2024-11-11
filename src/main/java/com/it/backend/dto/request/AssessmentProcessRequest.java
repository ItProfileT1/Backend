package com.it.backend.dto.request;

import com.it.backend.utils.offsetdatetime.FutureOffsetDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.Set;

public record AssessmentProcessRequest(
        @Schema(description = "Id оцениваемого специалиста", example = "1")
        @NotNull(message = "{assessment_process.request.specialist_id.is_null}")
        @Positive(message = "{assessment_process.request.specialist_id.negative_or_zero}")
        Long specialistId,

        @Schema(description = "Дата и время завершения опроса", example = "2024-11-11T18:00:00+03:00")
        @NotNull(message = "{assessment_process.request.deadline.is_null}")
        @FutureOffsetDateTime(message = "{assessment_process.request.deadline.not_future}")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        OffsetDateTime deadline,

        @Schema(description = "Список id пользователей, которым необходимо назначить опрос", example = "[1, 2, 3]")
        @NotEmpty(message = "{assessment_process.request.assessors_ids}")
        Set<Long> assessorsIds,

        @Schema(description = "Список id компетенций, которые необходимо оценить в рамках данного опроса", example = "[1, 2, 3]")
        @NotEmpty(message = "{assessment_process.request.skills_ids}")
        Set<Long> skillsIds
) {
}
