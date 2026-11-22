package edu.utsa.cs3443.replog;

import edu.utsa.cs3443.replog.model.DataManager;
import edu.utsa.cs3443.replog.model.SetEntry;
import edu.utsa.cs3443.replog.model.User;
import edu.utsa.cs3443.replog.model.WorkoutSession;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HistoryController extends BaseController {

    @FXML private Label historyTitleLabel;
    @FXML private Label historySubtitleLabel;
    @FXML private Label statsLabel;

    @FXML
    public void initialize() {
        dataManager = DataManager.getInstance();
        loadData();
    }

    /** Builds the stats text from the current user's saved sessions. */
    public void loadData() {
        User user = dataManager.getCurrentUser();
        historyTitleLabel.setText(user.getName() + "'s History");
        historySubtitleLabel.setText("Here is how you've done so far, " + user.getName() + "!");

        ArrayList<WorkoutSession> sessions = dataManager.getSessionsForUser(user.getUserId());
        if (sessions.isEmpty()) {
            statsLabel.setText("No workouts logged yet. Finish a workout to see it here.");
            return;
        }

        double totalVolume = 0;
        for (WorkoutSession s : sessions) {
            for (SetEntry set : s.getSets()) {
                totalVolume += set.getVolume();
            }
        }

        String text = "Total workouts: " + sessions.size() + "\n"
                + "Total volume: " + (int) totalVolume + " lbs\n\n";
        for (int i = sessions.size() - 1; i >= 0; i--) {
            text += sessions.get(i).viewSession() + "\n";
        }
        statsLabel.setText(text);
    }
}