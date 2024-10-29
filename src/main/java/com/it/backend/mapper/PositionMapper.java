package com.it.backend.mapper;

import com.it.backend.dto.request.PositionRequest;
import com.it.backend.dto.response.PositionResponse;
import com.it.backend.entity.Position;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PositionMapper {
    PositionMapper INSTANCE = Mappers.getMapper(PositionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specialists", ignore = true)
    @Mapping(target = "positionSkillsLevels", ignore = true)
    Position toPosition(PositionRequest positionRequest);

    PositionResponse toPositionResponse(Position position);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePosition(PositionRequest positionRequest, @MappingTarget Position position);
}
