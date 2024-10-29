package com.it.backend.repository;

import com.it.backend.entity.Skill;
import com.it.backend.entity.Specialist;
import com.it.backend.entity.SpecialistSkill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SpecialistSkillRepository extends JpaRepository<SpecialistSkill, Long> {
    Page<SpecialistSkill> findBySpecialist(Specialist specialist, Pageable pageable);

    SpecialistSkill findBySpecialistAndSkill(Specialist specialist, Skill skill);

    boolean existsBySpecialistAndSkill(Specialist specialist, Skill skill);

    @Query(value = "SELECT s.skill FROM SpecialistSkill s WHERE s.specialist = :specialist " +
            "AND s.level.numericValue = 0")
    Set<Skill> findAllUnratedSkillsBySpecialist(Specialist specialist);
}
