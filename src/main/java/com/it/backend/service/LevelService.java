package com.it.backend.service;

import com.it.backend.entity.Level;
import com.it.backend.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;

    public Level findByNumericValue(Integer value){
        return levelRepository.findByNumericValue(value);
    }
}
