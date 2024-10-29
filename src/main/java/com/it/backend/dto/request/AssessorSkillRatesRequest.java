package com.it.backend.dto.request;

import java.util.Set;

public record AssessorSkillRatesRequest(
        Set<AssessorSkillRateRequest> assessorSkillRatesRequest
) {
}
