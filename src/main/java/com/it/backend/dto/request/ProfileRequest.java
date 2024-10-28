package com.it.backend.dto.request;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

public record ProfileRequest(
        String surname,
        String name,
        String patronymic,
        LocalDate birthday,
        String city,
        String sex,
        Optional<Long> positionId,
        Optional<Set<Long>> skillsIds
) {
} 
