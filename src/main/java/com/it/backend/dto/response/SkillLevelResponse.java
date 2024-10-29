package com.it.backend.dto.response;

import java.time.LocalDate;

public record SkillLevelResponse(
        Long id,
        SkillResponse skillResponse,
        LevelResponse levelResponse,
        LocalDate date,
        String description,
        String developmentWay
) {
}
