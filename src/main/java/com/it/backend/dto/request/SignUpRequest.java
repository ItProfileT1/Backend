package com.it.backend.dto.request;

public record SignUpRequest (
        String username,
        String password,
        Long roleId
) {
}
