package View;

import Controller.SimpleSnakeController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

/**
 * Run this class to play Simple Snake
 * @author Andreas Goll Rossau
 */
public class MainApp extends Application {

    /**
     * Initializer method for javafx application
     * Creates Stage, Scene and GridPane to show game on
     * Creates SimpleSnakeController
     * Handles key input
     * @param primary_stage primary stage to show game on
     * @author Andreas Goll Rossau
     */
    public void start(Stage primary_stage) {
        // Pass command line arguments to list
        List<String> args = getParameters().getRaw();

        GridPane grid_pane = new GridPane();
        Scene scene = new Scene(grid_pane);
        primary_stage.setScene(scene);

        primary_stage.show();

        SimpleSnakeController controller = new SimpleSnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), grid_pane, primary_stage);

        // Constantly detects key pressed
        // Converts keycode to descriptive string and sends it to controller via. the method key_press
        // Controller calls for View action based on keycode string - which is saved for scene?
        scene.setOnKeyPressed(
                event -> controller.key_press(event.getCode().toString().toLowerCase())
        );
    }

    /**
     * main method launches javafx application and passes command line arguments
     * @param args command line arguments should be x and y dimensions of game grid
     * @author Andreas Goll Rossau
     */
    public static void main(String[] args) {
        launch(args);
    }
}
