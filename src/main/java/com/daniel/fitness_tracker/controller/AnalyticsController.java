package com.daniel.fitness_tracker.controller;

import com.daniel.fitness_tracker.dto.MuscleGroupSetCountResponse;
import com.daniel.fitness_tracker.repository.SetEntryRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final SetEntryRepository setEntryRepository;

    public AnalyticsController(SetEntryRepository setEntryRepository) {
        this.setEntryRepository = setEntryRepository;
    }

    @GetMapping("/sets-by-muscle-group")
    public List<MuscleGroupSetCountResponse> setsByMuscleGroup(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to
    ) {
        return setEntryRepository.countSetsByMuscleGroupBetween(from, to);
    }
}
