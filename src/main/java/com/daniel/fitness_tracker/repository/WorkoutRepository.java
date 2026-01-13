package com.daniel.fitness_tracker.repository;

import com.daniel.fitness_tracker.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
}
