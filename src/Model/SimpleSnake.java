package Model;
import java.awt.Point;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimpleSnake {


    private Snake solid;
    private Mouse mickey;
    private Map<Integer, Map<Integer, Integer>> game_board;

    /**
     * Constructor
     * @param grid_x
     * @param grid_y
     * @author  Thea Birk Berger
     */
    public SimpleSnake(int grid_x, int grid_y) {
        this.solid = new Snake(grid_x, grid_y);
        this.mickey = new Mouse(grid_x, grid_y);
        this.game_board = new LinkedHashMap<>();

        for (int i = 0; i < grid_x; i++) {
            game_board.put(i, new LinkedHashMap<>());
            for (int j = 0; j < grid_y; j++) {
                game_board.get(i).put(j, 0);
            }
        }

        for (Point p: solid.get_location()) {
            game_board.get((int) p.getX()).remove((int) p.getY());
        }
    }

    /**
     * gameAction
     * @param key_input
     * @param grid_x
     * @param grid_y
     * @author Thea Birk Berger
     */
    public void gameAction(String key_input, int grid_x, int grid_y) {

        List<Point> snake_location = solid.get_location();
        Point newHead = snake_location.get(snake_location.size() - 1);

        // Determine newHead coordinates
        switch (key_input) {
            case "up":
                newHead.setLocation(newHead.getX(), newHead.getY() - 1);
                break;
            case "down":
                newHead.setLocation(newHead.getX(), newHead.getY() + 1);
                break;
            case "left":
                newHead.setLocation(newHead.getX() - 1, newHead.getY());
                break;
            case "right":
                newHead.setLocation(newHead.getX() + 1, newHead.getY());
                break;
            default:
                break;
        }

        // Check for collision
        for (int i = 0; i < snake_location.size(); i++) {
            if (snake_location.get(i) == newHead.getLocation()) {
                // TODO: implement gameOver() as a method in SimpleSnake;
            }
        }

        // Check for mouse
        if (newHead.getX() == mickey.get_x_coordinate() && newHead.getY() == mickey.get_y_coordinate()) {
            solid.move((int) newHead.getX(), (int) newHead.getY(), true);
            game_board.get((int) newHead.getX()).put((int) newHead.getY(), 0);
            // Update local snake location
            snake_location = solid.get_location();
            mickey.update_location(grid_x, grid_y, snake_location);
        }

        // Check for impossible action - otherwise move snake
        else if (newHead.getLocation() != snake_location.get(snake_location.size() - 2)) {
            Point tail = solid.move((int) newHead.getX(), (int) newHead.getY(), false);
            game_board.get((int) newHead.getX()).put((int) newHead.getY(), 0);
            game_board.get((int) tail.getX()).remove((int) tail.getY());
        }
    }
}


