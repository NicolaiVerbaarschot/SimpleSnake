package Controller;

import Model.SimpleSnake;
import View.SnakeTextView;

/**
 * This Class controls the Simple Snake game
 *
 * @author  Andreas Goll Rossau
 */
class SnakeTextController {

    /**
     * Constructor. The program never leaves this constructor unless the game ends
     *
     * @param   grid_x: The grid size in the x dimension
     * @param   grid_y: The grid size in the y dimension
     * @author  Andreas Goll Rossau
     */
    SnakeTextController(int grid_x, int grid_y) {

        SimpleSnake game = new SimpleSnake(grid_x, grid_y);
        SnakeTextView view = new SnakeTextView();

        // This is the game loop only exited on game over
        String game_status;
        do
        {
            view.drawBoard(grid_x, grid_y, game.get_snake_location(), game.get_mouse_location(), game.get_score());
            game_status = game.game_action(view.getInput());
        }
        while (game_status.equals("Playing"));

        System.out.println(game_status);
    }

}
