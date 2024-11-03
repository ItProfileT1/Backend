package com.it.backend.dto.response;

public record SkillResponse(
        Long id,
        String name,
        String description,
        String type,
        String category
) {
}
