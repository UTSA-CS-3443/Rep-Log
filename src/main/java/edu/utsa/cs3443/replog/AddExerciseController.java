package edu.utsa.cs3443.replog;

import edu.utsa.cs3443.replog.model.DataManager;
import edu.utsa.cs3443.replog.model.Exercise;
import edu.utsa.cs3443.replog.model.Workout;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddExerciseController extends BaseController {
    @FXML private TextField nameField;
    @FXML private TextField setsField;
    @FXML private TextField repsField;
    @FXML private Label errorLabel;

    private Workout workout;
    private Scene previousScene;
    private Runnable onExerciseAdded;

    public static void show(Stage stage, Workout workout, Runnable onExerciseAdded) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                AddExerciseController.class.getResource("layouts/add-exercise-view.fxml"));
        Scene scene = new Scene(loader.load());
        AddExerciseController controller = loader.getController();
        controller.setStage(stage);
        controller.workout = workout;
        controller.previousScene = stage.getScene();
        controller.onExerciseAdded = onExerciseAdded;
        stage.setScene(scene);
    }

    @FXML
    private void initialize() {
        dataManager = DataManager.getInstance();
    }

    @FXML
    private void onSaveClicked() {
        Exercise exercise;
        try {
            int sets = Integer.parseInt(setsField.getText().trim());
            int reps = Integer.parseInt(repsField.getText().trim());
            int nextId = workout.getExercises().stream()
                    .mapToInt(Exercise::getExerciseId)
                    .max().orElse(0) + 1;
            exercise = new Exercise(nextId, nameField.getText(), sets, reps, 0.0);
        } catch (NumberFormatException e) {
            errorLabel.setText("Sets and reps must be whole numbers.");
            return;
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            return;
        }

        workout.addExercise(exercise);
        stage.setScene(previousScene);
        onExerciseAdded.run();
    }

    @FXML
    private void onCancelClicked() {
        stage.setScene(previousScene);
    }
}
