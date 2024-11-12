package com.it.backend.service;

import com.it.backend.entity.Level;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;

    public Level findById(Long id) {
        return levelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("level.not.found", id));
    }

    public Level findByNumericValue(Integer value) {
        return levelRepository.findByNumericValue(value);
    }
}
