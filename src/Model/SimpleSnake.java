package Model;

import java.awt.Point;
import java.util.List;

public class SimpleSnake {

    private Snake solid;
    private Mouse mickey;
    private Mousetrack mousetrack;
    private int grid_x;
    private int grid_y;
    private int score;
    private Point old_snake_tail;

    /**
     * Constructor sets SimpleSnake fields
     *
     * @param   grid_x: denotes the game frame width
     * @param   grid_y: denotes the game frame height
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
        for (Point p : solid.get_location()) {
            mousetrack.remove(p);
        }
        // Create mouse
        this.mickey = new Mouse(mousetrack.get_track());

        // Set score
        this.score = 0;
    }

    /**
     * Helper method to pass old position of snake's tail
     *
     * @return  point where the has just moved from
     * @author  Andreas Goll Rossau
     */
    public Point get_tail() {
        return old_snake_tail;
    }

    /**
     * Helper method to get location of the snake
     *
     * @return  Array of Point objects with the location of the snake
     * @author  Andreas Goll Rossau
     */
    public List<Point> get_snake_location() {
        return solid.get_location();
    }

    /**
     * Helper method to get mouse position
     *
     * @return  Point which has the coordinates of the mouse
     * @author  Andreas Goll Rossau
     */
    public Point get_mouse_location() {
        return new Point(mickey.get_x_coordinate(), mickey.get_y_coordinate());
    }

    /**
     * Getter method for score
     *
     * @return  current score
     * @author  Andreas Goll Rossau
     */
    public int get_score() {
        return score;
    }

    /**
     * Method determines whether snake should grow or move and notifies the controller if the game should continue of finish
     *
     * @param    key_input: String representing a key pressed by user during the game
     * @return   game status to SnakeController
     * @author   Thea Birk Berger
     */
    public String game_action(String key_input) {

        // Extracting current snake information and calculating target cell ("maalfeltet")
        List<Point> snake_location = solid.get_location();
        Point current_head = new Point(snake_location.get(0));
        Point target_cell = new Point();

        switch (key_input.toLowerCase()) {
            case "up":
                target_cell.setLocation(current_head.getX(), current_head.getY() - 1); break;
            case "down":
                target_cell.setLocation(current_head.getX(), current_head.getY() + 1); break;
            case "left":
                target_cell.setLocation(current_head.getX() - 1, current_head.getY()); break;
            case "right":
                target_cell.setLocation(current_head.getX() + 1, current_head.getY()); break;
            case "r": return "Restart";
            case "escape": return "Exit";
            default: return "Playing";
        }
        // Updating target_cell coordinates in the event of wall collision
        target_cell.x = wall_collision_check((int) target_cell.getX(), grid_x - 1);
        target_cell.y = wall_collision_check((int) target_cell.getY(), grid_y - 1);

        // Ending game in the event of snake collision
        if (snake_location.contains(target_cell) && !target_cell.equals(snake_location.get(1)) && !target_cell.equals(snake_location.get(snake_location.size() - 1))) {
            return "Game Over";
        }
        // Updating fields in the event of mouse presence
        if (target_cell.getX() == mickey.get_x_coordinate() && target_cell.getY() == mickey.get_y_coordinate()) {
            grow_snake(target_cell);
            score++;
            if ((mousetrack.get_track().isEmpty())) {
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
     *
     * @param   coordinate: coordinate of snake head
     * @param   coordinate_max: maximum coordinate in dimension
     * @return  possibly corrected coordinate
     * @author  Andreas Goll Rossau
     */
    private int wall_collision_check(int coordinate, int coordinate_max) {

        if (coordinate == -1) {
            coordinate = coordinate_max;
        } else if (coordinate == coordinate_max + 1) {
            coordinate = 0;
        }
        return coordinate;
    }

    /**
     * This method grows the snake by one unit and updates the mousetrack accordingly
     *
     * @param   target_cell: The cell to be moved into
     * @author  Thea Birk Berger
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
     * This method moves the snake and frees previously occupied cells
     *
     * @param   target_cell: The location of the snake head post-movement
     * @author  Thea Birk Berger
     */
    private void move_snake(Point target_cell) {

        // Move snake and extract tail
        old_snake_tail = solid.move((int) target_cell.getX(), (int) target_cell.getY(), false);

        // Update mousetrack
        mousetrack.add(old_snake_tail);
        mousetrack.remove(target_cell);
    }

    /**
     * This method resets the game by creating new instances of each game element, removing the previous snake locations, and resetting the points
     *
     * @author  Nicolai Verbaarschot
     */
    public void reset_game() {

        // Create new mousetrack (gameboard)
        this.mousetrack = new Mousetrack(grid_x, grid_y);

        // Create new (initial) snake
        this.solid = new Snake(grid_x, grid_y);

        // Remove snake location from mousetrack
        for (Point p : solid.get_location()) {
            mousetrack.remove(p);
        }
        // Create mouse
        this.mickey = new Mouse(mousetrack.get_track());

        // Reset points
        this.score = 0;
    }
}
