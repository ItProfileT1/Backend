package com.it.backend.repository;

import com.it.backend.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Level findByNumericValue(int numericValue);
}
