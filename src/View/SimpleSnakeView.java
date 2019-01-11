package View;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Class displays the Simple Snake game as text in the console and takes text input from user
 * @author Andreas Goll Rossau
 */
public class SimpleSnakeView {
    int grid_x;
    int grid_y;
    Scene scene;
    HashMap<Integer, HashMap<Integer, Canvas>> display_map;
    GridPane grid_pane;
    Image mouse = new Image("/image/mouse.png");
    Image snake = new Image("/image/snake.png");
    Image head = new Image("/image/head.png");
    Image emptyCell = new Image("/image/emptyCell.png");

    /**
     * Constructor
     * @author Andreas Goll Rossau
     */
    public SimpleSnakeView(int grid_x, int grid_y, Scene scene, GridPane grid_pane) {
        this.grid_x = grid_x;
        this.grid_y = grid_y;
        this.scene = scene;
        this.display_map = new HashMap<>();
        this.grid_pane = grid_pane;

        // Add canvas cells to display_map and add display_map to grid_pane
        for (int i = 0; i < grid_x; i++) {
            display_map.put(i, new HashMap<>());
            for (int j = 0; j < grid_y; j++) {
                display_map.get(i).put(j, new Canvas(100, 100));
                grid_pane.add(display_map.get(i).get(j), i, j, 1, 1);
            }
        }

    }


    // Prints Game Over and Game Won to the console
    public void print_status(String status) {
        System.out.println(status);
    }

    /**
     * Method 'draws' the game as characters in the console
     * @param snake_location list of locations of parts of the snake
     * @param mouse_location location of the mouse
     * @author Andreas Goll Rossau
     */
    public void draw_board(List<Point> snake_location, Point mouse_location, int points) {
        for (int i = 0; i < grid_y; i++) {
            for (int j = 0; j < grid_x; j++) {
                Point p = new Point(j, i);
                if (p.equals(mouse_location)) {
                    // Draw mouse
                    display_map.get(j).get(i).getGraphicsContext2D().drawImage(mouse, 0, 0);
                }
                else if (p.equals(snake_location.get(0))) {
                    // Draw snake head
                    display_map.get(j).get(i).getGraphicsContext2D().drawImage(head, 0, 0);

                }
                else if (snake_location.contains(p)) {
                    // Draw snake body
                    display_map.get(j).get(i).getGraphicsContext2D().drawImage(snake, 0, 0);

                }
                else {
                    // Draw empty cell
                    display_map.get(j).get(i).getGraphicsContext2D().drawImage(emptyCell, 0, 0);

                }
            }
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
