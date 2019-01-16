package Model;

import java.awt.Point;

public class SnakeSegment {

    private boolean is_head;
    private boolean is_tail;
    private Point coordinates;
    private Point previous_coordinates;
    private Point next_coordinates;

    SnakeSegment() {

        this.coordinates = null;
        this.previous_coordinates = null;
        this.next_coordinates = null;
    }

    SnakeSegment(Point coordinates, Point previous_coordinates, Point next_coordinates) {

        this.coordinates = coordinates;
        this.previous_coordinates = previous_coordinates;
        this.next_coordinates = next_coordinates;
    }

    public Point get_coordinates() {
        return coordinates;
    }

    public Point get_next_coordinates() {
        return next_coordinates;
    }

    public Point get_previous_coordinates() {
        return previous_coordinates;
    }

    public void set_coordinates(Point coordinates) {
        this.coordinates = coordinates;
    }

    public void set_next_coordinates(Point next_coordinates) {
        this.next_coordinates = next_coordinates;
    }

    public void set_previous_coordinates(Point previous_coordinates) {
        this.previous_coordinates = previous_coordinates;
    }

    public void set_head(boolean value) {
        this.is_head = value;
    }

    public void set_tail(boolean value) {
        this.is_tail = value;
    }

    public boolean is_head() {
        return is_head;
    }

    public boolean is_tail() {
        return is_tail;
    }






}
