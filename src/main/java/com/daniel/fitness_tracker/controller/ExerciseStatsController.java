package com.daniel.fitness_tracker.controller;

import com.daniel.fitness_tracker.dto.OneRepMaxResponse;
import com.daniel.fitness_tracker.dto.VolumeResponse;
import com.daniel.fitness_tracker.model.SetEntry;
import com.daniel.fitness_tracker.repository.SetEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseStatsController {

    private static final Logger log = LoggerFactory.getLogger(ExerciseStatsController.class);
    private final SetEntryRepository setEntryRepository;

    public ExerciseStatsController(SetEntryRepository setEntryRepository) {
        this.setEntryRepository = setEntryRepository;
    }

    @GetMapping("/{exerciseId}/last-set")
    public LastSetResponse lastSet(
            @PathVariable Long exerciseId,
            @RequestParam(required = false) Long workoutId
    ) {
        Optional<SetEntry> lastOverall = setEntryRepository.findLastSetForExercise(exerciseId);
        Optional<SetEntry> lastInWorkout =
                (workoutId == null)
                        ? Optional.empty()
                        : setEntryRepository.findTopByWorkoutIdAndExerciseIdOrderBySetNumberDesc(workoutId, exerciseId);

        return new LastSetResponse(lastInWorkout.orElse(null), lastOverall.orElse(null));
    }

    @GetMapping("/{exerciseId}/volume")
    public VolumeResponse calculateVolume(
            @PathVariable Long exerciseId,
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        log.info("=== VOLUME CALCULATION DEBUG ===");
        log.info("Exercise ID: {}", exerciseId);
        log.info("Date range: {} to {}", from, to);

        // Get all sets for this exercise in the date range
        List<SetEntry> sets = setEntryRepository.findByExerciseIdAndWorkout_PerformedAtBetween(
                exerciseId, from, to
        );

        log.info("Found {} sets", sets.size());

        // Manual calculation
        Double totalVolume = null;
        if (!sets.isEmpty()) {
            totalVolume = sets.stream()
                    .peek(set -> log.info("Set: {} reps × {}kg = {}",
                            set.getReps(), set.getWeight(), set.getReps() * set.getWeight()))
                    .mapToDouble(set -> set.getReps() * set.getWeight())
                    .sum();
        }

        log.info("Total volume: {}", totalVolume);
        log.info("=== END DEBUG ===");

        return new VolumeResponse(exerciseId, totalVolume, from, to);
    }

    @GetMapping("/{exerciseId}/estimated-1rm")
    public OneRepMaxResponse estimateOneRepMax(@PathVariable Long exerciseId) {
        Optional<SetEntry> heaviest = setEntryRepository.findFirstByExerciseIdOrderByWeightDescRepsDesc(exerciseId);
        if (heaviest.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No sets found");
        }
        SetEntry set = heaviest.get();
        double estimatedMax = set.getWeight() * (1 + set.getReps() / 30.0);
        return new OneRepMaxResponse(exerciseId, estimatedMax, set.getWeight(), set.getReps());
    }

    public record LastSetResponse(SetEntry lastInWorkout, SetEntry lastOverall) {}
}