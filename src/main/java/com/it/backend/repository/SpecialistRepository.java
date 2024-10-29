package com.it.backend.repository;

import com.it.backend.entity.Specialist;
import com.it.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpecialistRepository extends CrudRepository<Specialist, Long> {
    boolean existsByUser(User user);

    Optional<Specialist> findByUser(User user);
}
