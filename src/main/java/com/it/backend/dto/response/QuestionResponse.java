package com.it.backend.dto.response;

import com.it.backend.entity.Rate;
import com.it.backend.entity.Skill;

import java.util.Set;

public record QuestionResponse(
        SkillResponse skillResponse,
        Set<RateResponse> ratesResponse
) {
}
