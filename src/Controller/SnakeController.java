package Controller;

import Model.SimpleSnake;
import View.SnakeTextViewKeyPress;

/**
 * Class controls the Simple Snake game
 * @author Andreas Goll Rossau
 */
public class SnakeController {
    SimpleSnake game;
    SnakeTextViewKeyPress view;
    String game_status;
    int grid_x;
    int grid_y;

    /**
     * Constructor. The program never leaves this constructor unless the game ends
     * @param grid_x The grid size in the x dimension
     * @param grid_y The grid size in the y dimension
     * @author Andreas Goll Rossau
     */
    public SnakeController(int grid_x, int grid_y) {
        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new SnakeTextViewKeyPress();
        this.grid_x = grid_x;
        this.grid_y = grid_y;

        view.drawBoard(grid_x, grid_y, game.get_snake_location(), game.get_mouse_location(), game.get_points());
    }

    public void keyPress(String code) {
        game_status = game.gameAction(code);
        if (game_status.equals("Playing")) {
            view.drawBoard(grid_x, grid_y, game.get_snake_location(), game.get_mouse_location(), game.get_points());
        }
        else {
            view.print_status(game_status);
            System.exit(0);
        }
    }
}
