package edu.utsa.cs3443.replog;

import edu.utsa.cs3443.replog.model.DataManager;
import edu.utsa.cs3443.replog.model.Exercise;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class EditExerciseController extends BaseController {

    @FXML private TextField nameField;
    @FXML private TextField setsField;
    @FXML private TextField repsField;
    @FXML private Label errorLabel;

    private Exercise exercise;
    private Scene previousScene;
    private Runnable onExerciseSaved;

    public static void show(Stage stage, Exercise exercise, Runnable onExerciseSaved) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                EditExerciseController.class.getResource("layouts/edit-exercise-view.fxml"));
        Scene scene = new Scene(loader.load());
        EditExerciseController controller = loader.getController();
        controller.setStage(stage);
        controller.exercise = exercise;
        controller.previousScene = stage.getScene();
        controller.onExerciseSaved = onExerciseSaved;
        controller.fillFields();
        stage.setScene(scene);
    }

    @FXML
    private void initialize() {
        dataManager = DataManager.getInstance();
    }

    private void fillFields() {
        nameField.setText(exercise.getName());
        setsField.setText(String.valueOf(exercise.getTargetSets()));
        repsField.setText(String.valueOf(exercise.getTargetReps()));
        errorLabel.setText("");
    }

    @FXML
    private void onSaveClicked() {
        String name = nameField.getText().trim();
        if (name.contains(",")) {
            errorLabel.setText("Exercise name can't contain commas.");
            return;
        }

        try {
            int sets = Integer.parseInt(setsField.getText().trim());
            int reps = Integer.parseInt(repsField.getText().trim());
            new Exercise(exercise.getExerciseId(), name, sets, reps, exercise.getTargetWeight());
            exercise.updateExercise(name, sets, reps, exercise.getTargetWeight());
        } catch (NumberFormatException e) {
            errorLabel.setText("Sets and reps must be whole numbers.");
            return;
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            return;
        }

        dataManager.saveExercisesToFile();
        stage.setScene(previousScene);
        onExerciseSaved.run();
    }

    @FXML
    private void onCancelClicked() {
        stage.setScene(previousScene);
    }
}