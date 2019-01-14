package View;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

/**
 * Class displays the Simple Snake game as text in the console and takes text input from user
 * @author Andreas Goll Rossau
 */
public class SimpleSnakeView {
    private int grid_x;
    private int grid_y;
    private int cell_size;
    private Stage primary_stage;
    private Scene scene;
    private HashMap<Integer, HashMap<Integer, Canvas>> display_map;
    private GridPane grid_pane;
    private Text endgame_text = new Text();
    private Rectangle endgame_background = new Rectangle();
    private Text score_bar;
    private Image mouse = new Image("/image/mouse.png");
    private Image snake = new Image("/image/snake.png");
    private Image head = new Image("/image/head.png");
    private Image emptyCell = new Image("/image/emptyCell.png");
    private Point old_mouse_location;
    private Point old_snake_head;

    /**
     * Constructor
     * @param grid_x
     * @param grid_y
     * @param scene
     * @param grid_pane
     * @param primary_stage
     * @author Andreas Goll Rossau
     */
    public SimpleSnakeView(int grid_x, int grid_y, Scene scene, GridPane grid_pane, Stage primary_stage) {
        this.grid_x = grid_x;
        this.grid_y = grid_y;
        this.cell_size = Math.min( (100/Math.max(grid_x,grid_y))*9, 100 );
        this.scene = scene;
        this.display_map = new HashMap<>();
        this.grid_pane = grid_pane;
        this.primary_stage = primary_stage;
        this.score_bar = new Text();

        // Initialize score bar and add it to grid_pane
        set_score_bar(0);
        grid_pane.add(score_bar, 0, grid_y + 1, grid_x, 1);

        // Set window size
        primary_stage.setWidth(grid_x * cell_size);
        primary_stage.setHeight((grid_y * cell_size) + 65);

        // Add canvas cells to display_map and add display_map to grid_pane
        for (int i = 0; i < grid_x; i++) {
            display_map.put(i, new HashMap<>());
            for (int j = 0; j < grid_y; j++) {
                display_map.get(i).put(j, new Canvas(cell_size, cell_size));
                grid_pane.add(display_map.get(i).get(j), i, j, 1, 1);
            }
        }
    }

    /**
     * Method draws the game board for a new game
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
                    display_map.get(j).get(i).getGraphicsContext2D().drawImage(mouse, 0, 0, cell_size, cell_size);
                    old_mouse_location = new Point(mouse_location);
                }
                else if (p.equals(snake_location.get(0))) {
                    // Draw snake head
                    display_map.get(j).get(i).getGraphicsContext2D().drawImage(head, 0, 0,  cell_size, cell_size);
                    old_snake_head = new Point(snake_location.get(0));
                }
                else if (snake_location.contains(p)) {
                    // Draw snake body
                    display_map.get(j).get(i).getGraphicsContext2D().drawImage(snake, 0, 0,  cell_size, cell_size);
                }
                else {
                    // Draw empty cell
                    display_map.get(j).get(i).getGraphicsContext2D().drawImage(emptyCell, 0, 0, cell_size, cell_size);
                }
            }
        }
    }

    /**
     * Method updates game window by only redrawing as needed
     * @param snake_head position of the snake's head
     * @param old_snake_tail old position of the snake's tail
     * @param mouse_location location of the mouse
     * @author Andreas Goll Rossau
     */
    public void update_board(Point snake_head, Point old_snake_tail, Point mouse_location) {
        if (!snake_head.equals(old_snake_head)) {
            // A mouse has been eaten
            if (!mouse_location.equals(old_mouse_location)) {
                display_map.get((int) mouse_location.getX()).get((int) mouse_location.getY()).getGraphicsContext2D().drawImage(mouse, 0, 0, cell_size, cell_size);
                old_mouse_location.setLocation(mouse_location);
            }
            // No mouse has been eaten
            else {
                display_map.get((int) old_snake_tail.getX()).get((int) old_snake_tail.getY()).getGraphicsContext2D().drawImage(emptyCell, 0, 0, cell_size, cell_size);
            }

            display_map.get((int) old_snake_head.getX()).get((int) old_snake_head.getY()).getGraphicsContext2D().drawImage(snake, 0, 0, cell_size, cell_size);
            display_map.get((int) snake_head.getX()).get((int) snake_head.getY()).getGraphicsContext2D().drawImage(head, 0, 0, cell_size, cell_size);
            old_snake_head.setLocation(snake_head);
        }
    }

    /**
     * Method displays game Over and game Won
     * @param status denotes either "Game Over" or "Game Won"
     * @author Thes Birk Berger
     */
    public void print_status(String status) {

        // Set text
        if (status.equals("Game Over")) {
            this.endgame_text.setText("YOU HAVE\nLOST THE GAME");
        } else {
            this.endgame_text.setText("CONGRATULATIONS!\n YOU HAVE WON THE GAME");

        }
        // Set text position
        this.endgame_text.setTextAlignment(TextAlignment.CENTER);
        this.endgame_text.setWrappingWidth(grid_x * cell_size);

        // Set text font
        this.endgame_text.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
        this.endgame_text.setFill(Color.CHOCOLATE);
        this.endgame_text.setStroke(Color.BLACK);

        // Set rectangle parameters
        endgame_background.setHeight(grid_y*cell_size);
        endgame_background.setWidth(grid_x*cell_size);
        endgame_background.setFill(Color.CADETBLUE);

        // Add endgame_background and text to grid_pane
        this.grid_pane.add(endgame_background, 0, 0, grid_x, grid_y);
        this.grid_pane.add(this.endgame_text, 0,0, grid_x, grid_y);
    }

    public void clear_endgame () {
        grid_pane.getChildren().remove(endgame_text);
        grid_pane.getChildren().remove(endgame_background);
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
}
