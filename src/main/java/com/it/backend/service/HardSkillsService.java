package com.it.backend.service;

import com.it.backend.dto.NameDescriptionDto;
import com.it.backend.dto.StringDto;
import com.it.backend.entity.HardSkill;
import com.it.backend.entity.PositionsHardSkills;
import com.it.backend.repository.HardSkillsRepository;
import com.it.backend.repository.PositionRepository;
import com.it.backend.repository.PositionsHardSkillsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HardSkillsService {

    private final HardSkillsRepository hardSkillsRepository;
    private final PositionRepository positionRepository;
    private final PositionsHardSkillsRepository positionsHardSkillsRepository;

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

    public Optional<Object> attachToPosition(Long positionId, StringDto hardSkillNameDto){
        var optionalHardSkill = hardSkillsRepository.findHardSkillByName(hardSkillNameDto.name());
        if (optionalHardSkill.isEmpty())
            return Optional.empty();
        var optionalPosition = positionRepository.findById(positionId);
        if (optionalPosition.isEmpty())
            return Optional.empty();
        var hardSkill = optionalHardSkill.get();
        var position = optionalPosition.get();
        var positionsHardSkills = new PositionsHardSkills();
        positionsHardSkills.setPosition(position);
        positionsHardSkills.setHardSkill(hardSkill);
        positionsHardSkillsRepository.save(positionsHardSkills);
        return Optional.of(new Object());
    }
}
