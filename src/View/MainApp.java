package View;

import Controller.SimpleSnakeController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class MainApp extends Application {
    public void start(Stage primary_stage) {
        // Accept console input
        List<String> args = getParameters().getRaw();

        GridPane grid_pane = new GridPane();
        Scene scene = new Scene(grid_pane);
        primary_stage.setScene(scene);

        primary_stage.show();

        SimpleSnakeController controller = new SimpleSnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), scene, grid_pane, primary_stage);

        // Constantly detects key pressed
        // Converts keycode to descriptive string and sends it to controller via. the method key_press
        // Controller calls for View action based on keycode string - which is saved for scene?
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        controller.key_press(event.getCode().toString());
                    }
                }
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
