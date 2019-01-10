package View;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

/**
 * Class displays the Simple Snake game as text in the console and takes text input from user
 * @author Andreas Goll Rossau
 */
public class SnakeTextView {
    Scanner console = new Scanner(System.in);

    /**
     * Constructor
     * @author Andreas Goll Rossau
     */
    public SnakeTextView() {}

    /**
     * Method gets input from console
     * @return input string
     * @author Andreas Goll Rossau
     */
    public String getInput() {
        return console.next();
    }

    /**
     * Method 'draws' the game as characters in the console
     * @param grid_x grid size in x dimension
     * @param grid_y grid size in y dimension
     * @param snake_location list of locations of parts of the snake
     * @param mouse_location location of the mouse
     * @author Andreas Goll Rossau
     */
    public void drawBoard(int grid_x, int grid_y, List<Point> snake_location, Point mouse_location) {
        for (int i = 0; i < grid_y; i++) {
            for (int j = 0; j < grid_x; j++) {
                Point p = new Point(j, i);
                if (p.equals(mouse_location)) {
                    System.out.print("M");
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
}
