package com.daniel.fitness_tracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Max;

public record CreateSetEntryRequest(

        @NotNull(message = "workoutId is required")
        @Positive(message = "workoutId must be > 0")
        Long workoutId,

        @NotNull(message = "exerciseId is required")
        @Positive(message = "exerciseId must be > 0")
        Long exerciseId,

        @Min(value = 1, message = "reps must be at least 1")
        int reps,

        @PositiveOrZero(message = "weight must be >= 0")
        double weight,

        @Min(value = 1, message = "setNumber must be at least 1")
        int setNumber,


        @Min(value = 0, message = "rir must be between 0 and 10")
        @Max(value = 10, message = "rir must be between 0 and 10")
        Integer rir
) {}
