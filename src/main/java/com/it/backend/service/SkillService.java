package com.it.backend.service;

import com.it.backend.dto.request.SkillRequest;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.entity.Skill;
import com.it.backend.exception.entity.DuplicateEntityException;
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
    private final TypeService typeService;
    private final CategoryService categoryService;
    private final ScaleService scaleService;
    private final SkillMapper skillMapper;

    public Skill findById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("skill.not.found", id));
    }

    public Set<SkillResponse> findAll(String typeName) {
        if (typeName == null) {
            return skillMapper.toSkillResponses(skillRepository.findAll());
        }
        Set<Skill> skills = new HashSet<>();
        for (Skill skill : skillRepository.findAll()) {
            if (typeService.findByName(typeName).equals(skill.getType())) {
                skills.add(skill);
            }
        }
        return skillMapper.toSkillResponses(skills);
    }

    public SkillResponse create(SkillRequest request) {
        Skill skill = skillMapper.toSkill(
                request,
                typeService.findById(request.typeId()),
                request.categoryId().map(categoryService::findById).orElse(null),
                scaleService.findById(request.scaleId()));
        if (skillRepository.existsByName(skill.getName())) {
            throw new DuplicateEntityException("scale.already.exists", request.name());
        }
        skillRepository.save(skill);
        return skillMapper.toSkillResponse(skill);
    }

    public void delete(Long id) {
        var skill = findById(id);
        skillRepository.delete(skill);
    }
}
