package edu.utsa.cs3443.replog.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DataManager {

    private ArrayList<User> users;
    private final String usersDataFilename;
    private User currentUser;

    private static DataManager instance;

    private DataManager() {
        users = new ArrayList<>();
        usersDataFilename = "data/Users.csv";
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
            instance.loadUsersDataFromFile();
        }
        return instance;
    }

    /// /////////////////////////////////////////////////////////
    /// User Methods
    /// /////////////////////////////////////////////////////////

    public void loadUsersDataFromFile() {
        try {
            Scanner scanner = new Scanner(new File(usersDataFilename));
            scanner.nextLine(); // skip header row
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
}