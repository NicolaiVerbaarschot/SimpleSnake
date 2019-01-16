package Controller;

import Model.SimpleSnake;
import View.SimpleSnakeView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * This Class controls the Simple Snake game
 *
 * @author  Andreas Goll Rossau
 */
public class SimpleSnakeController {

    private SimpleSnake game;
    private SimpleSnakeView view;
    private boolean endgame_flag;

    /**
     * Constructor. The program never leaves this constructor unless the game ends
     *
     * @param   grid_x: The grid size in the x dimension
     * @param   grid_y: The grid size in the y dimension
     * @param   stack_pane: JavaFX Node
     * @param   primary_stage: JavaFX Node
     * @author  Andreas Goll Rossau
     */
    public SimpleSnakeController(int grid_x, int grid_y, StackPane stack_pane, Stage primary_stage) {

        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new SimpleSnakeView(grid_x, grid_y, stack_pane, primary_stage);

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
    public void key_press(String code) {

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
                System.exit(0);
            default:
                view.print_status(game_status);
                endgame_flag = true;
                break;
        }
    }

}
