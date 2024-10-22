package com.it.backend.service;

import com.it.backend.dto.NameDescriptionDto;
import com.it.backend.entity.HardSkill;
import com.it.backend.repository.HardSkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HardSkillsService {

    private final HardSkillsRepository hardSkillsRepository;

    private HardSkill castToEntity(NameDescriptionDto dto){
        var hardSkill = new HardSkill();
        hardSkill.setName(dto.name());
        hardSkill.setDescription(dto.description());
        return hardSkill;
    }

    public Optional<Long> createHardSkill(NameDescriptionDto dto){
        if (hardSkillsRepository.existsHardSkillByName(dto.name()))
            return Optional.empty();
        return Optional.of(hardSkillsRepository.save(castToEntity(dto)).getId());
    }
}
