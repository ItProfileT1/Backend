package com.it.backend.repository;

import com.it.backend.entity.Position;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PositionRepository extends CrudRepository<Position, Long> {
    boolean existsByName(String name);

    Optional<Position> findByName(String position);
}
