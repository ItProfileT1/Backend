package com.it.backend.dto.response;

import java.time.LocalDate;

public record SkillLevelResponse(
        Long id,
        SkillResponse skill,
        LevelResponse level,
        LocalDate date,
        String description,
        String developmentWay
) {
}
