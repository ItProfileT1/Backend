package com.it.backend.service;

import com.it.backend.dto.request.CategoryRequest;
import com.it.backend.dto.response.CategoryResponse;
import com.it.backend.entity.Category;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.CategoryMapper;
import com.it.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final TypeService typeService;

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("category.not.found", id));
    }

    public Set<CategoryResponse> findAll() {
        return categoryMapper.toCategoryResponses(categoryRepository.findAll());
    }

    public Category create(CategoryRequest categoryRequest) {
        if (categoryRepository.existsByName(categoryRequest.name())) {
            throw new RuntimeException(String.format("category with name %s already exists", categoryRequest.name()));
        }
        Category category = categoryMapper.toCategory(categoryRequest, typeService.findById(categoryRequest.typeId()));
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.delete(findById(id));
    }
}
