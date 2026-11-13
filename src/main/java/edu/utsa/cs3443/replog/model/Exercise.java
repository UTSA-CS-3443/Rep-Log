public class Exercise {
    private int exerciseId;
    private String name;
    private int targetSets;
    private int targetReps;
    private double targetWeight;   // lbs, 0 = bodyweight

    public Exercise(int exerciseId, String name, int targetSets, int targetReps, double targetWeight) {
        this.exerciseId = exerciseId;
        setName(name);
        setTargetSets(targetSets);
        setTargetReps(targetReps);
        setTargetWeight(targetWeight);
    }

    public void updateExercise(String name, int targetSets, int targetReps, double targetWeight) {
        setName(name);
        setTargetSets(targetSets);
        setTargetReps(targetReps);
        setTargetWeight(targetWeight);
    }

    public double calculateVolume() {
        return targetSets * targetReps * targetWeight;
    }

    

    public int getExerciseId() { return exerciseId; }
    public String getName() { return name; }
    public int getTargetSets() { return targetSets; }
    public int getTargetReps() { return targetReps; }
    public double getTargetWeight() { return targetWeight; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Exercise name can't be empty.");
        }
        this.name = name.trim();
    }

    public void setTargetSets(int targetSets) {
        if (targetSets < 1) {
            throw new IllegalArgumentException("Sets must be at least 1.");
        }
        this.targetSets = targetSets;
    }

    public void setTargetReps(int targetReps) {
        if (targetReps < 1) {
            throw new IllegalArgumentException("Reps must be at least 1.");
        }
        this.targetReps = targetReps;
    }

    public void setTargetWeight(double targetWeight) {
        if (targetWeight < 0) {
            throw new IllegalArgumentException("Weight can't be negative.");
        }
        this.targetWeight = targetWeight;
    }
}
