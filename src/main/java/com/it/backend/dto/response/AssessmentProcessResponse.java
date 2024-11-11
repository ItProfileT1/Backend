package com.it.backend.dto.response;

import java.time.OffsetDateTime;

public record AssessmentProcessResponse(
        Long id,
        OffsetDateTime createdAt,
        OffsetDateTime deadline,
        SpecialistResponse specialist
) {
}
