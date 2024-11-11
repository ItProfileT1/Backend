package com.it.backend.dto.response;

public record PositionSkillResponse(
        PositionResponse position,
        SkillResponse skill
) {
}
