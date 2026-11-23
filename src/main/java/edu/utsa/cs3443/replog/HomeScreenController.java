package edu.utsa.cs3443.replog;

import edu.utsa.cs3443.replog.model.DataManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeScreenController extends BaseController {

    @FXML
    private Label usernameLabel;

    @FXML
    private void initialize() {
        dataManager = DataManager.getInstance();
        loadData();
    }

    private void loadData() {
        usernameLabel.setText(dataManager.getCurrentUser().getUsername());
    }

    @FXML
    void onPushClicked(ActionEvent event) {
        dataManager.setCurrentCategory("Push");
        switchScreen("myWorkouts-view");
    }

    @FXML
    void onPullClicked(ActionEvent event) {
        dataManager.setCurrentCategory("Pull");
        switchScreen("myWorkouts-view");
    }

    @FXML
    void onLegsClicked(ActionEvent event) {
        dataManager.setCurrentCategory("Legs");
        switchScreen("myWorkouts-view");
    }
}