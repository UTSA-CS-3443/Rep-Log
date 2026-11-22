package edu.utsa.cs3443.replog.model;

public class User {
    private String username;
    private int userId;
    private String name;
    private String email;
    private String password;
    private int age;
    private double startingWeight;
    
    public User(int userId, String username, String name, String email, String password, int age, double startingWeight) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.startingWeight = startingWeight;
    }
    
    public void updateProfile(String username, String name, String email, int age, double startingWeight) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException ("Username cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException ("Please enter a valid email");
        }
        if(age <= 0) {
            throw new IllegalArgumentException ("Please enter a valid age.");
        }
        if (startingWeight <= 0) {
            throw new IllegalArgumentException ("Please enter a valid starting weight.");
        }
        this.username = username;
        this.email = email;
        this.name = name;
        this.age = age;
        this.startingWeight = startingWeight;
    }

   public boolean checkPassword(String attempt) {
       return password.equals(attempt);
   }
    public void updatePassword(String oldPassword, String newPassword) {
        if (!checkPassword(oldPassword)) {
            throw new IllegalArgumentException ("Password is incorrect");
        }
        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException ("Password must be at least 6 characters");
        }
        this.password = newPassword;
    }
    
    public String viewAccount() {
        return "Username: " + username + "\n"
        + "Name: " + name + "\n"
        + "Email: " + email + "\n";
    }
    
    public int getUserId() {return userId;}
    
    public String getUsername() {return username;}

    public String getName() { return name;}

    public String getEmail(){ return email;}
    
    String getPassword() {return password;}
    
    public int getAge() {return age;}
    
    public double getStartingWeight() {return startingWeight;}
    
}
