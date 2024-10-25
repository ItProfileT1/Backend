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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PositionSkillService {
    private final PositionSkillRepository positionSkillRepository;
    private final PositionRepository positionRepository;
    private final SkillRepository skillRepository;
    private final SkillLevelRepository skillLevelRepository;

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
            }
            PositionSkill positionSkill = PositionSkillMapper.INSTANCE.toPositionSkill(position, skill, skillLevel);
            newPositionSkills.add(positionSkill);
        }
        return PositionSkillMapper.INSTANCE.toPositionSkillSetResponse(positionSkillRepository.saveAll(newPositionSkills));
    }
}
