package com.it.backend.mapper;

import com.it.backend.dto.response.PositionSkillResponse;
import com.it.backend.entity.Position;
import com.it.backend.entity.PositionSkill;
import com.it.backend.entity.Skill;
import com.it.backend.entity.SkillLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PositionSkillMapper {
    PositionSkillMapper INSTANCE = Mappers.getMapper(PositionSkillMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "skillLevel", target = "minSkillLevel")
    PositionSkill toPositionSkill(Position position, Skill skill, SkillLevel skillLevel);

    @Mapping(source = "positionSkill.position", target = "positionResponse")
    @Mapping(source = "positionSkill.skill", target = "skillResponse")
    @Mapping(source = "positionSkill.minSkillLevel", target = "skillLevelResponse")
    PositionSkillResponse toPositionSkillResponse(PositionSkill positionSkill);

    Set<PositionSkillResponse> toPositionSkillSetResponse(Iterable<PositionSkill> positionSkills);

}
