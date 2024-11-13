package com.it.backend.dto.response;

public record TechnologyPayloadResponse(
        Long id,
        String name,
        String level,
        Integer type
) {
}
