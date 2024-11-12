package com.it.backend.service;

import com.it.backend.dto.request.PositionRequest;
import com.it.backend.dto.response.PositionResponse;
import com.it.backend.entity.Position;
import com.it.backend.entity.PositionSkill;
import com.it.backend.entity.Skill;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.PositionMapper;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.repository.PositionRepository;
import com.it.backend.repository.PositionSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;
    private final SkillMapper skillMapper;
    private final SkillService skillService;
    private final PositionSkillRepository positionSkillRepository;

    public PositionResponse attachSkills(Long positionId, Set<Long> skillsIds) {
        var position = findById(positionId);
        Set<PositionSkill> positionSkills = new HashSet<>();
        for (Long skillsId : skillsIds) {
            var skill = skillService.findById(skillsId);
            var positionSkill = PositionSkill.builder()
                    .position(position)
                    .skill(skill)
                    .build();
            positionSkillRepository.save(positionSkill);
            positionSkills.add(positionSkill);
        }
        position.setPositionSkills(positionSkills);
        positionRepository.save(position);
        return findPositionById(positionId);
    }

    public Set<Skill> findSkillsByPosition(Position position) {
        Set<Skill> skills = new HashSet<>();
        var positionSkills = findAllPositionSkillsByPosition(position);
        if (positionSkills == null) {
            return null;
        }
        for (PositionSkill positionSkill : positionSkills) {
            var skill = positionSkill.getSkill();
            if (skill != null)
                skills.add(positionSkill.getSkill());
        }
        return skills;
    }

    public PositionResponse createPosition(PositionRequest request) {
        Position position = positionMapper.toPosition(request);
        if (positionRepository.existsByName(position.getName())) {
            //TODO сделать обработку ошибки в случае если должность уже существует
        }
        return positionMapper.toPositionResponse(
                positionRepository.save(position),
                skillMapper.toSkillResponses(findSkillsByPosition(position)));
    }

    public PositionResponse findPositionById(Long id) {
        var position = findById(id);
        return positionMapper.toPositionResponse(position,
                skillMapper.toSkillResponses(findSkillsByPosition(position)));
    }

    public Position findById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("position.not.found", id));
    }

    public Set<PositionResponse> findAllPositions() {
        Iterable<Position> positions = positionRepository.findAll();
        Set<PositionResponse> positionResponses = new HashSet<>();
        for (Position position : positions) {
            positionResponses.add(positionMapper.toPositionResponse(position,
                    skillMapper.toSkillResponses(findSkillsByPosition(position))));
        }
        return positionResponses;
    }

    public PositionResponse updatePosition(Long id, PositionRequest request) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("position.not.found", id));
        positionMapper.updatePosition(request, position);
        return positionMapper.toPositionResponse(positionRepository.save(position),
                skillMapper.toSkillResponses(findSkillsByPosition(position)));
    }

    public void deletePosition(Long id) {
        positionRepository.delete(findById(id));
    }

    public Set<PositionSkill> findAllPositionSkillsByPosition(Position position) {
        return position.getPositionSkills();
    }

    public Position findPositionByName(String positionName) {
        return positionRepository.findByName(positionName)
                .orElseThrow(() -> new EntityNotFoundException(String.format("position.%s.not.found", positionName), 0L));
    }
}
