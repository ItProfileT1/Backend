package com.it.backend.repository;

import com.it.backend.entity.Skill;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SkillRepository extends CrudRepository<Skill, Long> {
    Optional<Skill> findByName(String name);
}
