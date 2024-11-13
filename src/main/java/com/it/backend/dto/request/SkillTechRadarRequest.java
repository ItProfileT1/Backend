package com.it.backend.dto.request;

public record SkillTechRadarRequest(
        Long id,
        String name,
        String category,
        Double usageLevel
) {
}
