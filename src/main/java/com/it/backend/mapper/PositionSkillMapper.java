package com.it.backend.mapper;

import com.it.backend.dto.response.PositionSkillResponse;
import com.it.backend.entity.Position;
import com.it.backend.entity.PositionSkill;
import com.it.backend.entity.Skill;
import com.it.backend.entity.Level;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface PositionSkillMapper {
    PositionSkillMapper INSTANCE = Mappers.getMapper(PositionSkillMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "level", target = "minLevel")
    PositionSkill toPositionSkill(Position position, Skill skill, Level level);

    @Mapping(source = "positionSkill.position", target = "positionResponse")
    @Mapping(source = "positionSkill.skill", target = "skillResponse")
    @Mapping(source = "positionSkill.minLevel", target = "levelResponse")
    PositionSkillResponse toPositionSkillResponse(PositionSkill positionSkill);

    Set<PositionSkillResponse> toPositionSkillSetResponse(Iterable<PositionSkill> positionSkills);

}
