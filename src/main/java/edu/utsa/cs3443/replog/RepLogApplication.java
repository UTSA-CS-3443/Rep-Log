package edu.utsa.cs3443.replog;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RepLogApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RepLogApplication.class.getResource("layouts/Login-screen-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        // The LoginController switches to the home screen on the same stage after login
        LoginController loginController = fxmlLoader.getController();
        loginController.setStage(stage);

        stage.setTitle("RepLog");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}