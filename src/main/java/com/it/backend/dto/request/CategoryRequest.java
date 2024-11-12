package com.it.backend.dto.request;

public record CategoryRequest(
        String name,
        Long typeId
) {
}
