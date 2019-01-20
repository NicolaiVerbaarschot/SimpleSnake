package Model;

import java.awt.Point;

/**
 * This class expands the snake segments from points to a more developed model capable of providing support for a dynamic body
 *
 * @author Nicolai Verbaarschot
 */
public class SnakeSegment {

    private boolean is_head;
    private boolean is_tail;
    private Point coordinates;
    private Point previous_coordinates;
    private Point next_coordinates;

    /**
     * Constructor
     *
     * @author Nicolai Verbaarschot
     */
    SnakeSegment() {

        this.coordinates = null;
        this.previous_coordinates = null;
        this.next_coordinates = null;
    }

    /**
     * Constructor
     *
     * @param s SnakeSegment to construct
     * @author Nicolai Verbaarschot
     */
    public SnakeSegment(SnakeSegment s) {
        this.coordinates = new Point(s.get_coordinates());

        if (s.get_previous_coordinates() != null) {
            this.previous_coordinates = new Point(s.get_previous_coordinates());
        }
        else {
            is_tail = true;
        }

        if (s.get_next_coordinates() != null) {
            this.next_coordinates = new Point(s.get_next_coordinates());
        }
        else {
            is_head = true;
        }
    }

    /**
     * Constructor
     *
     * @param coordinates current coordinates
     * @param previous_coordinates past coordinates
     * @param next_coordinates future coordinates
     * @author Nicolai verbaarschot
     */
    SnakeSegment(Point coordinates, Point previous_coordinates, Point next_coordinates) {

        this.coordinates = coordinates;
        this.previous_coordinates = previous_coordinates;
        this.next_coordinates = next_coordinates;
    }

    /**
     * @return current coordinates
     * @author Nicolai Verbaarschot
     */
    public Point get_coordinates() {
        return coordinates;
    }

    /**
     * @return future coordinates
     * @author Nicolai Verbaarschot
     */
    public Point get_next_coordinates() {
        return next_coordinates;
    }

    /**
     * @return past coordinates
     * @author Nicolai Verbaarschot
     */
    public Point get_previous_coordinates() {
        return previous_coordinates;
    }

    /**
     * @param next_coordinates coordinates to set future coordinates to
     * @author Nicolai Verbaarschot
     */
    void set_next_coordinates(Point next_coordinates) {
        this.next_coordinates = next_coordinates;
    }

    /**
     * @param previous_coordinates coordinates to set past coordinates to
     * @author Nicolai verbaarschot
     */
    void set_previous_coordinates(Point previous_coordinates) {
        this.previous_coordinates = previous_coordinates;
    }

    /**
     * @param value boolean value to set is_head field
     * @author Nicolai Verbaarschot
     */
    void set_head(boolean value) {
        this.is_head = value;
    }

    /**
     * @param value boolean value to set is_tail field
     * @author Nicolai Verbaarschot
     */
    void set_tail(boolean value) {
        this.is_tail = value;
    }

    /**
     * @return flag identifying if segment is head
     * @author Nicolai Verbaarshcot
     */
    public boolean is_head() {
        return is_head;
    }

    /**
     * @return flag identifying if segment is tail
     * @author Nicolai Verbaarschot
     */
    public boolean is_tail() {
        return is_tail;
    }






}
