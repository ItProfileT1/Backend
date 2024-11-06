package com.it.backend.repository;

import com.it.backend.entity.Type;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TypeRepository extends CrudRepository<Type, Long> {
    Optional<Type> findByName(String name);
}
