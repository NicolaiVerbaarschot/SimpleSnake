package Controller;

import Model.SimpleSnake;
import View.FancySnakeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This Class controls the Simple Snake game
 *
 * @author  Andreas Goll Rossau
 */
class FancySnakeController {

    private SimpleSnake game;
    private FancySnakeView view;
    private boolean endgame_flag;
    private MenuController menuController;

    /**
     * Constructor. The program never leaves this constructor unless the game ends
     *
     * @param   grid_x : The grid size in the x dimension
     * @param   grid_y : The grid size in the y dimension
     * @param   stack_pane : JavaFX Node
     * @param   primary_stage : JavaFX Node
     * @param   menuController : Controller for main menu
     * @author  Andreas Goll Rossau
     */
    FancySnakeController(int grid_x, int grid_y, StackPane stack_pane, Stage primary_stage, MenuController menuController) {

        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new FancySnakeView(grid_x, grid_y, stack_pane, primary_stage);
        this.menuController = menuController;

        endgame_flag = false;

        // Initialize window
        view.draw_board(game.get_snake_segments(), game.get_mouse_location());
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
                view.update_board(game.get_snake_segments(), game.get_tail(), game.get_mouse_location());
                view.set_score_bar(game.get_score());
                break;
            case "Restart":
                game.reset_game();
                view.clear_endgame();
                view.draw_board(game.get_snake_segments(), game.get_mouse_location());
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
