package com.daniel.fitness_tracker.config;

import com.daniel.fitness_tracker.model.*;
import com.daniel.fitness_tracker.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(
            MuscleGroupRepository muscleGroupRepo,
            ExerciseRepository exerciseRepo,
            WorkoutRepository workoutRepo,
            SetEntryRepository setEntryRepo
    ) {
        return args -> {

            if (muscleGroupRepo.count() == 0) {

                MuscleGroup chest = muscleGroupRepo.save(new MuscleGroup("CHEST"));
                MuscleGroup back = muscleGroupRepo.save(new MuscleGroup("BACK"));
                MuscleGroup legs = muscleGroupRepo.save(new MuscleGroup("LEGS"));
                MuscleGroup shoulders = muscleGroupRepo.save(new MuscleGroup("SHOULDERS"));
                MuscleGroup arms = muscleGroupRepo.save(new MuscleGroup("ARMS"));


                Exercise bench = exerciseRepo.save(new Exercise("Bench Press", chest));
                Exercise squat = exerciseRepo.save(new Exercise("Squat", legs));
                Exercise row = exerciseRepo.save(new Exercise("Barbell Row", back));
                Exercise shoulderPress = exerciseRepo.save(new Exercise("Overhead Press", shoulders));


                Workout workout1 = workoutRepo.save(
                        new Workout(LocalDateTime.now().minusDays(2), LocalDate.now().minusDays(2))
                );

                Workout workout2 = workoutRepo.save(
                        new Workout(LocalDateTime.now().minusDays(1), LocalDate.now().minusDays(1))
                );


                setEntryRepo.save(new SetEntry(workout1, bench, 10, 60, 1, 2));
                setEntryRepo.save(new SetEntry(workout1, bench, 8, 65, 2, 1));
                setEntryRepo.save(new SetEntry(workout1, squat, 5, 100, 1, 2));

                setEntryRepo.save(new SetEntry(workout2, bench, 10, 62.5, 1, 2));
                setEntryRepo.save(new SetEntry(workout2, row, 8, 70, 1, 1));
                setEntryRepo.save(new SetEntry(workout2, shoulderPress, 8, 40, 1, 2));
            }
        };
    }
}
