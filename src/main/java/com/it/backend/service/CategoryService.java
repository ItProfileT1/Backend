package com.it.backend.service;

import com.it.backend.entity.Category;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("category.not.found", id));
    }

}
