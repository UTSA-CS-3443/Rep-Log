package edu.utsa.cs3443.replog;

import edu.utsa.cs3443.replog.model.DataManager;
import edu.utsa.cs3443.replog.model.Exercise;
import edu.utsa.cs3443.replog.model.Workout;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MyWorkoutsController extends BaseController {

    @FXML private Label categoryTitleLabel;
    @FXML private Label emptyLabel;
    @FXML private VBox workoutList;
    @FXML private Button addExerciseButton;
    @FXML private Button startWorkoutButton;

    private ArrayList<Workout> shownWorkouts;
    private ArrayList<HBox> rows;
    private Workout selectedWorkout;
    private int selectedIndex;

    @FXML
    public void initialize() {
        dataManager = DataManager.getInstance();
        shownWorkouts = new ArrayList<>();
        rows = new ArrayList<>();
        selectedWorkout = null;
        selectedIndex = 0;
        loadData();
    }

    public void loadData() {
        String category = dataManager.getCurrentCategory();
        categoryTitleLabel.setText(category + " Workouts");

        workoutList.getChildren().clear();
        rows.clear();
        shownWorkouts = dataManager.getWorkoutsByCategory(category);

        if (shownWorkouts.isEmpty()) {
            emptyLabel.setVisible(true);
            emptyLabel.setManaged(true);
            startWorkoutButton.setDisable(true);
            addExerciseButton.setDisable(true);
            return;
        }

        emptyLabel.setVisible(false);
        emptyLabel.setManaged(false);
        startWorkoutButton.setDisable(false);
        addExerciseButton.setDisable(false);

        for (int i = 0; i < shownWorkouts.size(); i++) {
            HBox row = buildRow(shownWorkouts.get(i), i);
            rows.add(row);
            workoutList.getChildren().add(row);
        }
        if (selectedIndex >= shownWorkouts.size()) {
            selectedIndex = 0;
        }
        selectWorkout(selectedIndex);
    }

    private HBox buildRow(Workout workout, int index) {
        HBox row = new HBox(10);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPrefHeight(56);
        row.setPadding(new Insets(0, 12, 0, 20));

        Label nameLabel = new Label(workout.getName());
        nameLabel.setTextFill(Color.web("#e1b531"));
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));

        String info = workout.getExercises().size() + " exercises: ";
        ArrayList<Exercise> exerciseList = new ArrayList<>(workout.getExercises());
        for (int i = 0; i < exerciseList.size(); i++) {
            info += exerciseList.get(i).getName();
            if (i < exerciseList.size() - 1) {
                info += ", ";
            }
        }
        Label infoLabel = new Label(info);
        infoLabel.setTextFill(Color.web("#a8a8a8"));
        infoLabel.setFont(Font.font(14));

        row.getChildren().addAll(nameLabel, infoLabel);
        row.setOnMouseClicked(event -> selectWorkout(index));
        return row;
    }

    private void selectWorkout(int index) {
        selectedIndex = index;
        selectedWorkout = shownWorkouts.get(index);
        for (int i = 0; i < rows.size(); i++) {
            if (i == index) {
                rows.get(i).setStyle("-fx-background-color: #3d3d3d; "
                        + "-fx-border-color: #e1b531; -fx-border-width: 2;");
            } else {
                rows.get(i).setStyle("-fx-background-color: #323232; "
                        + "-fx-border-color: #5a5a5a;");
            }
        }
    }

    @FXML
    protected void onAddExerciseClicked() {
        if (selectedWorkout == null) {
            return;
        }
        try {
            AddExerciseController.show(stage, selectedWorkout, () -> loadData());
        } catch (IOException e) {
            System.out.println("Could not open the add exercise screen: " + e.getMessage());
        }
    }

    @FXML
    protected void onStartWorkoutClicked() {
        if (selectedWorkout == null) {
            return;
        }
        dataManager.setCurrentWorkout(selectedWorkout);
        switchScreen("Workout-view");
    }
}