package com.it.backend.repository;

import com.it.backend.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    Level findByNumericValue(int numericValue);
}
