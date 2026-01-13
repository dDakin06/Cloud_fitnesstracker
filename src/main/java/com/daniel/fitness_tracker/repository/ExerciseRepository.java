package com.daniel.fitness_tracker.repository;

import com.daniel.fitness_tracker.model.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByMuscleGroupIdOrderByNameAsc(Long muscleGroupId);
    boolean existsByNameIgnoreCase(String name);
}
