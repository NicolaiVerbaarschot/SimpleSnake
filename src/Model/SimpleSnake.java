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
    public void gameAction(String key_input) {

        // Extracting current snake information and calculating new_head ("målfeltet")
        List<Point> snake_location = solid.get_location();
        Point new_head = creating_new_head(key_input, snake_location);

        // Updating new_head coordinates in the event of wall collision
        new_head.x = wall_collision_check((int) new_head.getX(), grid_x-1);
        new_head.y = wall_collision_check((int) new_head.getY(), grid_y-1);

        // Ending game in the event of snake collision
        if (snake_location.contains(new_head) && !(new_head.getLocation() == snake_location.get(snake_location.size() - 2))) {
            // TODO: implement Game Over;
        }

        // Updating fields in the event of mouse presence
        if (new_head.getX() == mickey.get_x_coordinate() && new_head.getY() == mickey.get_y_coordinate()) {
            grow_snake(new_head, snake_location);
        }

        // Updating fields in the event of no opposite direction attempt
        else if (!(new_head.getLocation() == snake_location.get(snake_location.size() - 2))) {
            move_snake(new_head);
        }
    }

    /**
     * creating_new_head
     * @param key_input
     * @param snake_location
     * @author Thea Birk Berger
     */
    // Translate key input to new_head coordinates
    private Point creating_new_head(String key_input, List<Point> snake_location) {
        // Extract current snake head information
        Point new_head = snake_location.get(snake_location.size() - 1);

        switch (key_input) {
            case "up":
                new_head.setLocation(new_head.getX(), new_head.getY() - 1); break;
            case "down":
                new_head.setLocation(new_head.getX(), new_head.getY() + 1); break;
            case "left":
                new_head.setLocation(new_head.getX() - 1, new_head.getY()); break;
            case "right":
                new_head.setLocation(new_head.getX() + 1, new_head.getY()); break;
            default: break;
        }
        return new_head;
    }

    /**
     * wall_collision_check
     * @param coordinate
     * @param coordinate_max
     * @return coordinate
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

    /**
     * grow_snake
     * @param new_head
     * @param snake_location
     * @author Thea Birk Berger
     */
    private void grow_snake(Point new_head, List<Point> snake_location) {
        // Grow snake
        solid.move((int) new_head.getX(), (int) new_head.getY(), true);
        // Update map
        gameboard.remove(new_head);
        // Update mouse location
        mickey.update_location(gameboard.get_board());
    }

    /**
     * move_snake
     * @param new_head
     * @author Thea Birk Berger
     */
    private void move_snake(Point new_head) {
        // Move snake and extract tail
        Point tail = solid.move((int) new_head.getX(), (int) new_head.getY(), false);
        // Update map
        gameboard.remove(new_head);
        gameboard.add(tail);
    }

    // for testing
    public List<Point> get_snake_location() {
        return solid.get_location();
    }
}


