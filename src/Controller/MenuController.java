package Controller;

import View.MainApp;
import View.MenuView;
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

    public MenuController(Stage stage, List<String> args, MainApp mainApp) {
        this.menuView = new MenuView(stage, this);
        selected = 1;
        menuView.drawMenu(selected);
        this.args = args;
        this.stage = stage;
        this.mainApp = mainApp;
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
        menuView.drawMenu(selected);
    }

    private void menuSelection(int selected) {
        switch (selected) {
            case 1:
                playSimpleSnake();
                break;
            case 2:
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
    }
}
