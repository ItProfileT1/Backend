package com.it.backend.repository;

import com.it.backend.entity.Specialist;
import org.springframework.data.repository.CrudRepository;

public interface SpecialistRepository extends CrudRepository<Specialist, Long> {
    boolean existsByFio(String fio);
}
