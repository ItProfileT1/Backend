package com.it.backend.repository;

import com.it.backend.entity.Position;
import com.it.backend.entity.PositionSkill;
import com.it.backend.entity.Skill;
import com.it.backend.entity.SkillLevel;
import org.springframework.data.repository.CrudRepository;

public interface PositionSkillRepository extends CrudRepository<PositionSkill, Long> {
    boolean existsByPositionAndSkillAndMinSkillLevel(Position position, Skill skill, SkillLevel minSkillLevel);
}
