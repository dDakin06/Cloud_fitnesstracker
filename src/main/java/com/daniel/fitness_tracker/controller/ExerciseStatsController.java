package com.daniel.fitness_tracker.controller;

import com.daniel.fitness_tracker.model.SetEntry;
import com.daniel.fitness_tracker.repository.SetEntryRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseStatsController {

    private final SetEntryRepository setEntryRepository;

    public ExerciseStatsController(SetEntryRepository setEntryRepository) {
        this.setEntryRepository = setEntryRepository;
    }

    // Example:
    // /api/exercises/5/last-set?workoutId=3
    @GetMapping("/{exerciseId}/last-set")
    public LastSetResponse lastSet(
            @PathVariable Long exerciseId,
            @RequestParam(required = false) Long workoutId
    ) {
        Optional<SetEntry> lastOverall =
                setEntryRepository.findTopByExerciseIdOrderByWorkout_PerformedAtDescCreatedAtDesc(exerciseId);

        Optional<SetEntry> lastInWorkout =
                (workoutId == null)
                        ? Optional.empty()
                        : setEntryRepository.findTopByWorkoutIdAndExerciseIdOrderBySetNumberDesc(workoutId, exerciseId);

        return new LastSetResponse(lastInWorkout.orElse(null), lastOverall.orElse(null));
    }

    record LastSetResponse(SetEntry lastInWorkout, SetEntry lastOverall) {}
}
