package View;

import javafx.scene.Scene;

import java.awt.*;
import java.util.List;

/**
 * Class displays the Simple Snake game as text in the console and takes text input from user
 * @author Andreas Goll Rossau
 */
public class SnakeTextViewKeyPress {
    int grid_x;
    int grid_y;

    /**
     * Constructor
     * @author Andreas Goll Rossau
     */
    public SnakeTextViewKeyPress(Scene scene, int grid_x, int grid_y) {
        this.grid_x = grid_x;
        this.grid_y = grid_y;
    }



    public void print_status(String status) {
        System.out.println(status);
    }

    /**
     * Method 'draws' the game as characters in the console
     * @param snake_location list of locations of parts of the snake
     * @param mouse_location location of the mouse
     * @author Andreas Goll Rossau
     */
    public void drawBoard(List<Point> snake_location, Point mouse_location, int points) {
        print_spaces(grid_x/2);
        System.out.println(points);
        for (int i = 0; i < grid_y; i++) {
            for (int j = 0; j < grid_x; j++) {
                Point p = new Point(j, i);
                if (p.equals(mouse_location)) {
                    System.out.print("M");
                }
                else if (p.equals(snake_location.get(0))) {
                    System.out.print("H");
                }
                else if (snake_location.contains(p)) {
                    System.out.print("S");
                }
                else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }

    /**
     * Helper method for printing a variable number of spaces
     * @param n number of spaces to print
     * @author Andreas Goll Rossau
     */
    private void print_spaces(int n) {
        for (int i = 0; i < n; i++) {
            System.out.print(" ");
        }
    }
}