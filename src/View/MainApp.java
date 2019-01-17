package View;

import Controller.MenuController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.List;

/**
 * Main class of Simple Snake game
 *
 * @author  Andreas Goll Rossau
 */
public class MainApp extends Application {
    List<String> args;
    MenuController menuController;

    /**
     * Initializer method for javafx application
     * Creates Stage, Scene and GridPane to show game onan
     * Creates FancySnakeController
     * Handles key input
     *
     * @param   primary_stage: primary stage to show game on
     * @author  Andreas Goll Rossau
     */
    public void start(Stage primary_stage) {

        // Pass command line arguments to list
        args = getParameters().getRaw();

        menuController = new MenuController(primary_stage, args, this);
    }

    /**
     * Main method launches javafx application and passes command line arguments
     *
     * @param   args: command line arguments should be x and y dimensions of game grid
     * @author  Andreas Goll Rossau
     */
    public static void main(String[] args) {
        launch(args);
    }
}
