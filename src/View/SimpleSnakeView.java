package View;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
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
    Stage primary_stage;
    Scene scene;
    HashMap<Integer, HashMap<Integer, Canvas>> display_map;
    GridPane grid_pane;
    Text score_bar;
    Image mouse = new Image("/image/mouse.png");
    Image snake = new Image("/image/snake.png");
    Image head = new Image("/image/head.png");
    Image emptyCell = new Image("/image/emptyCell.png");

    /**
     * Constructor
     * @author Andreas Goll Rossau
     */
    public SimpleSnakeView(int grid_x, int grid_y, Scene scene, GridPane grid_pane, Stage primary_stage) {
        this.grid_x = grid_x;
        this.grid_y = grid_y;
        this.scene = scene;
        this.display_map = new HashMap<>();
        this.grid_pane = grid_pane;
        this.primary_stage = primary_stage;
        this.score_bar = new Text();

        // Initialize score bar and add it to grid_pane
        set_score_bar(0);
        grid_pane.add(score_bar, 0, grid_y + 1, 2, 1);

        // Set window size
        primary_stage.setWidth(grid_x * 100);
        primary_stage.setHeight((grid_y * 100) + 50);

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
    public void draw_board(List<Point> snake_location, Point mouse_location) {
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
     * Method updates the text of the score bar to match the current game state
     * @param points denotes how many mice have been eaten
     * @author Thea Birk Berger
     */
    public void set_score_bar(int points) {
        // Set score bar text
        score_bar.setText("SCORE" + "   " + points);
        // Set score bar font
        score_bar.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        score_bar.setFill(Color.PINK);
        score_bar.setStroke(Color.BLACK);
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
