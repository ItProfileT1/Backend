package com.it.backend.mapper;


import com.it.backend.dto.request.CategoryRequest;
import com.it.backend.dto.response.CategoryResponse;
import com.it.backend.entity.Category;
import com.it.backend.entity.Type;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryResponse toCategoryResponse(Category category);

    Set<CategoryResponse> toCategoryResponses(Iterable<Category> categories);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "skills", ignore = true)
    Category toCategory(CategoryRequest categoryRequest, Type type);
}