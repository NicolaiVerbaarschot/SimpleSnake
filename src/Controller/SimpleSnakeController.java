package Controller;

import Model.SimpleSnake;
import View.SimpleSnakeView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * This Class controls the Simple Snake game
 *
 * @author  Andreas Goll Rossau
 */
class SimpleSnakeController {

    private SimpleSnake game;
    private SimpleSnakeView view;
    private boolean endgame_flag;
    private MenuController menuController;

    /**
     * Constructor. The program never leaves this constructor unless the game ends
     *
     * @param   grid_x : The grid size in the x dimension
     * @param   grid_y : The grid size in the y dimension
     * @param   gridPane : JavaFX Node
     * @param   primary_stage : JavaFX Node
     * @param   menuController : Controller for main menu
     * @author  Andreas Goll Rossau
     */
    SimpleSnakeController(int grid_x, int grid_y, GridPane gridPane, Stage primary_stage, MenuController menuController) throws IOException {

        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new SimpleSnakeView(grid_x, grid_y, gridPane, primary_stage);
        this.menuController = menuController;

        endgame_flag = false;

        // Initialize window
        view.draw_board(game.get_snake_location(), game.get_mouse_location());
        view.set_score_bar(game.get_score());
    }

    /**
     * Method passes key input code to model and updates view according to game status returned from model
     *
     * @param   code key: Input code
     * @author  Andreas Goll Rossau
     */
    void key_press(String code) {

        if (endgame_flag && !(code.equals("R") || code.equals("ESCAPE")))
            return;

        String game_status = game.game_action(code);
        switch (game_status) {
            case "Playing":
                view.update_board(game.get_snake_location().get(0), game.get_tail(), game.get_mouse_location());
                view.set_score_bar(game.get_score());
                break;
            case "Restart":
                game.reset_game();
                view.clear_endgame();
                view.draw_board(game.get_snake_location(), game.get_mouse_location());
                view.set_score_bar(game.get_score());
                view.set_score_bar(game.get_score());
                endgame_flag = false;
                break;
            case "Exit":
                menuController.reinitialize();
                System.gc();
                break;
            default:
                view.print_status(game_status);
                endgame_flag = true;
                break;
        }
    }

}
