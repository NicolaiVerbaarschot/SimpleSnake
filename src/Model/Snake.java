package Model;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

/**
 * Class is a snake for the Simple Snake game. It keeps track of the snake's location and moves the snake
 *
 * @author Andreas Goll Rossau
 */
class Snake {

//    private List<Point> snake_location = new ArrayList<>();
    private List<SnakeSegment> snake = new ArrayList<>();

    /** Snake constructor determines the initial position of the snake from the size of the grid
     *
     * @param   grid_x: The maximum gridsize in the x direction
     * @param   grid_y: The maximum gridsize in the y direction
     * @author  Andreas Goll Rossau
     */
    Snake(int grid_x, int grid_y) {
//        snake_location.add(0, new Point(grid_x/2, grid_y/2));
//        snake_location.add(0, new Point(grid_x/2, grid_y/2 + 1));

        Point initial_head = new Point(grid_x/2, grid_y/2 - 1);
        Point initial_tail = new Point(grid_x/2, grid_y/2);
        snake.add(0, new SnakeSegment(initial_tail, null, null));
        snake.add(0, new SnakeSegment(initial_head, null, null));
    }

    /**
     * Method moves the snake's head to the specified coordinates
     *
     * @param   x: New x coordinate for snake head
     * @param   y: New y coordinate for snake head
     * @param   will_grow: True if the snake is supposed to grow
     * @return  location of the snakes tail
     * @author  Andreas Goll Rossau
     */
    Point move(int x, int y, boolean will_grow) {

        SnakeSegment tail = new SnakeSegment();

        snake.add(0, new SnakeSegment(new Point(x, y), null, null));

        // If the snake does not grow, the tail is deleted
        if (!will_grow) {
            tail = snake.get(snake.size() - 1);
            snake.remove(snake.size() - 1);
        }
        return tail.get_coordinates();
    }

    /**
     * Returns a copy of the snake's location array
     *
     * @return Copy of snake's location array
     * @author Andreas Goll Rossau
     */
    List<Point> get_location() {
        List<Point> points = new ArrayList<>();

        for (int i=snake.size()-1; i>=0; i--) {
            points.add(0,snake.get(i).get_coordinates());
        }
        return points;
    }





}