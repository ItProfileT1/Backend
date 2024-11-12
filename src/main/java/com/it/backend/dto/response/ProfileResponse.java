package com.it.backend.dto.response;

import java.time.LocalDate;
import java.util.Set;

public record ProfileResponse(
        Long id,
        String surname,
        String name,
        String patronymic,
        LocalDate birthday,
        String city,
        String sex,
        PositionResponse positionResponse,
        Set<SkillResponse> skillResponses
) {
}
