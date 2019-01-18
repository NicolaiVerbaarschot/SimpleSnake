/*
package View;

import Controller.FancySnakeController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class MainFancyApp extends Application {

    public void start(Stage primary_stage) {

        List<String> args = getParameters().getRaw();

        StackPane stack_pane = new StackPane();
        Scene scene = new Scene(stack_pane);
        primary_stage.setScene(scene);

        primary_stage.show();

        FancySnakeController controller = new FancySnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), stack_pane, primary_stage);

        // Constantly detects key pressed
        // Converts keycode to descriptive string and sends it to controller via. the method key_press
        // Controller calls for View action based on keycode string - which is saved for scene?
        scene.setOnKeyPressed(
                event -> controller.set_direction(event.getCode().toString().toLowerCase())
        );

    }

    public static void main(String[] args) {
        launch(args);
    }
}
*/
