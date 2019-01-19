package Controller;

import View.MenuView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller for main menu
 * @author Andreas Goll Rossau
 */
public class MenuController {
    private int numberOfMenuOptions = 5;
    private int selected;
    private List<String> args;
    private Stage stage;
    private Scene menuScene;
    private MenuView menuView;
    private AnimationTimer timer;
    private long startNanoTime;

    /**
     * Constructor creates view for menu and starts animation timer
     * to update view
     * @param stage : Game window
     * @param args : List of command line arguments, should be grid dimensions
     * @author Andreas Goll Rossau
     */
    public MenuController(Stage stage, List<String> args) {
        this.menuView = new MenuView(stage, this, numberOfMenuOptions);
        selected = 1;
        this.args = args;
        this.stage = stage;

        startNanoTime = System.nanoTime();
        timer = new AnimationTimer() {
            public void handle(long nanoTime) {
                long t = (nanoTime - startNanoTime) / 100000000;
                menuView.updateMenu(selected, t);
            }
        };
        timer.start();
    }

    /**
     * Method handles key input codes
     * @param code : input code
     * @author Andreas Goll Rossau
     */
    public void keyPress(String code) {
        switch (code) {
            case "UP":
                selected--;
                if (selected < 1) {
                    selected = numberOfMenuOptions;
                }
                break;
            case "DOWN":
                selected++;
                if (selected > numberOfMenuOptions) {
                    selected = 1;
                }
                break;
            case "ENTER":
                menuSelection(selected);
                break;
            case "ESCAPE":
                System.exit(0);
        }
    }

    /**
     * Method executes selected menu item
     * @param selected menu selection
     * @author Andreas Goll Rossau
     */
    private void menuSelection(int selected) {
        switch (selected) {
            case 1:
                timer.stop();
                playSimpleSnake();
                break;
            case 2:
                timer.stop();
                playFancySnake();
                break;
            case 3:
                showSubmenu("instructions");
                break;
            case 4:
                showSubmenu("high scores");
                break;
            case 5:
                System.exit(0);
        }
    }

    private void showSubmenu(String subMenu) {
        StackPane stack_pane = new StackPane();
        Scene scene = new Scene(stack_pane);
        menuScene = stage.getScene();
        stage.setScene(scene);

        if (subMenu.equals("instructions")) {
            menuView.showInstructions(stack_pane);
        }
        else {
            menuView.showHighScores(stack_pane);
        }

        scene.setOnKeyPressed(
                event -> reinitialize()
        );
    }

    /**
     * Method initializes Fancy Snake
     * @author Andreas Goll Rossau
     */
    private void playFancySnake() {
        StackPane stack_pane = new StackPane();
        Scene scene = new Scene(stack_pane);

        stage.setScene(scene);

        FancySnakeController fancyController = new FancySnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), stack_pane, stage, this);

        scene.setOnKeyPressed(
                event -> fancyController.set_direction(event.getCode().toString().toLowerCase())
        );
    }

    /**
     * Method initializes Simple Snake
     * @author Andreas Goll Rossau
     */
    private void playSimpleSnake() {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);

        SimpleSnakeController simpleController = new SimpleSnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), gridPane, stage, this);

        scene.setOnKeyPressed(
                event -> simpleController.key_press(event.getCode().toString())
        );
    }


    /**
     * Method reinitializes menu
     * @author Andreas Goll Rossau
     */
    void reinitialize() {
        stage.setScene(menuScene);
        selected = 1;
        menuView.reinitialize();
        startNanoTime = System.nanoTime();
        timer.start();
    }
}
