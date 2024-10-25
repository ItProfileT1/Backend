package com.it.backend.mapper;

import com.it.backend.dto.request.SkillRequest;
import com.it.backend.entity.Skill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillMapper INSTANCE = Mappers.getMapper(SkillMapper.class);

    @Mapping(target = "id", ignore = true)
    Skill toSkill(SkillRequest skillRequest);

}
