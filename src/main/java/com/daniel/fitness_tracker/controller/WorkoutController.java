package com.daniel.fitness_tracker.controller;

import com.daniel.fitness_tracker.dto.OneRepMaxResponse;
import com.daniel.fitness_tracker.dto.VolumeResponse;
import com.daniel.fitness_tracker.dto.CreateWorkoutRequest;
import com.daniel.fitness_tracker.dto.WorkoutResponse;
import com.daniel.fitness_tracker.model.Workout;
import com.daniel.fitness_tracker.repository.WorkoutRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutRepository workoutRepository;

    public WorkoutController(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }



    @GetMapping
    public List<WorkoutResponse> getALLWorkouts(){
        return workoutRepository.findAll()
                .stream()
                .map(w -> new WorkoutResponse(
                        w.getId(),
                        w.getPerformedAt(),
                        w.getStartedAt()
                ))
                .toList();
    }
    @GetMapping("/{id}")
    public WorkoutResponse getWorkoutById(@PathVariable Long id){
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workout not found"));
        return new WorkoutResponse(
                workout.getId(),
                workout.getPerformedAt(),
                workout.getStartedAt()
        );
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkoutResponse createWorkout(@Valid @RequestBody CreateWorkoutRequest request) {

        Workout workout = new Workout(
                request.startedAt(),
                request.performedAt()
        );

        Workout saved = workoutRepository.save(workout);

        return new WorkoutResponse(saved.getId(), saved.getPerformedAt(), saved.getStartedAt());
    }
}