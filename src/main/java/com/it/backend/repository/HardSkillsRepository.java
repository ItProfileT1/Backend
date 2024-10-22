package com.it.backend.repository;

import com.it.backend.entity.HardSkill;
import org.springframework.data.repository.CrudRepository;

public interface HardSkillsRepository extends CrudRepository<HardSkill, Long> {
    boolean existsHardSkillByName(String name);
}
