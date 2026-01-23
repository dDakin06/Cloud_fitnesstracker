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
import java.util.List;

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
    @GetMapping
    public List<SetEntryResponse> getAllSets() {
        return setEntryRepository.findAll()
                .stream()
                .map(s -> new SetEntryResponse(
                        s.getId(),
                        s.getWorkout().getId(),
                        s.getExercise().getId(),
                        s.getReps(),
                        s.getWeight(),
                        s.getSetNumber(),
                        s.getRir(),
                        s.getCreatedAt()
                ))
                .toList();
    }
    @GetMapping("/by-workout-exercise")
    public List<SetEntryResponse> getSetsForWorkoutAndExcercise(
            @RequestParam Long workoutId,
            @RequestParam Long exerciseId
    ){
        return setEntryRepository
                .findByWorkoutIdAndExerciseIdOrderBySetNumberAsc(workoutId, exerciseId)
                .stream()
                .map(s -> new SetEntryResponse(
                        s.getId(),
                        s.getWorkout().getId(),
                        s.getExercise().getId(),
                        s.getReps(),
                        s.getWeight(),
                        s.getSetNumber(),
                        s.getRir(),
                        s.getCreatedAt()
                ))
                .toList();
    }

}
