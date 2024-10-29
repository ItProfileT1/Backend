package com.it.backend.dto.request;

import java.time.LocalDate;
import java.util.Set;

public record SkillLevelsRequest(
        LocalDate date,
        Set<SkillLevelRequest> skillLevelsRequest
) {
}
