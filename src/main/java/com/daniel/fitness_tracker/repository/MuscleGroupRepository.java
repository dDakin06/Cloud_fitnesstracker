package com.daniel.fitness_tracker.repository;

import com.daniel.fitness_tracker.model.MuscleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MuscleGroupRepository extends JpaRepository<MuscleGroup, Long> {
    Optional<MuscleGroup> findByNameIgnoreCase(String name);
}
