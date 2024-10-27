package com.it.backend.dto.request;

import com.it.backend.dto.response.PositionResponse;
import com.it.backend.dto.response.SkillResponse;

import java.time.LocalDate;
import java.util.Set;

public record ProfileResponse(
        String surname,
        String name,
        String patronymic,
        LocalDate birthday,
        String city,
        String sex,
        PositionResponse positionResponse,
        Set<SkillResponse> skillsResponses
) {
}
