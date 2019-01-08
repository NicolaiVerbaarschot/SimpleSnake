package Model;
import java.awt.Point;
import java.util.List;

public class SimpleSnake {


    private Snake solid;
    private Mouse mickey;

    public SimpleSnake(int grid_x, int grid_y) {
        this.solid = new Snake(grid_x, grid_y);
        this.mickey = new Mouse(grid_x, grid_y);

    }

    public void gameAction(String key_input, int grid_x, int grid_y) {

        List<Point> snake_location = Snake.get_location();
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
        for (int i = 0; i < snake_location.size; i++) {
            if (snake_location.get(i) == newHead.getLocation()) {
                // TODO: implement gameOver() as a method in SimpleSnake;
            }
        }

        // Check for mouse
        if (newHead.getX() == mickey.get_x_coordinate() && newHead.getY() == mickey.get_y_coordinate()) {
            Snake.move(newHead.getX(), newHead.getY(), 1);
            // Update local snake location
            snake_location = Snake.get_location();
            Mouse.update_location(grid_x, grid_y, snake_location);
        }

        // Check for impossible action - otherwise move snake
        else if (newHead.getLocation() != snake_location.get(snake_location.size() - 2)) {
            Snake.move(newHead.getX(), newHead.getY(), 0);
        }
    }
}


