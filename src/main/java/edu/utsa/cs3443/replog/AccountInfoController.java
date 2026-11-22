package edu.utsa.cs3443.replog;

import edu.utsa.cs3443.replog.model.DataManager;
import edu.utsa.cs3443.replog.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class AccountInfoController extends BaseController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField startingWeightField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button deleteButton;

    @FXML
    public void initialize() {
        dataManager = DataManager.getInstance();
        loadData();
    }

    public void loadData() {
        errorLabel.setText("");
        deleteButton.setVisible(false);
        deleteButton.setManaged(false);
    }

    @FXML
    protected void onSaveClicked() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String name = nameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || email.isEmpty() || name.isEmpty() || password.isEmpty()
                || ageField.getText().isBlank() || startingWeightField.getText().isBlank()) {
            errorLabel.setText("Please fill in every field.");
            return;
        }
        if (dataManager.isUserExists(username)) {
            errorLabel.setText("That username is already taken.");
            return;
        }

        int age;
        double weight;
        try {
            age = Integer.parseInt(ageField.getText().trim());
            weight = Double.parseDouble(startingWeightField.getText().trim());
        } catch (NumberFormatException e) {
            errorLabel.setText("Age and weight must be numbers.");
            return;
        }

        try {
            User user = new User(dataManager.getNextUserId(), username, name, email,
                    password, age, weight);
            if (dataManager.addNewUser(user)) {
                dataManager.setCurrentUser(user);
                switchScreen("Home-screen-view");
            } else {
                errorLabel.setText("Could not create the account.");
            }
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    @FXML
    protected void onCancelClicked() {
        switchScreen("Login-screen-view");
    }
}