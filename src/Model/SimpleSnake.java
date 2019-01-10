package Model;
import java.awt.Point;
import java.util.List;

public class SimpleSnake {


    private Snake solid;
    private Mouse mickey;
    private Mousetrack mousetrack;
    private int grid_x;
    private int grid_y;

    /**
     * Constructor
     * @param grid_x
     * @param grid_y
     * @author  Thea Birk Berger
     */
    public SimpleSnake(int grid_x, int grid_y) {

        // Set mousetrack size
        this.grid_x = grid_x;
        this.grid_y = grid_y;
        // Create snake
        this.solid = new Snake(grid_x, grid_y);
        // Create mousetrack
        this.mousetrack = new Mousetrack(grid_x, grid_y);
        // Remove snake location from mousetrack map
        for (Point p: solid.get_location()) {
            mousetrack.remove(p);
        }
        // Create mouse
        this.mickey = new Mouse(mousetrack.get_board());
    }


    /**
     * gameAction
     * @param key_input
     * @author Thea Birk Berger
     */
    public String gameAction(String key_input) {

        // Extracting current snake information and calculating new_head ("målfeltet")
        List<Point> snake_location = solid.get_location();
        Point new_head = new Point(snake_location.get(0));

        switch (key_input) {
            case "up":
                new_head.setLocation(new_head.getX(), new_head.getY() - 1); break;
            case "down":
                new_head.setLocation(new_head.getX(), new_head.getY() + 1); break;
            case "left":
                new_head.setLocation(new_head.getX() - 1, new_head.getY()); break;
            case "right":
                new_head.setLocation(new_head.getX() + 1, new_head.getY()); break;
            default: return "Ok";
        }

        // Updating new_head coordinates in the event of wall collision
        new_head.x = wall_collision_check((int) new_head.getX(), grid_x-1);
        new_head.y = wall_collision_check((int) new_head.getY(), grid_y-1);

        // Ending game in the event of snake collision
        if (snake_location.contains(new_head) && !new_head.equals(snake_location.get(1))) {
            // TODO: implement gameOver() as a method in SimpleSnake;
            // This should return game over, but at this point the game always game overs, so it is disabled for now
            return "Game Over";
        }

        // Updating fields in the event of mouse presence
        if (new_head.getX() == mickey.get_x_coordinate() && new_head.getY() == mickey.get_y_coordinate()) {
            grow_snake(new_head);
        }

        // Updating fields in the event of no opposite direction attempt
        else if (!new_head.getLocation().equals(snake_location.get(1))) {
            move_snake(new_head);
        }

        return "Ok";
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
        else if (coordinate == coordinate_max + 1){
            coordinate = 0;
        }
        return coordinate;
    }


    /**
     * grow_snake (mouse-function)
     * @param new_head
     * @author Thea Birk Berger
     */
    private void grow_snake(Point new_head) {
        // Grow snake
        solid.move((int) new_head.getX(), (int) new_head.getY(), true);
        // Update map
        mousetrack.remove(new_head);
        // Update mouse location
        mickey.update_location(mousetrack.get_board());
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
        mousetrack.remove(new_head);
        mousetrack.add(tail);
    }


    // for testing
    public List<Point> get_snake_location() {
        return solid.get_location();
    }

    public Point get_mouse_location() {
        return new Point(mickey.get_x_coordinate(), mickey.get_y_coordinate());
    }
}


