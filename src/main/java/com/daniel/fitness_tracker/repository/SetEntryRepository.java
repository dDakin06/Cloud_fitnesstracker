package com.daniel.fitness_tracker.repository;

import com.daniel.fitness_tracker.dto.MuscleGroupSetCountResponse;
import com.daniel.fitness_tracker.model.SetEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SetEntryRepository extends JpaRepository<SetEntry, Long> {

    // 1) Last set for an exercise INSIDE a specific workout
    Optional<SetEntry> findTopByWorkoutIdAndExerciseIdOrderBySetNumberDesc(
            Long workoutId,
            Long exerciseId
    );

    // 2) All sets for an exercise INSIDE a specific workout (ordered 1,2,3,...)
    List<SetEntry> findByWorkoutIdAndExerciseIdOrderBySetNumberAsc(
            Long workoutId,
            Long exerciseId
    );

    // 3) Last set for an exercise ACROSS ALL workouts (overall last performed)
    @Query("""
        SELECT se
        FROM SetEntry se
        JOIN se.workout w
        WHERE se.exercise.id = :exerciseId
        ORDER BY w.performedAt DESC, se.createdAt DESC
    """)
    List<SetEntry> findLastSetForExerciseRaw(@Param("exerciseId") Long exerciseId);

    default Optional<SetEntry> findLastSetForExercise(Long exerciseId) {
        return findLastSetForExerciseRaw(exerciseId).stream().findFirst();
    }

    // 4) Analytics: count sets by muscle group between dates
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
        ORDER BY COUNT(se) DESC
    """)
    List<MuscleGroupSetCountResponse> countSetsByMuscleGroupBetween(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    // 5) Calculate total volume for an exercise
    @Query("""
        SELECT SUM(se.weight * se.reps)
        FROM SetEntry se
        JOIN se.workout w
        WHERE se.exercise.id = :exerciseId
        AND w.performedAt BETWEEN :from AND :to
    """)
    Double calculateTotalVolume(
            @Param("exerciseId") Long exerciseId,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    // 6) Find heaviest set for an exercise (for 1RM estimation)
    Optional<SetEntry> findFirstByExerciseIdOrderByWeightDescRepsDesc(Long exerciseId);
}