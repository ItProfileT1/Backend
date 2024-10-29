package com.it.backend.dto.request;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.Set;

public record AssessmentProcessRequest(
        Long specialistId,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        OffsetDateTime deadline,
        Set<Long> assessorsIds,
        Set<Long> skillsIds
) {
}
