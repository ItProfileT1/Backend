package com.it.backend.dto.response;

import java.time.LocalDate;

public record SpecialistResponse(
        Long id,
        String surname,
        String name,
        String patronymic,
        LocalDate birthday,
        String sex,
        String city
) {
}
