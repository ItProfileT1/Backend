package com.it.backend.dto.request;

import java.util.Set;

public record PositionSkillsRequest(Set<PositionSkillRequest> positionSkills) {
}
