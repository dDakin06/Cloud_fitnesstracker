package com.daniel.fitness_tracker.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "workouts")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Date the workout is considered to have happened (for analytics)
    @Column(nullable = false)
    private LocalDate performedAt;

    // Exact start time (optional, more precise)
    private LocalDateTime startedAt;

    // Required by JPA
    protected Workout() {}

    public Workout(LocalDateTime startedAt, LocalDate performedAt) {
        this.startedAt = startedAt;
        this.performedAt = performedAt;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(LocalDate performedAt) {
        this.performedAt = performedAt;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }
}
