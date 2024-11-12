package com.it.backend.dto.response;

import java.util.Set;

public record PositionResponse(
        Long id,
        String name,
        String description,
        Set<SkillResponse> skillResponses
) {
}
