package com.daniel.fitness_tracker.dto;

import java.time.LocalDate;

public record VolumeResponse(
        Long exerciseId,
        Double totalVolume,
        LocalDate from,
        LocalDate to
){}