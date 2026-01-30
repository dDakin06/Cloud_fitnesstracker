-- V2__seed_data.sql
-- Seed data for local development and testing
-- H2-compatible version (uses MERGE instead of ON CONFLICT)

-- ============================================================================
-- SEED MUSCLE GROUPS
-- ============================================================================
MERGE INTO muscle_groups (name) KEY(name) VALUES ('CHEST');
MERGE INTO muscle_groups (name) KEY(name) VALUES ('BACK');
MERGE INTO muscle_groups (name) KEY(name) VALUES ('LEGS');
MERGE INTO muscle_groups (name) KEY(name) VALUES ('SHOULDERS');
MERGE INTO muscle_groups (name) KEY(name) VALUES ('ARMS');
MERGE INTO muscle_groups (name) KEY(name) VALUES ('CORE');

-- ============================================================================
-- SEED EXERCISES
-- ============================================================================
-- Chest exercises
MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Bench Press', (SELECT id FROM muscle_groups WHERE name = 'CHEST'));

MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Incline Dumbbell Press', (SELECT id FROM muscle_groups WHERE name = 'CHEST'));

MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Cable Flyes', (SELECT id FROM muscle_groups WHERE name = 'CHEST'));

-- Back exercises
MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Barbell Row', (SELECT id FROM muscle_groups WHERE name = 'BACK'));

MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Pull-ups', (SELECT id FROM muscle_groups WHERE name = 'BACK'));

MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Lat Pulldown', (SELECT id FROM muscle_groups WHERE name = 'BACK'));

-- Leg exercises
MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Squat', (SELECT id FROM muscle_groups WHERE name = 'LEGS'));

MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Romanian Deadlift', (SELECT id FROM muscle_groups WHERE name = 'LEGS'));

MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Leg Press', (SELECT id FROM muscle_groups WHERE name = 'LEGS'));

-- Shoulder exercises
MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Overhead Press', (SELECT id FROM muscle_groups WHERE name = 'SHOULDERS'));

MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Lateral Raises', (SELECT id FROM muscle_groups WHERE name = 'SHOULDERS'));

-- Arm exercises
MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Barbell Curl', (SELECT id FROM muscle_groups WHERE name = 'ARMS'));

MERGE INTO exercises (name, muscle_group_id) KEY(name) 
VALUES ('Tricep Pushdown', (SELECT id FROM muscle_groups WHERE name = 'ARMS'));

-- ============================================================================
-- SEED SAMPLE WORKOUTS (Last 3 days)
-- ============================================================================

-- Workout 1 (2 days ago)
INSERT INTO workouts (performed_at, started_at) VALUES 
    (CURRENT_DATE - 2, CURRENT_TIMESTAMP - 2);

-- Workout 2 (1 day ago)
INSERT INTO workouts (performed_at, started_at) VALUES 
    (CURRENT_DATE - 1, CURRENT_TIMESTAMP - 1);

-- Workout 3 (today)
INSERT INTO workouts (performed_at, started_at) VALUES 
    (CURRENT_DATE, CURRENT_TIMESTAMP);

-- ============================================================================
-- SEED SAMPLE SET ENTRIES
-- ============================================================================

