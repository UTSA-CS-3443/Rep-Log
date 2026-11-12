package edu.utsa.cs3443.replog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    // private User user;

    private Stage stage;


    @FXML
    private void initialize(){

    }

    @FXML
    public void onLoginButtonClick(ActionEvent e){
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        if(username.isEmpty() || password.isEmpty()){
            errorLabel.setText("Please enter a valid username and password!");
            return;
        }

        // user find user
    }
}
