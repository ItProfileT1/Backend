package com.it.backend.mapper;

import com.it.backend.dto.request.PositionRequest;
import com.it.backend.dto.response.PositionResponse;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.entity.Position;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface PositionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "specialists", ignore = true)
    Position toPosition(PositionRequest positionRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PositionResponse toPositionResponse(Position position, Set<SkillResponse> skillResponses);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updatePosition(PositionRequest positionRequest, @MappingTarget Position position);
}
