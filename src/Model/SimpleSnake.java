package Model;

import java.awt.Point;
import java.io.*;
import java.util.List;

/**
 * Class is main Model in Simple Snake
 * @author Thea Birk Berger
 */
public class SimpleSnake {

    private Snake solid;
    private Mouse mickey;
    private Mousetrack mousetrack;
    private int grid_x;
    private int grid_y;
    private int score;
    private int[] high_scores = new int[5];
    private String[] high_score_name = new String[5];
    private boolean score_on_leader_boards;
    private Point old_snake_tail;

    /**
     * Constructor sets SimpleSnake fields
     *
     * @param   grid_x: denotes the game frame width
     * @param   grid_y: denotes the game frame height
     * @author  Thea Birk Berger
     */
    public SimpleSnake(int grid_x, int grid_y) throws IOException {

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

        initialize_high_scores();



    }

    /**
     * This method initializes the array fields used to store and manipulate the games high scores
     *
     * @throws IOException file not found
     * @author Nicolai Verbaarschot
     */
    private void initialize_high_scores() throws IOException {
        File file = new File("res/highscores.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String str;
        int leader_board_position = 0;
        while ((str = br.readLine()) != null) {
            String[] tokens = str.split(" ");
            high_score_name[leader_board_position] = tokens[0];
            high_scores[leader_board_position] = Integer.parseInt(tokens[1]);
            leader_board_position++;
        }
        br.close();
    }

    /**
     * This method checks if the current score is a high score and saves to highscores.txt
     *
     * @throws IOException file not found
     * @author Nicolai Verbaarschot
     */
    public int update_high_scores(String name) throws IOException {

        boolean score_is_highest_score = score > high_scores[0];

        int leader_board_position = 5;


        // Handle all other score cases
        for (int i=0; i<=high_scores.length-1; i++)
            if (score > high_scores[i]) {
                System.arraycopy(high_scores, i, high_scores, i + 1, (high_scores.length - 1) - i);
                System.arraycopy(high_score_name, i, high_score_name, i+1, (high_score_name.length - 1) - i);
                high_scores[i] = score;
                high_score_name[i] = name;
                leader_board_position = i;
                break;
            }

        save_high_scores();

        return leader_board_position;

    }

    /**
     * This method writes the objects field high scores and names to highscore.txt
     *
     * @throws IOException file not found
     * @author Nicolai Verbaarschot
     */
    private void save_high_scores() throws IOException {
        FileWriter fw = new FileWriter("res/highscores.txt");

        for (int i = 0; i < high_scores.length; i++) {
            fw.write(high_score_name[i] + " " + high_scores[i] + "\n");
        }
        fw.close();
    }

    /**
     * This method is called publicly to determine if a high score has been achieved
     *
     * @return is_highest_score boolean flag used indicating if the current score is the high score
     * @author Nicolai Verbaarschot
     */
    public int[] get_high_scores() {
        return high_scores;
    }

    public String[] get_high_score_names () {
        return high_score_name;
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
     * Method returns snake as SnakeSegment objects
     * @return List of SnakeSegment object in snake
     * @author Andreas Goll Rossau
     */
    public List<SnakeSegment> get_snake_segments() {
        return solid.get_segments();
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
        int dx = 0;
        int dy = 0;

        switch (key_input.toLowerCase()) {
            case "up":
                dy = -1;
                target_cell.setLocation(current_head.getX(), current_head.getY() - 1); break;
            case "down":
                dy = 1;
                target_cell.setLocation(current_head.getX(), current_head.getY() + 1); break;
            case "left":
                dx = -1;
                target_cell.setLocation(current_head.getX() - 1, current_head.getY()); break;
            case "right":
                dx = 1;
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
            grow_snake(target_cell, dx, dy);
            score++;
            if ((mousetrack.get_track().isEmpty())) {
                return "Game Won";
            }
        }
        // Updating fields in the event of no opposite direction attempt
        else if (!target_cell.getLocation().equals(snake_location.get(1))) {
            move_snake(target_cell, dx, dy);
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
     * @param   dx: Difference in x dimension ignoring grid borders
     * @param   dy: Difference in y dimension ignoring grid borders
     * @author  Thea Birk Berger
     */
    private void grow_snake(Point target_cell, int dx, int dy) {

        // Grow snake
        solid.move((int) target_cell.getX(), (int) target_cell.getY(), dx, dy, true);

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
     * @param   dx: Difference in x dimension ignoring grid borders
     * @param   dy: Difference in y dimension ignoring grid borders
     * @author  Thea Birk Berger
     */
    private void move_snake(Point target_cell, int dx, int dy) {

        // Move snake and extract tail
        old_snake_tail = solid.move((int) target_cell.getX(), (int) target_cell.getY(), dx, dy, false);

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


