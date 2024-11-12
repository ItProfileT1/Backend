package com.it.backend.mapper;

import com.it.backend.dto.request.LevelRequest;
import com.it.backend.dto.response.LevelResponse;
import com.it.backend.entity.Level;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LevelMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "levelSpecialistsSkills", ignore = true)
    Level toSkillLevel(LevelRequest levelRequest);

    LevelResponse toLevelResponse(Level level);
}
