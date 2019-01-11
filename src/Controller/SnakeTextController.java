package Controller;

import Model.SimpleSnake;
import View.SnakeTextView;

/**
 * Class controls the Simple Snake game
 * @author Andreas Goll Rossau
 */
public class SnakeTextController {
    SimpleSnake game;
    SnakeTextView view;
    String game_status;

    /**
     * Constructor. The program never leaves this constructor unless the game ends
     * @param grid_x The grid size in the x dimension
     * @param grid_y The grid size in the y dimension
     * @author Andreas Goll Rossau
     */
    public SnakeTextController(int grid_x, int grid_y) {
        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new SnakeTextView();

        // This is the game loop only exited on game over
        do {
            view.drawBoard(grid_x, grid_y, game.get_snake_location(), game.get_mouse_location(), game.get_points());
            this.game_status = game.game_action(view.getInput());
        }
        while(game_status.equals("Playing"));

        System.out.println(game_status);

    }
}
