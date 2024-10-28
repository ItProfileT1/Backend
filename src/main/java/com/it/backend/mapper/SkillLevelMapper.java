package com.it.backend.mapper;

import com.it.backend.dto.request.SkillLevelRequest;
import com.it.backend.entity.SkillLevel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SkillLevelMapper {
    SkillLevelMapper INSTANCE = Mappers.getMapper(SkillLevelMapper.class);

    @Mapping(target = "id", ignore = true)
    SkillLevel toSkillLevel(SkillLevelRequest skillLevelRequest);
}
