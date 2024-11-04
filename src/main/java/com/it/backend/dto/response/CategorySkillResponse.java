package com.it.backend.dto.response;

import java.util.Set;

public record CategorySkillResponse (CategoryResponse categoryResponse, Set<SkillResponse> skillResponses) {
}
