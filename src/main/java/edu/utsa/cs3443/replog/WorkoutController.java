package edu.utsa.cs3443.replog;

import edu.utsa.cs3443.replog.model.DataManager;
import edu.utsa.cs3443.replog.model.Exercise;
import edu.utsa.cs3443.replog.model.SetEntry;
import edu.utsa.cs3443.replog.model.Workout;
import edu.utsa.cs3443.replog.model.WorkoutSession;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class WorkoutController extends BaseController {

    @FXML private Label titleLabel;
    @FXML private Label emptyLabel;
    @FXML private Label errorLabel;
    @FXML private VBox workoutList;
    @FXML private Button finishButton;

    private Workout workout;
    private ArrayList<Exercise> exerciseList;
    private ArrayList<CheckBox> checkBoxes;

    @FXML
    public void initialize() {
        dataManager = DataManager.getInstance();
        exerciseList = new ArrayList<>();
        checkBoxes = new ArrayList<>();
        loadData();
    }

    public void loadData() {
        workout = dataManager.getCurrentWorkout();
        errorLabel.setText("");
        workoutList.getChildren().clear();
        exerciseList.clear();
        checkBoxes.clear();

        if (workout == null || workout.getExercises().isEmpty()) {
            emptyLabel.setVisible(true);
            emptyLabel.setManaged(true);
            finishButton.setDisable(true);
            return;
        }

        titleLabel.setText(workout.getName() + " Day");
        emptyLabel.setVisible(false);
        emptyLabel.setManaged(false);
        finishButton.setDisable(false);
        exerciseList.addAll(workout.getExercises());

        for (Exercise exercise : exerciseList) {
            workoutList.getChildren().add(buildRow(exercise));
        }
    }

    private HBox buildRow(Exercise exercise) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefHeight(56);
        row.setPadding(new Insets(0, 12, 0, 20));
        row.setStyle("-fx-background-color: #323232; -fx-border-color: #e1b531;");

        CheckBox checkBox = new CheckBox(exercise.getName());
        checkBox.setTextFill(Color.web("#e1b531"));
        checkBox.setFont(Font.font("System", FontWeight.BOLD, 18));
        checkBox.setStyle("-fx-mark-color: #e1b531;");
        checkBox.setPrefWidth(300);
        checkBoxes.add(checkBox);

        Label infoLabel = new Label(formatInfo(exercise));
        infoLabel.setTextFill(Color.web("#a8a8a8"));
        infoLabel.setFont(Font.font(16));

        row.getChildren().addAll(checkBox, infoLabel);
        return row;
    }

    private String formatInfo(Exercise exercise) {
        String info = exercise.getTargetSets() + " sets x " + exercise.getTargetReps() + " reps";
        double weight = exercise.getTargetWeight();
        if (weight == 0) {
            info += " (bodyweight)";
        } else if (weight == (int) weight) {
            info += " @ " + (int) weight + " lbs";
        } else {
            info += " @ " + weight + " lbs";
        }
        return info;
    }

    @FXML
    protected void onFinishClicked() {
        int checkedCount = 0;
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                checkedCount++;
            }
        }
        if (checkedCount == 0) {
            errorLabel.setText("Check off at least one exercise first.");
            return;
        }

        WorkoutSession session = new WorkoutSession(dataManager.getNextSessionId(),
                workout.getWorkoutId(), workout.getName(), LocalDate.now());
        for (int i = 0; i < exerciseList.size(); i++) {
            if (checkBoxes.get(i).isSelected()) {
                Exercise exercise = exerciseList.get(i);
                for (int setNumber = 1; setNumber <= exercise.getTargetSets(); setNumber++) {
                    session.addSet(new SetEntry(exercise.getName(), setNumber,
                            exercise.getTargetReps(), exercise.getTargetWeight()));
                }
            }
        }

        dataManager.saveSession(session, dataManager.getCurrentUser().getUserId());
        switchScreen("History-view");
    }
}