package edu.utsa.cs3443.replog.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorkoutSession {

    private int sessionId;
    private int workoutId;          
    private String workoutName;     
    private LocalDate date;
    private ArrayList<SetEntry> sets;

    public WorkoutSession(int sessionId, int workoutId, String workoutName, LocalDate date) {
        if (workoutName == null || workoutName.isBlank()) {
            throw new IllegalArgumentException("Workout name cannot be empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.sessionId = sessionId;
        this.workoutId = workoutId;
        this.workoutName = workoutName;
        this.date = date;
        this.sets = new ArrayList<>();
    }

    public void addSet(SetEntry set) {
        if (set == null) {
            throw new IllegalArgumentException("Set cannot be null");
        }
        sets.add(set);
    }

    public boolean removeSet(SetEntry set) {
        return sets.remove(set);
    }

    public int getExerciseCount() {
        ArrayList<String> names = new ArrayList<>();
        for (SetEntry s : sets) {
            if (!names.contains(s.getExerciseName())) {
                names.add(s.getExerciseName());
            }
        }
        return names.size();
    }

    public String viewSession() 
    {
      int count = getExerciseCount();
      return date + " - " + workoutName + " Day   " + count
           + (count == 1 ? " exercise" : " exercises");
    }

    public int getSessionId() 
    { 
      return sessionId; 
    }
    public int getWorkoutId() 
    { 
      return workoutId; 
    }
    public String getWorkoutName() 
    { 
      return workoutName; 
    }
    public LocalDate getDate() 
    { 
      return date; 
    }
    public List<SetEntry> getSets() 
    { 
      return new ArrayList<>(sets); 
    }
}
