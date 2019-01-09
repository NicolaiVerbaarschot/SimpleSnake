package Model;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

/**
 * Class is a snake for the Simple Snake game. It keeps track of the snake's location and moves the snake
 * @author Andreas Goll Rossau
 */
public class Snake {
    private List<Point> snake_location = new ArrayList<>();

    /**
     * Snake constructor determines the initial position of the snake from the size of the grid
     * @param grid_x The maximum gridsize in the x direction
     * @param grid_y The maximum gridsize in the y direction
     * @author Andreas Goll Rossau
     */
    public Snake(int grid_x, int grid_y) {
        snake_location.add(0, new Point(grid_x/2, grid_y/2));
        snake_location.add(0, new Point(grid_x/2, grid_y/2 + 1));
    }

    /**
     * Method moves the snake's head to the specified coordinates
     * @param x New x coordinate for snake head
     * @param y New y coordinate for snake head
     * @param will_grow True if the snake is supposed to grow
     * @author Andreas Goll Rossau
     */
    public Point move(int x, int y, boolean will_grow) {
        Point tail = new Point();

        snake_location.add(0, new Point(x, y));

        // If the snake does not grow, the tail is deleted
        if (!will_grow) {
            tail = snake_location.get(snake_location.size() - 1);
            snake_location.remove(snake_location.size() - 1);
        }

        return tail;
    }

    /**
     * Returns a copy of the snake's location array
     * @return Copy of snake's location array
     * @author Andreas Goll Rossau
     */
    public List<Point> get_location() {
        return new ArrayList<>(snake_location);
    }
}