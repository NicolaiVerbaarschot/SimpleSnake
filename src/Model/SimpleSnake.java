package Model;
import java.awt.Point;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimpleSnake {


    private Snake solid;
    private Mouse mickey;
    private Gameboard gameboard;

    /**
     * Constructor
     * @param grid_x
     * @param grid_y
     * @author  Thea Birk Berger
     */
    public SimpleSnake(int grid_x, int grid_y) {

        // Create snake
        this.solid = new Snake(grid_x, grid_y);
        // Create gameboard
        this.gameboard = new Gameboard(grid_x, grid_y);
        // Remove snake location from gameboard map
        for (Point p: solid.get_location()) {
            gameboard.remove(p);
        }
        // Create mouse
        this.mickey = new Mouse(gameboard.get_board());
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
            // Grow snake
            solid.move((int) newHead.getX(), (int) newHead.getY(), true);
            // Update map
            gameboard.remove(newHead);
            // Update mouse location
            mickey.update_location(gameboard.get_board());
        }

        // Check for impossible action - otherwise move snake
        else if (newHead.getLocation() != snake_location.get(snake_location.size() - 2)) {
            // Move snake and define tail
            Point tail = solid.move((int) newHead.getX(), (int) newHead.getY(), false);
            // Update map
            gameboard.remove(newHead);
            gameboard.add(tail);
        }
    }

    public List<Point> get_snake_location() {
        return solid.get_location();
    }
}


