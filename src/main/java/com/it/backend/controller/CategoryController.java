package com.it.backend.controller;

import com.it.backend.dto.request.CategoryRequest;
import com.it.backend.dto.response.CategoryResponse;
import com.it.backend.mapper.CategoryMapper;
import com.it.backend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/skill_categories")
@Tag(name = "Категории навыков")
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получение всех категорий, доступно только администратору")
    public Set<CategoryResponse> findAll(){
        return categoryService.findAll();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Получение категории по айди, доступно только администратору")
    public CategoryResponse findById(@PathVariable Long id){
        return categoryMapper.toCategoryResponse(categoryService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Создание категории, доступно только администратору")
    public CategoryResponse create(@RequestBody CategoryRequest categoryRequest){
        return categoryMapper.toCategoryResponse(categoryService.create(categoryRequest));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Удаление категории, доступно только администратору")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