-- Workout 1: Chest & Triceps
INSERT INTO set_entries (workout_id, exercise_id, reps, weight, set_number, rir, created_at) VALUES
    -- Bench Press (3 sets)
    (1, (SELECT id FROM exercises WHERE name = 'Bench Press'), 10, 60.0, 1, 2, CURRENT_TIMESTAMP - 2),
    (1, (SELECT id FROM exercises WHERE name = 'Bench Press'), 8, 65.0, 2, 1, CURRENT_TIMESTAMP - 2),
    (1, (SELECT id FROM exercises WHERE name = 'Bench Press'), 6, 67.5, 3, 0, CURRENT_TIMESTAMP - 2),
    
    -- Incline Dumbbell Press (3 sets)
    (1, (SELECT id FROM exercises WHERE name = 'Incline Dumbbell Press'), 10, 25.0, 1, 2, CURRENT_TIMESTAMP - 2),
    (1, (SELECT id FROM exercises WHERE name = 'Incline Dumbbell Press'), 8, 27.5, 2, 1, CURRENT_TIMESTAMP - 2),
    (1, (SELECT id FROM exercises WHERE name = 'Incline Dumbbell Press'), 8, 27.5, 3, 1, CURRENT_TIMESTAMP - 2),
    
    -- Tricep Pushdown (3 sets)
    (1, (SELECT id FROM exercises WHERE name = 'Tricep Pushdown'), 12, 30.0, 1, 2, CURRENT_TIMESTAMP - 2),
    (1, (SELECT id FROM exercises WHERE name = 'Tricep Pushdown'), 10, 32.5, 2, 1, CURRENT_TIMESTAMP - 2),
    (1, (SELECT id FROM exercises WHERE name = 'Tricep Pushdown'), 10, 32.5, 3, 1, CURRENT_TIMESTAMP - 2);

-- Workout 2: Back & Biceps
INSERT INTO set_entries (workout_id, exercise_id, reps, weight, set_number, rir, created_at) VALUES
    -- Barbell Row (3 sets)
    (2, (SELECT id FROM exercises WHERE name = 'Barbell Row'), 8, 70.0, 1, 2, CURRENT_TIMESTAMP - 1),
    (2, (SELECT id FROM exercises WHERE name = 'Barbell Row'), 8, 70.0, 2, 1, CURRENT_TIMESTAMP - 1),
    (2, (SELECT id FROM exercises WHERE name = 'Barbell Row'), 6, 72.5, 3, 0, CURRENT_TIMESTAMP - 1),
    
    -- Pull-ups (3 sets)
    (2, (SELECT id FROM exercises WHERE name = 'Pull-ups'), 8, 0.0, 1, 2, CURRENT_TIMESTAMP - 1),
    (2, (SELECT id FROM exercises WHERE name = 'Pull-ups'), 7, 0.0, 2, 1, CURRENT_TIMESTAMP - 1),
    (2, (SELECT id FROM exercises WHERE name = 'Pull-ups'), 6, 0.0, 3, 0, CURRENT_TIMESTAMP - 1),
    
    -- Barbell Curl (3 sets)
    (2, (SELECT id FROM exercises WHERE name = 'Barbell Curl'), 10, 30.0, 1, 2, CURRENT_TIMESTAMP - 1),
    (2, (SELECT id FROM exercises WHERE name = 'Barbell Curl'), 8, 32.5, 2, 1, CURRENT_TIMESTAMP - 1),
    (2, (SELECT id FROM exercises WHERE name = 'Barbell Curl'), 8, 32.5, 3, 1, CURRENT_TIMESTAMP - 1);

-- Workout 3: Legs
INSERT INTO set_entries (workout_id, exercise_id, reps, weight, set_number, rir, created_at) VALUES
    -- Squat (4 sets)
    (3, (SELECT id FROM exercises WHERE name = 'Squat'), 5, 100.0, 1, 3, CURRENT_TIMESTAMP),
    (3, (SELECT id FROM exercises WHERE name = 'Squat'), 5, 100.0, 2, 2, CURRENT_TIMESTAMP),
    (3, (SELECT id FROM exercises WHERE name = 'Squat'), 5, 100.0, 3, 1, CURRENT_TIMESTAMP),
    (3, (SELECT id FROM exercises WHERE name = 'Squat'), 3, 105.0, 4, 0, CURRENT_TIMESTAMP),
    
    -- Romanian Deadlift (3 sets)
    (3, (SELECT id FROM exercises WHERE name = 'Romanian Deadlift'), 8, 80.0, 1, 2, CURRENT_TIMESTAMP),
    (3, (SELECT id FROM exercises WHERE name = 'Romanian Deadlift'), 8, 80.0, 2, 1, CURRENT_TIMESTAMP),
    (3, (SELECT id FROM exercises WHERE name = 'Romanian Deadlift'), 6, 85.0, 3, 0, CURRENT_TIMESTAMP);
