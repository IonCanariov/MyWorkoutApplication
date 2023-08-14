package com.example.applicatiofirst.model;


public class Exercise {
    private int exerciseId;
    private String exerciseName;
    private String description;
    private int workoutPlanId;

    public Exercise(int exerciseId, String exerciseName, String description, int workoutPlanId) {
        this.exerciseId = exerciseId;
        this.exerciseName = exerciseName;
        this.description = description;
        this.workoutPlanId = workoutPlanId;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public String getDescription() {
        return description;
    }

    public int getWorkoutPlanId() {
        return workoutPlanId;
    }
}
