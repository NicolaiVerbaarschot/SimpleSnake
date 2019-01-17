package Model;

import java.awt.Point;

/**
 * @author Nicolai Verbaarschot
 */
public class SnakeSegment {

    private boolean is_head;
    private boolean is_tail;
    private Point coordinates;
    private Point previous_coordinates;
    private Point next_coordinates;

    /**
     * @author
     */
    SnakeSegment() {

        this.coordinates = null;
        this.previous_coordinates = null;
        this.next_coordinates = null;
    }

    /**
     * @param s
     * @author
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
     *
     * @param coordinates
     * @param previous_coordinates
     * @param next_coordinates
     * @author
     */
    SnakeSegment(Point coordinates, Point previous_coordinates, Point next_coordinates) {

        this.coordinates = coordinates;
        this.previous_coordinates = previous_coordinates;
        this.next_coordinates = next_coordinates;
    }

    /**
     * @return
     * @author
     */
    public Point get_coordinates() {
        return coordinates;
    }

    /**
     * @return
     * @author
     */
    public Point get_next_coordinates() {
        return next_coordinates;
    }

    /**
     * @return
     * @author
     */
    public Point get_previous_coordinates() {
        return previous_coordinates;
    }

    /**
     * @param coordinates
     * @author
     */
    public void set_coordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * @param next_coordinates
     * @author
     */
    void set_next_coordinates(Point next_coordinates) {
        this.next_coordinates = next_coordinates;
    }

    /**
     * @param previous_coordinates
     * @author
     */
    void set_previous_coordinates(Point previous_coordinates) {
        this.previous_coordinates = previous_coordinates;
    }

    /**
     * @param value
     * @author
     */
    void set_head(boolean value) {
        this.is_head = value;
    }

    /**
     * @param value
     * @author
     */
    void set_tail(boolean value) {
        this.is_tail = value;
    }

    /**
     * @return
     * @author
     */
    public boolean is_head() {
        return is_head;
    }

    /**
     * @return
     * @author
     */
    public boolean is_tail() {
        return is_tail;
    }






}
