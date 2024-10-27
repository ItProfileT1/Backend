package com.it.backend.service;

import com.it.backend.entity.Skill;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public Skill findById(Long id){
        return skillRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("skill.not.found", id));
    }
}
