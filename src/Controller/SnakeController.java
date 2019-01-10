package Controller;

import Model.SimpleSnake;
import View.SnakeTextView;

public class SnakeController {
    SimpleSnake game;
    SnakeTextView view;
    String game_status;

    public SnakeController(int grid_x, int grid_y) {
        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new SnakeTextView();

        do {
            view.drawBoard(grid_x, grid_y, game.get_snake_location(), game.get_mouse_location());
            this.game_status = game.gameAction(view.getInput());
        }
        while(game_status.equals("Playing"));

        System.out.println(game_status);

    }
}
