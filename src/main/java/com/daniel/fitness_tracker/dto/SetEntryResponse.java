package com.daniel.fitness_tracker.dto;

import java.time.Instant;

public record SetEntryResponse(
        Long id,
        Long workoutId,
        Long exerciseId,
        int reps,
        double weight,
        int setNumber,
        Integer rir,
        Instant createdAt
) {}
