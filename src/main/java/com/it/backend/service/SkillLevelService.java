package com.it.backend.service;

import com.it.backend.entity.SkillLevel;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.SkillLevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillLevelService {

    private final SkillLevelRepository skillLevelRepository;

    public SkillLevel findByNumericValue(Integer value){
        return skillLevelRepository.findByNumericValue(value).orElseThrow(() -> new EntityNotFoundException("skill.not.found", 0L));
    }
}
