package Controller;

import Model.SimpleSnake;
import View.SimpleSnakeView;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Class controls the Simple Snake game
 * @author Andreas Goll Rossau
 */
public class SimpleSnakeController {
    private SimpleSnake game;
    // SnakeTextViewKeyPress view;
    private SimpleSnakeView view;
    private  String game_status;
    private int grid_x;
    private int grid_y;

    /**
     * Constructor. The program never leaves this constructor unless the game ends
     * @param grid_x The grid size in the x dimension
     * @param grid_y The grid size in the y dimension
     * @author Andreas Goll Rossau
     */
    public SimpleSnakeController(int grid_x, int grid_y, Scene scene, GridPane gridPane, Stage primary_stage) {
        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new SimpleSnakeView(grid_x, grid_y, scene, gridPane, primary_stage);
        this.grid_x = grid_x;
        this.grid_y = grid_y;

        // Initialize window
        view.draw_board(game.get_snake_location(), game.get_mouse_location());
        view.set_score_bar(game.get_points());
    }

    // Modify window according to key input
    public void key_press(String code) {
        game_status = game.game_action(code);
        if (game_status.equals("Playing")) {
            view.draw_board(game.get_snake_location(), game.get_mouse_location());
            view.set_score_bar(game.get_points());
        } else if (game_status.equals("Restart")) {
            game.reset_game();
            view.draw_board(game.get_snake_location(), game.get_mouse_location());
            view.set_score_bar(game.get_points());
        } else {
            view.print_status(game_status);
            //System.exit(0);
        }
    }
}
