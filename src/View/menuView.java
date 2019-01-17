package View;

import Controller.menuController;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class menuView {
    public menuView(Stage stage, menuController menuController) {
        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane);
        stage.setWidth(300);
        stage.setHeight(300);
        stage.setScene(scene);
        stage.show();

        scene.setOnKeyPressed(
                event -> menuController.keyPress(event.getCode().toString())
        );
    }
}
