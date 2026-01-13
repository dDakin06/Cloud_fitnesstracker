package com.daniel.fitness_tracker.controller;

import com.daniel.fitness_tracker.dto.CreateWorkoutRequest;
import com.daniel.fitness_tracker.dto.WorkoutResponse;
import com.daniel.fitness_tracker.model.Workout;
import com.daniel.fitness_tracker.repository.WorkoutRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutRepository workoutRepository;

    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutResponse createWorkout(@RequestBody CreateWorkoutRequest request) {

        Workout workout = new Workout(
                request.startedAt(),
                request.performedAt()
        );

        Workout saved = workoutRepository.save(workout);

        return new WorkoutResponse(saved.getId(), saved.getPerformedAt(), saved.getStartedAt());
    }
}
