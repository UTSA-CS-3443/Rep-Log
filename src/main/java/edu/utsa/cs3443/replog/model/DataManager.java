package edu.utsa.cs3443.replog.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {

    private ArrayList<User> users;
    private final String usersDataFilename;
    private User currentUser;

    private ArrayList<WorkoutSession> sessions;
    private ArrayList<int[]> history;
    private final String sessionsDataFilename;
    private final String setsDataFilename;
    private final String historyDataFilename;

    private ArrayList<Exercise> exercises;
    private ArrayList<Workout> workouts;
    private final String exercisesDataFilename;
    private final String workoutsDataFilename;
    private final String workoutExercisesDataFilename;
    private String currentCategory;
    private Workout currentWorkout;

    private static DataManager instance;

    private DataManager() {
        users = new ArrayList<>();
        usersDataFilename = "data/Users.csv";

        sessions = new ArrayList<>();
        history = new ArrayList<>();
        sessionsDataFilename = "data/WorkoutSession.csv";
        setsDataFilename = "data/SessionSet.csv";
        historyDataFilename = "data/UserHistory.csv";

        exercises = new ArrayList<>();
        workouts = new ArrayList<>();
        exercisesDataFilename = "data/Exercise.csv";
        workoutsDataFilename = "data/Workout.csv";
        workoutExercisesDataFilename = "data/WorkoutExercise.csv";
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
            instance.loadUsersDataFromFile();
            instance.loadSessionsDataFromFile();
            instance.loadExercisesDataFromFile();
            instance.loadWorkoutsDataFromFile();
        }
        return instance;
    }

    public void loadUsersDataFromFile() {
        try {
            Scanner scanner = new Scanner(new File(usersDataFilename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                User user = convertLineToUserObject(line, ",");
                if (user != null) {
                    users.add(user);
                }
            }
            scanner.close();
        } catch (FileNotFoundException fnfEx) {
            System.out.println("File not found: " + fnfEx.getMessage());
            createUsersFile();
        } catch (Exception e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
    }

    public ArrayList<User> getUsersList() {
        return users;
    }

    public User findUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User findUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }

    public boolean isUserExists(String username) {
        return findUser(username) != null;
    }

    public int getNextUserId() {
        int maxId = 0;
        for (User user : users) {
            if (user.getUserId() > maxId) {
                maxId = user.getUserId();
            }
        }
        return maxId + 1;
    }

    public boolean addNewUser(User user) {
        if (user != null) {
            for (User u : users) {
                if (u.getUsername().equals(user.getUsername())) {
                    return false;
                }
            }
            users.add(user);
            addUserLineToFile(convertUserObjectToLine(user, ","));
            return true;
        }
        return false;
    }

    public void setCurrentUser(User user) {
        if (user != null) {
            currentUser = user;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logoutUser() {
        currentUser = null;
    }

    private void addUserLineToFile(String line) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(usersDataFilename, true));
            bw.write(line);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error writing to users file: " + e.getMessage());
        }
    }

    private User convertLineToUserObject(String line, String delimiter) {
        String[] fields = line.split(delimiter);
        if (fields.length != 7) {
            return null;
        }
        return new User(Integer.parseInt(fields[0]), fields[1], fields[2], fields[3],
                fields[4], Integer.parseInt(fields[5]), Double.parseDouble(fields[6]));
    }

    private String convertUserObjectToLine(User user, String delimiter) {
        return user.getUserId() + delimiter + user.getUsername() + delimiter + user.getName()
                + delimiter + user.getEmail() + delimiter + user.getPassword() + delimiter
                + user.getAge() + delimiter + user.getStartingWeight();
    }

    private void createUsersFile() {
        try {
            FileWriter writer = new FileWriter(usersDataFilename);
            writer.write("userId,username,name,email,password,age,startingWeight\n");
            writer.close();
            System.out.println("File created successfully!");
        } catch (IOException ex) {
            System.out.println("Error creating users file: " + ex.getMessage());
        }
    }

    public void loadSessionsDataFromFile() {
        sessions.clear();
        history.clear();
        try {
            Scanner scanner = new Scanner(new File(sessionsDataFilename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(",");
                sessions.add(new WorkoutSession(Integer.parseInt(fields[0]),
                        Integer.parseInt(fields[1]), fields[2], LocalDate.parse(fields[3])));
            }
            scanner.close();

            scanner = new Scanner(new File(setsDataFilename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(",");
                WorkoutSession session = findSession(Integer.parseInt(fields[0]));
                if (session != null) {
                    session.addSet(new SetEntry(fields[1], Integer.parseInt(fields[2]),
                            Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
                }
            }
            scanner.close();

            scanner = new Scanner(new File(historyDataFilename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(",");
                history.add(new int[]{Integer.parseInt(fields[0]), Integer.parseInt(fields[1])});
            }
            scanner.close();
        } catch (FileNotFoundException fnfEx) {
            System.out.println("History file not found: " + fnfEx.getMessage());
        } catch (Exception e) {
            System.out.println("Error reading history files: " + e.getMessage());
        }
    }

    public WorkoutSession findSession(int sessionId) {
        for (WorkoutSession session : sessions) {
            if (session.getSessionId() == sessionId) {
                return session;
            }
        }
        return null;
    }

    public ArrayList<WorkoutSession> getSessionsForUser(int userId) {
        ArrayList<WorkoutSession> result = new ArrayList<>();
        for (int[] link : history) {
            if (link[0] == userId) {
                WorkoutSession session = findSession(link[1]);
                if (session != null) {
                    result.add(session);
                }
            }
        }
        return result;
    }

    public int getNextSessionId() {
        int maxId = 0;
        for (WorkoutSession session : sessions) {
            if (session.getSessionId() > maxId) {
                maxId = session.getSessionId();
            }
        }
        return maxId + 1;
    }

    public void saveSession(WorkoutSession session, int userId) {
        sessions.add(session);
        history.add(new int[]{userId, session.getSessionId()});
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(sessionsDataFilename, true));
            bw.write(session.getSessionId() + "," + session.getWorkoutId() + ","
                    + session.getWorkoutName() + "," + session.getDate());
            bw.newLine();
            bw.close();

            bw = new BufferedWriter(new FileWriter(setsDataFilename, true));
            for (SetEntry set : session.getSets()) {
                bw.write(session.getSessionId() + "," + set.getExerciseName() + ","
                        + set.getSetNumber() + "," + set.getReps() + "," + set.getWeight());
                bw.newLine();
            }
            bw.close();

            bw = new BufferedWriter(new FileWriter(historyDataFilename, true));
            bw.write(userId + "," + session.getSessionId());
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            System.out.println("Error saving session: " + e.getMessage());
        }
    }

    public void loadExercisesDataFromFile() {
        exercises.clear();
        try {
            Scanner scanner = new Scanner(new File(exercisesDataFilename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(",");
                exercises.add(new Exercise(Integer.parseInt(fields[0]), fields[1],
                        Integer.parseInt(fields[2]), Integer.parseInt(fields[3]),
                        Double.parseDouble(fields[4])));
            }
            scanner.close();
        } catch (FileNotFoundException fnfEx) {
            System.out.println("Exercise file not found: " + fnfEx.getMessage());
        } catch (Exception e) {
            System.out.println("Error reading exercises file: " + e.getMessage());
        }
    }

    public void loadWorkoutsDataFromFile() {
        workouts.clear();
        try {
            Scanner scanner = new Scanner(new File(workoutsDataFilename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(",");
                workouts.add(new Workout(Integer.parseInt(fields[0]), fields[1], fields[2]));
            }
            scanner.close();

            scanner = new Scanner(new File(workoutExercisesDataFilename));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) {
                    continue;
                }
                String[] fields = line.split(",");
                Workout workout = findWorkout(Integer.parseInt(fields[0]));
                Exercise exercise = findExercise(Integer.parseInt(fields[1]));
                if (workout != null && exercise != null) {
                    workout.addExercise(exercise);
                }
            }
            scanner.close();
        } catch (FileNotFoundException fnfEx) {
            System.out.println("Workout file not found: " + fnfEx.getMessage());
        } catch (Exception e) {
            System.out.println("Error reading workouts files: " + e.getMessage());
        }
    }

    public ArrayList<Exercise> getExercisesList() {
        return exercises;
    }

    public ArrayList<Workout> getWorkoutsList() {
        return workouts;
    }

    public Exercise findExercise(int exerciseId) {
        for (Exercise exercise : exercises) {
            if (exercise.getExerciseId() == exerciseId) {
                return exercise;
            }
        }
        return null;
    }

    public Workout findWorkout(int workoutId) {
        for (Workout workout : workouts) {
            if (workout.getWorkoutId() == workoutId) {
                return workout;
            }
        }
        return null;
    }

    public ArrayList<Workout> getWorkoutsByCategory(String category) {
        ArrayList<Workout> result = new ArrayList<>();
        for (Workout workout : workouts) {
            if (workout.getCategory().equalsIgnoreCase(category)) {
                result.add(workout);
            }
        }
        return result;
    }

    public void setCurrentCategory(String category) {
        currentCategory = category;
    }

    public String getCurrentCategory() {
        return currentCategory;
    }

    public void setCurrentWorkout(Workout workout) {
        currentWorkout = workout;
    }

    public Workout getCurrentWorkout() {
        return currentWorkout;
    }
}