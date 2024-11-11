package com.it.backend.service;

import com.it.backend.entity.Skill;
import com.it.backend.entity.Specialist;
import com.it.backend.entity.SpecialistSkill;
import com.it.backend.repository.SpecialistSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SpecialistSkillService {

    private final SkillService skillService;
    private final LevelService skillLevelService;
    private final PositionSkillService positionSkillService;
    private final SpecialistSkillRepository specialistSkillRepository;

    private SpecialistSkill createSpecialistSkill(Specialist specialist, Skill skill) {
        SpecialistSkill specialistSkill = new SpecialistSkill();
        specialistSkill.setSpecialist(specialist);
        specialistSkill.setSkill(skill);
        specialistSkill.setLevel(skillLevelService.findByNumericValue(0));
        specialistSkillRepository.save(specialistSkill);
        return specialistSkill;
    }

    @Transactional
    public Set<SpecialistSkill> create(Specialist specialist, Optional<Set<Long>> skillsIds) {
        Set<SpecialistSkill> specialistSkills = new HashSet<>();
        skillsIds.ifPresent(set -> {
            for (Long skillId : set) {
                var specialistSkill = createSpecialistSkill(specialist, skillService.findById(skillId));
                specialistSkills.add(specialistSkill);
            }
        });
        Optional.ofNullable(specialist.getPosition())
                .ifPresent((position -> {
                    var skillSet = positionSkillService.findSkillsByPosition(position);
                    for (Skill skill : skillSet) {
                        var specialistSkill = createSpecialistSkill(specialist, skill);
                        specialistSkills.add(specialistSkill);
                    }
                }));
        return specialistSkills;
    }

    public Set<Skill> getSkillsBySpecialist(Specialist specialist) {
        Set<Skill> skills = new HashSet<>();
        for (SpecialistSkill specialistSkillsLevel : specialist.getSpecialistSkillsLevels()) {
            var skill = specialistSkillsLevel.getSkill();
            if (skill != null){
                skills.add(skill);
            }
        }
        return skills;
    }
}
