package com.it.backend.dto.response;

import java.util.Set;

public record QuestionResponse(
        SkillResponse skill,
        Set<RateResponse> rates
) {
}
