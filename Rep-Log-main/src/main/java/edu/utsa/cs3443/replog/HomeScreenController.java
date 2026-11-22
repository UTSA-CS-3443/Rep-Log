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
        System.out.println("Push workout screen coming soon");
    }

    @FXML
    void onPullClicked(ActionEvent event) {
        System.out.println("Pull workout screen coming soon");
    }

    @FXML
    void onLegsClicked(ActionEvent event) {
        System.out.println("Legs workout screen coming soon");
    }
}