package edu.utsa.cs3443.replog.model;

public class SetEntry {

    private String exerciseName;
    private int setNumber;
    private int reps;
    private double weight;

    public SetEntry(String exerciseName, int setNumber, int reps, double weight) {
        this.exerciseName = exerciseName;
        this.setNumber = setNumber;
        this.reps = reps;
        this.weight = weight;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getSetNumber() {
        return setNumber;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getVolume() {
        return reps * weight;
    }
}
