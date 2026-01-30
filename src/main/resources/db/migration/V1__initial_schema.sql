-- V1__initial_schema.sql
-- Initial schema for Fitness Tracker application
-- Creates: muscle_groups, exercises, workouts, set_entries tables

-- ============================================================================
-- 1. MUSCLE_GROUPS TABLE
-- ============================================================================
CREATE TABLE muscle_groups (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    CONSTRAINT uk_muscle_groups_name UNIQUE (name)
);

-- ============================================================================
-- 2. EXERCISES TABLE
-- ============================================================================
CREATE TABLE exercises (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    muscle_group_id BIGINT NOT NULL,
    CONSTRAINT uk_exercises_name UNIQUE (name),
    CONSTRAINT fk_exercises_muscle_group 
        FOREIGN KEY (muscle_group_id) 
        REFERENCES muscle_groups(id)
);

-- ============================================================================
-- 3. WORKOUTS TABLE
-- ============================================================================
CREATE TABLE workouts (
    id BIGSERIAL PRIMARY KEY,
    performed_at DATE NOT NULL,
    started_at TIMESTAMP
);

-- ============================================================================
-- 4. SET_ENTRIES TABLE
-- ============================================================================
CREATE TABLE set_entries (
    id BIGSERIAL PRIMARY KEY,
    workout_id BIGINT NOT NULL,
    exercise_id BIGINT NOT NULL,
    reps INTEGER NOT NULL,
    weight DOUBLE PRECISION NOT NULL,
    set_number INTEGER NOT NULL,
    rir INTEGER,
    created_at TIMESTAMP NOT NULL,
    
    CONSTRAINT fk_set_entries_workout 
        FOREIGN KEY (workout_id) 
        REFERENCES workouts(id),
    
    CONSTRAINT fk_set_entries_exercise 
        FOREIGN KEY (exercise_id) 
        REFERENCES exercises(id),
    
    CONSTRAINT uk_workout_exercise_setnumber 
        UNIQUE (workout_id, exercise_id, set_number)
);

-- ============================================================================
-- INDEXES FOR PERFORMANCE
-- ============================================================================

-- Index for finding sets by exercise and date range (volume calculations)
CREATE INDEX idx_set_entries_exercise_workout 
    ON set_entries(exercise_id, workout_id);

-- Index for finding last set by exercise (last-set queries)
CREATE INDEX idx_set_entries_exercise_created 
    ON set_entries(exercise_id, created_at DESC);

-- Index for workout date range queries (analytics)
CREATE INDEX idx_workouts_performed_at 
    ON workouts(performed_at);

-- Index for exercises by muscle group (filtering)
CREATE INDEX idx_exercises_muscle_group 
    ON exercises(muscle_group_id);
