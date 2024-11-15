package com.it.backend.service;

import com.it.backend.dto.request.SkillRequest;
import com.it.backend.dto.request.SkillTechRadarRequest;
import com.it.backend.dto.response.SkillResponse;
import com.it.backend.entity.Level;
import com.it.backend.entity.Skill;
import com.it.backend.entity.Specialist;
import com.it.backend.entity.SpecialistSkill;
import com.it.backend.exception.entity.DuplicateEntityException;
import com.it.backend.exception.entity.EntityNotFoundException;
import com.it.backend.mapper.SkillMapper;
import com.it.backend.repository.SkillRepository;
import com.it.backend.repository.SpecialistRepository;
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
    private final SpecialistRepository specialistRepository;

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
            throw new DuplicateEntityException("skill.already.exists", request.name());
        }
        skillRepository.save(skill);
        return skillMapper.toSkillResponse(skill);
    }

    public void delete(Long id) {
        var skill = findById(id);
        skillRepository.delete(skill);
    }

    public Set<SkillTechRadarRequest> getAllSkillTechRadarRequests() {
        var skills = skillRepository.findAll();
        Set<SkillTechRadarRequest> requests = new HashSet<>();
        var specialistsAmount = specialistRepository.count();
        for (Skill skill : skills) {
            if (!skill.getType().getId().equals(1L))
                continue;
            double usages;
            var skillSpecialistsLevels = skill.getSkillSpecialistsLevels();
            if (skillSpecialistsLevels == null) {
                usages = 0;
            }
            else {
                usages = skillSpecialistsLevels.size();
            }
            var usageLevel = usages / specialistsAmount;
            var request = skillMapper.toSkillTechRadarRequest(skill, usageLevel);
            requests.add(request);
        }
        return requests;
    }

    public boolean existsBySpecialist(Specialist specialist, String skillName) {
        for (SpecialistSkill specialistSkillsLevel : specialist.getSpecialistSkillsLevels()) {
            if (specialistSkillsLevel.getSkill().getName().equals(skillName))
                return true;
        }
        return false;
    }

    public Level getLevelBySpecialistSkill(Specialist specialist, String skill) {
        for (SpecialistSkill specialistSkillsLevel : specialist.getSpecialistSkillsLevels()) {
            if (specialistSkillsLevel.getSkill().getName().equals(skill)){
                return specialistSkillsLevel.getLevel();
            }
        }
        throw new EntityNotFoundException(String.format("skill.%s.for.specialist.with.position.%s.not.found", skill, specialist.getPosition().getName()), 0L);
    }
}
