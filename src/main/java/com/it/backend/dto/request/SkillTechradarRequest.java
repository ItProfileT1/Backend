package com.it.backend.dto.request;

public record SkillTechradarRequest(
        Long id,
        String name,
        String description,
        String category,
        Integer usageLevel
) {
}
