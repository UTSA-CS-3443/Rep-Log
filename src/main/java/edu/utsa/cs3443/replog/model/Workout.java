package edu.utsa.cs3443.replog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Workout {
    private final int workoutId;
    private final String name;
    private final String category;
    private final ArrayList<Exercise> exercises = new ArrayList<>();

    public Workout(int workoutId, String name, String category) {
        this.workoutId = workoutId;
        this.name = name;
        this.category = category;
    }

    public int getWorkoutId() {
        return workoutId;
    }

    public String getName() {
        return name;
    }

    public String getCategory(){
        return category;
    }

    public List<Exercise> getExercises() {
        return List.copyOf(exercises);
    }

    public void addExercise(Exercise exercise) {
        exercises.add(Objects.requireNonNull(exercise, "exercise"));
    }

    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }
}