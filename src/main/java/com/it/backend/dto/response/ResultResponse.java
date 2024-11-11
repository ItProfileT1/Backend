package com.it.backend.dto.response;

public record ResultResponse(
        SkillResponse skill,
        LevelResponse level,
        String description,
        String developmentWay
) {
}
