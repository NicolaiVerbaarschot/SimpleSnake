package Controller;

import View.MainApp;
import View.MenuView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class MenuController {
    private int selected;
    private int maxSelected = 3;
    private List<String> args;
    private Stage stage;
    private MainApp mainApp;
    private MenuView menuView;
    private AnimationTimer timer;
    private long startNanoTime;

    public MenuController(Stage stage, List<String> args, MainApp mainApp) {
        this.menuView = new MenuView(stage, this);
        selected = 1;
        this.args = args;
        this.stage = stage;
        this.mainApp = mainApp;

        startNanoTime = System.nanoTime();
        timer = new AnimationTimer() {
            public void handle(long nanoTime) {
                long t = (nanoTime - startNanoTime) / 100000000;
                menuView.updateMenu(selected, t);
            }
        };
        timer.start();
    }

    public void keyPress(String code) {
        switch (code) {
            case "UP":
                selected--;
                if (selected < 1) {
                    selected = maxSelected;
                }
                break;
            case "DOWN":
                selected++;
                if (selected > maxSelected) {
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
                System.exit(0);
        }
    }

    private void playFancySnake() {
        StackPane stack_pane = new StackPane();
        Scene scene = new Scene(stack_pane);

        stage.setScene(scene);

        FancySnakeController fancyController = new FancySnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), stack_pane, stage, this);

        scene.setOnKeyPressed(
                event -> fancyController.key_press(event.getCode().toString())
        );
    }

    private void playSimpleSnake() {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);

        SimpleSnakeController simpleController = new SimpleSnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), gridPane, stage, this);

        scene.setOnKeyPressed(
                event -> simpleController.key_press(event.getCode().toString())
        );
    }


    public void reinitialize() {
        selected = 1;
        menuView.reinitialize();
        startNanoTime = System.nanoTime();
        timer.start();
    }
}
