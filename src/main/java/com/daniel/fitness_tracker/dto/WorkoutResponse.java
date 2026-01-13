package com.daniel.fitness_tracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record WorkoutResponse(
        Long id,
        LocalDate performedAt,
        LocalDateTime startedAt
) {}
