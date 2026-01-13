package com.daniel.fitness_tracker.controller;

import com.daniel.fitness_tracker.model.MuscleGroup;
import com.daniel.fitness_tracker.repository.MuscleGroupRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/muscle-groups")
public class MuscleGroupController {

    private final MuscleGroupRepository muscleGroupRepository;

    public MuscleGroupController(MuscleGroupRepository muscleGroupRepository) {
        this.muscleGroupRepository = muscleGroupRepository;
    }

    @GetMapping
    public List<MuscleGroup> list() {
        return muscleGroupRepository.findAll();
    }
}
