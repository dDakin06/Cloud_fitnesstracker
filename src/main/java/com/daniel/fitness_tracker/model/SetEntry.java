package com.daniel.fitness_tracker.model;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(
        name = "set_entries",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_workout_exercise_setnumber",
                        columnNames = {"workout_id", "exercise_id", "set_number"}
                )
        }
)
public class SetEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(nullable = false)
    private int reps;

    @Column(nullable = false)
    private double weight;

    @Column(name = "set_number", nullable = false)
    private int setNumber;

    @Column(nullable = true)
    private Integer rir;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    // Required by JPA
    protected SetEntry() {}

    public SetEntry(Workout workout, Exercise exercise, int reps, double weight, int setNumber, Integer rir) {
        this.workout = workout;
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
        this.setNumber = setNumber;
        this.rir = rir;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = Instant.now();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Workout getWorkout() {
        return workout;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public int getReps() {
        return reps;
    }

    public double getWeight() {
        return weight;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public Integer getRir() {
        return rir;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    // Setters
    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSetNumber(int setNumber) {
        this.setNumber = setNumber;
    }

    public void setRir(Integer rir) {
        this.rir = rir;
    }
}