package com.daniel.fitness_tracker.dto;

public record OneRepMaxResponse(
        Long exerciseId,
        Double estimatedOneRepMax,
        Double heaviestWeight,
        Integer repsAtHeaviest
) {}