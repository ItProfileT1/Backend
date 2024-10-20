package com.it.backend.repository;

import com.it.backend.entity.Position;
import org.springframework.data.repository.CrudRepository;

public interface PositionRepository extends CrudRepository<Position, Long> {
}
