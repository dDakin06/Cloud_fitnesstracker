package com.daniel.fitness_tracker.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "muscle_groups",
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class MuscleGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    // Required by JPA
    protected MuscleGroup() {}

    public MuscleGroup(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
