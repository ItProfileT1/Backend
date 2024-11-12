package com.it.backend.dto.request;

public record CategoryRequest(
        String name,
        String description,
        Long typeId
) {
}
