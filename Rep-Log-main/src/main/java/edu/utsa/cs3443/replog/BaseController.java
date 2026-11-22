package edu.utsa.cs3443.replog;

import edu.utsa.cs3443.replog.model.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class BaseController {

    protected Stage stage;
    protected DataManager dataManager;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    protected void switchScreen(String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("layouts/" + fxml + ".fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            BaseController controller = fxmlLoader.getController();
            controller.setStage(stage);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onHomeClicked(ActionEvent event) {
        switchScreen("Home-screen-view");
    }

    @FXML
    public void onHistoryClicked(ActionEvent event) {
        System.out.println("History screen coming soon"); // switchScreen once the screen exists
    }

    @FXML
    public void onLogoutClicked(ActionEvent event) {
        dataManager.logoutUser();
        switchScreen("Login-screen-view");
    }
}