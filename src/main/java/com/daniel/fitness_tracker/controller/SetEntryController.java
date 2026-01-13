package com.daniel.fitness_tracker.controller;

import com.daniel.fitness_tracker.dto.CreateSetEntryRequest;
import com.daniel.fitness_tracker.dto.SetEntryResponse;
import com.daniel.fitness_tracker.model.Exercise;
import com.daniel.fitness_tracker.model.SetEntry;
import com.daniel.fitness_tracker.model.Workout;
import com.daniel.fitness_tracker.repository.ExerciseRepository;
import com.daniel.fitness_tracker.repository.SetEntryRepository;
import com.daniel.fitness_tracker.repository.WorkoutRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/sets")
public class SetEntryController {

    private final SetEntryRepository setEntryRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;

    public SetEntryController(SetEntryRepository setEntryRepository,
                              WorkoutRepository workoutRepository,
                              ExerciseRepository exerciseRepository) {
        this.setEntryRepository = setEntryRepository;
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SetEntryResponse createSet(@Valid @RequestBody CreateSetEntryRequest request) {

        Workout workout = workoutRepository.findById(request.workoutId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Workout not found"));

        Exercise exercise = exerciseRepository.findById(request.exerciseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exercise not found"));

        SetEntry setEntry = new SetEntry(
                workout,
                exercise,
                request.reps(),
                request.weight(),
                request.setNumber(),
                request.rir()
        );

        SetEntry saved = setEntryRepository.save(setEntry);

        return new SetEntryResponse(
                saved.getId(),
                saved.getWorkout().getId(),
                saved.getExercise().getId(),
                saved.getReps(),
                saved.getWeight(),
                saved.getSetNumber(),
                saved.getRir(),
                saved.getCreatedAt()
        );
    }
}
