package com.it.backend.repository;

import com.it.backend.entity.Level;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SkillLevelRepository extends CrudRepository<Level, Long> {
    Optional<Level> findByNumericValue(Integer value);
}
