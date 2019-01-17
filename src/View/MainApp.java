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

    /**
     * Initializer method for javafx application
     * Initializes MenuController
     *
     * @param   primary_stage: primary stage to show game on
     * @author  Andreas Goll Rossau
     */
    public void start(Stage primary_stage) {

        // Pass command line arguments to list
        List<String> args = getParameters().getRaw();

        new MenuController(primary_stage, args);
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
