package com.it.backend.mapper;

import com.it.backend.dto.response.TypeResponse;
import com.it.backend.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    TypeMapper INSTANCE = Mappers.getMapper(TypeMapper.class);

    TypeResponse toTypeResponse(Type type);
}
