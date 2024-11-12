package com.it.backend.mapper;

import com.it.backend.dto.response.PositionSkillResponse;
import com.it.backend.entity.Position;
import com.it.backend.entity.PositionSkill;
import com.it.backend.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PositionSkillMapper {

    @Mapping(target = "id", ignore = true)
    PositionSkill toPositionSkill(Position position, Skill skill);

    PositionSkillResponse toPositionSkillResponse(PositionSkill positionSkill);

    Set<PositionSkillResponse> toPositionSkillSetResponse(Iterable<PositionSkill> positionSkills);

}
