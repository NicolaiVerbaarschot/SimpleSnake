package Controller;

import Model.SimpleSnake;
import View.SnakeTextView;

public class SnakeController {
    SimpleSnake game;
    SnakeTextView view;

    public SnakeController(int grid_x, int grid_y) {
        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new SnakeTextView();


        do {
            view.drawBoard(grid_x, grid_y, game.get_snake_location(), game.get_mouse_location());
        }
        while(!game.gameAction(view.getInput()).equals("Game Over"));

        System.out.println("Game Over");
    }
}
