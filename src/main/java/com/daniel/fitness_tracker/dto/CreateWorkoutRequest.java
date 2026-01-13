package com.daniel.fitness_tracker.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateWorkoutRequest(
        LocalDate performedAt,
        LocalDateTime startedAt
) {}
