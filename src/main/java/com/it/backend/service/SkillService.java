package com.it.backend.service;

import com.it.backend.dto.response.SkillResponse;
import com.it.backend.entity.Skill;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public Skill findById(Long id){
        return skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("skill.not.found", id));
    }

    public Set<SkillResponse> findAll(){
        Set<Skill> skills = new HashSet<>();
        for (Skill skill : skillRepository.findAll()) {
            skills.add(skill);
        }
        return SkillMapper.INSTANCE.toSkillResponses(skills);
    }
}
