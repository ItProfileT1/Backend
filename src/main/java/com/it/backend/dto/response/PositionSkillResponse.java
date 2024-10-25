package com.it.backend.dto.response;

public record PositionSkillResponse(
        PositionResponse positionResponse,
        SkillResponse skillResponse,
        SkillLevelResponse skillLevelResponse
) {
}
