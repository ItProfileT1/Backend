package com.it.backend.dto.request;

public record SkillRequest(
        Long id,
        String name,
        String description,
        SkillTypeRequest type
) {
}

