package com.it.backend.mapper;

import com.it.backend.dto.request.ScaleRequest;
import com.it.backend.dto.response.ScaleResponse;
import com.it.backend.entity.Scale;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ScaleMapper {

    Scale toScale(ScaleRequest scaleRequest);

    ScaleResponse toScaleResponse(Scale scale);

    Set<ScaleResponse> toScalesResponse(Iterable<Scale> scales);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Scale updateScale(ScaleRequest scaleRequest, @MappingTarget Scale scale);
}
