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
    private boolean endgame_flag;

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
        endgame_flag = false;

        // Initialize window
        view.draw_board(game.get_snake_location(), game.get_mouse_location());
        view.set_score_bar(game.get_points());
    }

    /**
     * Method passes key input code to model and updates view according to game status returned from model
     * @param code key input code
     * @author
     */
    public void key_press(String code) {
        if (endgame_flag && !(code.equals("R") || code.equals("ESCAPE"))) {
            return;
        }
        game_status = game.game_action(code);

        if (game_status.equals("Playing")) {
            view.update_board(game.get_snake_location().get(0), game.get_tail(), game.get_mouse_location());
            view.set_score_bar(game.get_points());
        }
        else if (game_status.equals("Restart")) {
            game.reset_game();
            view.clear_endgame();
            view.draw_board(game.get_snake_location(), game.get_mouse_location());
            view.set_score_bar(game.get_points());
            endgame_flag = false;
        }
        else if (game_status.equals("Exit")){
            System.exit(0);
        }
        else {
            view.print_status(game_status);
            endgame_flag = true;
        }
    }
}
