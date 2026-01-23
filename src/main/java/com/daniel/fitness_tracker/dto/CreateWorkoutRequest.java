package com.daniel.fitness_tracker.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CreateWorkoutRequest(
        @NotNull(message = "performedAt date is required")
        LocalDate performedAt,

        LocalDateTime startedAt  // Optional - can be null
) {}