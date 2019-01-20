package View;

import Controller.EndGameController;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class EndGameView {

    private Stage stage;
    private Scene scene;
    private StackPane stack_pane;
    private Rectangle endgame_background = new Rectangle();
    private int grid_x;
    private int grid_y;
    private int cell_size;


    public EndGameView(int grid_x, int grid_y, Stage stage, EndGameController endGameController) {

        this.grid_x = grid_x;
        this.grid_y = grid_y;
        this.cell_size = Math.min( (100/Math.max(grid_x,grid_y))*9, 100 );

        this.stage = stage;
        this.stack_pane = new StackPane();
        stack_pane.setOpacity(1);
        this.scene = new Scene(stack_pane);

        //stage.setWidth(menuWidth);
        //stage.setHeight(menuHeight);
        stage.setScene(scene);
        stage.show();


        // Take input and send to endGameController
        scene.setOnKeyPressed(
                event -> endGameController.key_press(event.getCode().toString())
        );
    }


    public void open_end_game() {


        int window_init_size = 40;

        // Initialize rectangle parameters
        endgame_background.setHeight((int) ((grid_y+ 65)/window_init_size) * cell_size);
        endgame_background.setWidth((int) (grid_x/window_init_size) * cell_size);
        endgame_background.setFill(Color.CADETBLUE);

        // Add endgame_background to stack_pane
        stack_pane.getChildren().add(endgame_background);


        long startNanoTime = System.nanoTime();
        AnimationTimer timer = new AnimationTimer() {
            int windows_size = window_init_size - 1;
            private long last_update = 0 ;
            public void handle(long now) {
                long t = (now - startNanoTime) / 100000000;

                if (now - last_update >= 200000 && windows_size > 0) {

                    // Change rectangle parameters
                    endgame_background.setHeight((int) ((grid_y + 65)/windows_size) * cell_size);
                    endgame_background.setWidth((int) (grid_x/windows_size) * cell_size);
                    endgame_background.setFill(Color.CADETBLUE);

                    windows_size -= 1;
                    last_update = now ;
                }
            }
        };
        timer.start();
    }

    public void display_end_game(String game_status, int score, int high_score, boolean new_high_score) {

    }

    public void clear_endgame() {
        stack_pane.getChildren().remove(endgame_background);
    }

}
