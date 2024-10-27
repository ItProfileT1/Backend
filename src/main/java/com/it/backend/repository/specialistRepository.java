package com.it.backend.repository;

import com.it.backend.entity.Specialist;
import com.it.backend.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface specialistRepository extends CrudRepository<Specialist, Long> {
    boolean existsByUser(User user);
}
