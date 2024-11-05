package com.it.backend.dto.response;

public record UserResponse(
        Long id,
        String username,
        String password,
        RoleResponse roleResponse
) {
}
