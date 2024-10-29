package com.it.backend.mapper;

import com.it.backend.dto.request.LevelRequest;
import com.it.backend.dto.response.LevelResponse;
import com.it.backend.entity.Level;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LevelMapper {
    LevelMapper INSTANCE = Mappers.getMapper(LevelMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "levelPositionsSkills", ignore = true)
    @Mapping(target = "levelSpecialistsSkills", ignore = true)
    Level toSkillLevel(LevelRequest levelRequest);

    LevelResponse toLevelResponse(Level level);
}
