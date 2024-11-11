package com.it.backend.repository;

import com.it.backend.entity.Level;
import com.it.backend.entity.Skill;
import com.it.backend.entity.SkillLevel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SkillLevelRepository extends CrudRepository<SkillLevel, Long> {
    SkillLevel findBySkillAndLevel(Skill skill, Level level);
}
