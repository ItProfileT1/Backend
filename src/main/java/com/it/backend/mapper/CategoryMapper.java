package com.it.backend.mapper;


import com.it.backend.dto.request.CategoryRequest;
import com.it.backend.dto.response.CategoryResponse;
import com.it.backend.entity.Category;
import com.it.backend.entity.Type;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    @Mapping(target = "typeResponse", source = "type")
    CategoryResponse toCategoryResponse(Category category);

    @Mapping(target = "typeResponse", source = "type")
    Set<CategoryResponse> toCategoryResponses(Iterable<Category> categories);

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "categoryRequest.name")
    Category toCategory(CategoryRequest categoryRequest, Type type);
}