package Controller;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

public class MainApp extends Application {
    public void start(Stage primaryStage) {
        List<String> args = getParameters().getRaw();

        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);

        primaryStage.show();

        SnakeController controller = new SnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), scene, gridPane);

        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    @Override
                    public void handle(KeyEvent event) {
                        controller.keyPress(event.getCode().toString());
                    }
                }
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
