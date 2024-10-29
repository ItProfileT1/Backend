package com.it.backend.dto.request;

public record SkillRequest(
        String name,
        String description,
        Long typeId,
        Long categoryId,
        Long scaleId
) {
}

