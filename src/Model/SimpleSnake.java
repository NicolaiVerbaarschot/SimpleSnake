package Model;

import java.awt.Point;
import java.util.List;

public class SimpleSnake {

    private Snake solid;
    private Mouse mickey;
    private Mousetrack mousetrack;
    private int grid_x;
    private int grid_y;
    private int points = 0;

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
        // Remove snake location from mousetrack
        for (Point p: solid.get_location()) {
            mousetrack.remove(p);
        }
        // Create mouse
        this.mickey = new Mouse(mousetrack.get_track());
    }


    /**
     * gameAction
     * @param key_input
     * @author Thea Birk Berger
     */
    public String gameAction(String key_input) {

        // Extracting current snake information and calculating target_cell ("målfeltet")
        List<Point> snake_location = solid.get_location();
        Point target_cell = new Point(snake_location.get(0));

        switch (key_input.toLowerCase()) {
            case "up":
                target_cell.setLocation(target_cell.getX(), target_cell.getY() - 1); break;
            case "down":
                target_cell.setLocation(target_cell.getX(), target_cell.getY() + 1); break;
            case "left":
                target_cell.setLocation(target_cell.getX() - 1, target_cell.getY()); break;
            case "right":
                target_cell.setLocation(target_cell.getX() + 1, target_cell.getY()); break;
            default: return "Playing";
        }

        // Updating target_cell coordinates in the event of wall collision
        target_cell.x = wall_collision_check((int) target_cell.getX(), grid_x-1);
        target_cell.y = wall_collision_check((int) target_cell.getY(), grid_y-1);

        // Ending game in the event of snake collision
        if (snake_location.contains(target_cell) && !target_cell.equals(snake_location.get(1)) && !target_cell.equals(snake_location.get(snake_location.size()-1))) {
            // TODO: implement gameOver();
            // This should return game over, but at this point the game always game overs, so it is disabled for now
            return "Game Over";
        }

        // Updating fields in the event of mouse presence
        if (target_cell.getX() == mickey.get_x_coordinate() && target_cell.getY() == mickey.get_y_coordinate()) {
            grow_snake(target_cell);
            points++;
            if ((mousetrack.get_track().isEmpty())) {
                // TODO: implement gameWon();
                return "Game Won";
            }
        }

        // Updating fields in the event of no opposite direction attempt
        else if (!target_cell.getLocation().equals(snake_location.get(1))) {
            move_snake(target_cell);
        }

        return "Playing";
    }


    /**
     * Method checks for snake head colliding with walls and updates coordinates if needed
     * @param coordinate coordinate of snake head
     * @param coordinate_max maximum coordinate in dimension
     * @return possibly corrected coordinate
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
     * @param target_cell
     * @author Thea Birk Berger
     */
    private void grow_snake(Point target_cell) {
        // Grow snake
        solid.move((int) target_cell.getX(), (int) target_cell.getY(), true);
        // Update mousetrack
        mousetrack.remove(target_cell);
        if (!(mousetrack.get_track().isEmpty())) {
            // Update mouse location/create new mouse
            mickey.update_location(mousetrack.get_track());
        }
    }


    /**
     * move_snake
     * @param new_head
     * @author Thea Birk Berger
     */
    private void move_snake(Point new_head) {
        // Move snake and extract tail
        Point tail = solid.move((int) new_head.getX(), (int) new_head.getY(), false);
        // Update mousetrack
        mousetrack.remove(new_head);
        mousetrack.add(tail);
    }


    // for testing
    public List<Point> get_snake_location() {
        return solid.get_location();
    }

    /**
     * Helper method to get mouse position
     * @return Point which has the coordinates of the mouse
     * @author Andreas Goll Rossau
     */
    public Point get_mouse_location() {
        return new Point(mickey.get_x_coordinate(), mickey.get_y_coordinate());
    }

    /**
     * Getter method for points
     * @return current points
     * @author Andreas Goll Rossau
     */
    public int get_points() {
        return points;
    }
}


