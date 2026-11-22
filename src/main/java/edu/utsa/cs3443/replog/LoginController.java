package edu.utsa.cs3443.replog;

import edu.utsa.cs3443.replog.model.DataManager;
import edu.utsa.cs3443.replog.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        dataManager = DataManager.getInstance();
        loadData();
    }

    private void loadData() {
        errorLabel.setText("");
    }

    @FXML
    public void onLoginClicked(ActionEvent event) {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter a username and password!");
            return;
        }

        User user = dataManager.findUser(username, password);

        if (user == null) {
            errorLabel.setText("Invalid username and/or password!");
            return;
        }

        dataManager.setCurrentUser(user);
        switchScreen("Home-screen-view");
    }

    @FXML
    public void onAddAccountClicked(ActionEvent event) {
        switchScreen("AccountInfo-view");
    }
}