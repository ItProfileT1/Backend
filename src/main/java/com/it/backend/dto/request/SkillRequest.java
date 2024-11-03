package com.it.backend.dto.request;

import java.util.Optional;

public record SkillRequest(
        String name,
        String description,
        Long typeId,
        Optional<Long> categoryId,
        Long scaleId
) {
}

