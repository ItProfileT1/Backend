package com.it.backend.dto.response;

import java.util.Set;

public record ResultResponse(
        SkillResponse skill,
        LevelResponse level,
        String description,
        String developmentWay,
        Set<String> comments
) {
}
