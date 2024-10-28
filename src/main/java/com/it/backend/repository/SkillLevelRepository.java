package com.it.backend.repository;

import com.it.backend.entity.SkillLevel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SkillLevelRepository extends CrudRepository<SkillLevel, Long> {
    Optional<SkillLevel> findByNumericValue(Integer value);
}
