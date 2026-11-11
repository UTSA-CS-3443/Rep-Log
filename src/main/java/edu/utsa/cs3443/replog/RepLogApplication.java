package edu.utsa.cs3443.replog;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RepLogApplication extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("RepLog");
        stage.setScene(new Scene(new Label("RepLog"), 400, 300));
        stage.show();
    }
}
