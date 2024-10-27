package com.it.backend.service;

import com.it.backend.dto.request.PositionSkillRequest;
import com.it.backend.dto.request.PositionSkillsRequest;
import com.it.backend.dto.response.PositionSkillResponse;
import com.it.backend.entity.Position;
import com.it.backend.entity.PositionSkill;
import com.it.backend.entity.Skill;
import com.it.backend.entity.SkillLevel;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.PositionSkillMapper;
import com.it.backend.repository.PositionRepository;
import com.it.backend.repository.PositionSkillRepository;
import com.it.backend.repository.SkillLevelRepository;
import com.it.backend.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PositionSkillService {
    private final PositionSkillRepository positionSkillRepository;
    private final PositionRepository positionRepository;
    private final SkillRepository skillRepository;
    private final SkillLevelRepository skillLevelRepository;
    private final PositionService positionService;

    public Set<PositionSkillResponse> addSkills(Long id, PositionSkillsRequest request) {
        Position position = positionRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("position.not.found", id));
        List<PositionSkill> newPositionSkills = new ArrayList<>();
        for (PositionSkillRequest subRequest : request.positionSkills()) {
            Skill skill = skillRepository.findById(subRequest.skillId()).orElseThrow(
                    () -> new EntityNotFoundException("skill.not.found", subRequest.skillId())
            );
            SkillLevel skillLevel = skillLevelRepository.findById(subRequest.minLevelId()).orElseThrow(
                    () -> new EntityNotFoundException("skill_level.not.found", subRequest.skillId())
            );
            if (positionSkillRepository.existsByPositionAndSkillAndMinSkillLevel(position, skill, skillLevel)) {
            //TODO сделать обработку ошибки
            }
            PositionSkill positionSkill = PositionSkillMapper.INSTANCE.toPositionSkill(position, skill, skillLevel);
            newPositionSkills.add(positionSkill);
        }
        return PositionSkillMapper.INSTANCE.toPositionSkillSetResponse(positionSkillRepository.saveAll(newPositionSkills));
    }

    public Set<Skill> findSkillsByPosition(Position position) {
        Set<Skill> skills = new HashSet<>();
        for (PositionSkill positionSkill : positionService.findAllPositionSkillsByPosition(position)) {
            var skill = positionSkill.getSkill();
            if (skill != null)
                skills.add(positionSkill.getSkill());
        }
        return skills;
    }
}
