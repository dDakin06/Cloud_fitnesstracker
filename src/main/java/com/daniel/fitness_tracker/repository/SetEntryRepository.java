package com.daniel.fitness_tracker.repository;

import com.daniel.fitness_tracker.model.SetEntry;
import com.daniel.fitness_tracker.dto.MuscleGroupSetCountResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SetEntryRepository extends JpaRepository<SetEntry, Long> {

    // Last set for this exercise in a specific workout
    Optional<SetEntry> findTopByWorkoutIdAndExerciseIdOrderBySetNumberDesc(
            Long workoutId,
            Long exerciseId
    );

    // Last set for this exercise across all workouts
    Optional<SetEntry> findTopByExerciseIdOrderByWorkout_PerformedAtDescCreatedAtDesc(
            Long exerciseId
    );
    @Query("""
    SELECT new com.daniel.fitness_tracker.dto.MuscleGroupSetCountResponse(
        mg.name,
        COUNT(se)
    )
    FROM SetEntry se
    JOIN se.exercise e
    JOIN e.muscleGroup mg
    JOIN se.workout w
    WHERE w.performedAt BETWEEN :from AND :to
    GROUP BY mg.name
""")
    List<MuscleGroupSetCountResponse> countSetsByMuscleGroupBetween(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

}
