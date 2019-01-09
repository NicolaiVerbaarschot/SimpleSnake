package Model;
import java.awt.Point;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SimpleSnake {


    private Snake solid;
    private Mouse mickey;
    private Gameboard gameboard;
    private int grid_x;
    private int grid_y;

    /**
     * Constructor
     * @param grid_x
     * @param grid_y
     * @author  Thea Birk Berger
     */
    public SimpleSnake(int grid_x, int grid_y) {

        // Set gameboard size
        this.grid_x = grid_x;
        this.grid_y = grid_y;
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
     * @author Thea Birk Berger
     */
    public String gameAction(String key_input) {

        List<Point> snake_location = solid.get_location();
        Point new_head = snake_location.get(snake_location.size() - 1);

        // Determine new_head coordinates
        switch (key_input) {
            case "up":
                new_head.setLocation(new_head.getX(), new_head.getY() - 1);
                break;
            case "down":
                new_head.setLocation(new_head.getX(), new_head.getY() + 1);
                break;
            case "left":
                new_head.setLocation(new_head.getX() - 1, new_head.getY());
                break;
            case "right":
                new_head.setLocation(new_head.getX() + 1, new_head.getY());
                break;
            default:
                break;
        }

        // Updating new_head according wall collision
        new_head.x = wall_collision_check((int) new_head.getX(), grid_x-1);
        new_head.y = wall_collision_check((int) new_head.getY(), grid_y-1);

        // Check for snake collision
        if (snake_location.contains(new_head)) {
            // TODO: implement gameOver() as a method in SimpleSnake;
            // This should return game over, but at this point the game always game overs, so it is disabled for now
            return "Game Overx";
        }

        // Check for mouse
        if (new_head.getX() == mickey.get_x_coordinate() && new_head.getY() == mickey.get_y_coordinate()) {
            // Grow snake
            solid.move((int) new_head.getX(), (int) new_head.getY(), true);
            // Update map
            gameboard.remove(new_head);
            // Update mouse location
            mickey.update_location(gameboard.get_board());
        }

        // Check for impossible action - otherwise move snake
        else if (new_head.getLocation() != snake_location.get(snake_location.size() - 2)) {
            // Move snake and define tail
            Point tail = solid.move((int) new_head.getX(), (int) new_head.getY(), false);
            // Update map
            gameboard.remove(new_head);
            gameboard.add(tail);
        }

        return "Ok";
    }

    public List<Point> get_snake_location() {
        return solid.get_location();
    }

    public Point get_mouse_location() {
        return new Point(mickey.get_x_coordinate(), mickey.get_y_coordinate());
    }


    /**
     * @param coordinate
     * @param coordinate_max
     * @return
     * @author Andreas Goll Rossau
     */
    private int wall_collision_check(int coordinate, int coordinate_max) {
        if(coordinate == -1) {
            coordinate = coordinate_max;
        }
        else if (coordinate == coordinate_max){
            coordinate = 0;
        }
        return coordinate;
    }
}


