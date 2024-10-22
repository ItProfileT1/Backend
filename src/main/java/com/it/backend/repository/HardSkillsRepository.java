package com.it.backend.repository;

import com.it.backend.entity.HardSkill;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HardSkillsRepository extends CrudRepository<HardSkill, Long> {

    boolean existsHardSkillByName(String name);

    Optional<HardSkill> findHardSkillByName(String name);
}
