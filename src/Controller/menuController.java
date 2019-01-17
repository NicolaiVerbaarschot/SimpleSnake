package Controller;

import View.menuView;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.List;

public class menuController {
    private int selected;
    private List<String> args;
    private Stage stage;

    public menuController(Stage stage, List<String> args) {
        new menuView(stage, this);
        selected = 1;
        this.args = args;
        this.stage = stage;
    }

    public void keyPress(String code) {
        switch (code) {
            case "UP":
                selected--;
                break;
            case "DOWN":
                selected++;
                break;
            case "ENTER":
                menuSelection(selected);
                break;
            case "ESCAPE":
                System.exit(0);
        }
        System.out.println(selected);
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

        FancySnakeController fancyController = new FancySnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), stack_pane, stage);

        scene.setOnKeyPressed(
                event -> fancyController.key_press(event.getCode().toString())
        );
    }

    private void playSimpleSnake() {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        stage.setScene(scene);

        SimpleSnakeController fancyController = new FancySnakeController(Integer.parseInt(args.get(0)), Integer.parseInt(args.get(1)), stack_pane, stage);

        scene.setOnKeyPressed(
                event -> fancyController.key_press(event.getCode().toString())
        );
    }


}
