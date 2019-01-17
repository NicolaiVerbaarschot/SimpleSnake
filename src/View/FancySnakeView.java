package View;

import Model.SnakeSegment;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.Point;
import java.util.List;

/**
 * This class displays the Simple Snake game as text in the console and takes text input from user
 *
 * @author  Andreas Goll Rossau
 */
public class FancySnakeView {
    private int grid_x;
    private int grid_y;
    private int cell_size;

    private DisplayMap avatar_map;

    private GridPane avatars;

    private Text endgame_text = new Text();
    private Text score_bar;
    private Rectangle endgame_background = new Rectangle();

    private SpriteHolder sprites;

    private Point old_mouse_location;
    private SnakeSegment old_snake_head;

    /**
     * Constructor initializes the view, setting the scene dimensions, and adding a gridpane of canvases
     *
     * @param   grid_x: Horizontal dimension
     * @param   grid_y: Vertical dimension
     * @param   stack_pane: JavaFX Node
     * @param   primary_stage: JavaFX Node
     * @author  Andreas Goll Rossau
     */
    public FancySnakeView(int grid_x, int grid_y, StackPane stack_pane, Stage primary_stage) {
        this.grid_x = grid_x;
        this.grid_y = grid_y;
        this.cell_size = Math.min( (100/Math.max(grid_x,grid_y))*9, 100 );

        DisplayMap background_map = new DisplayMap(grid_x, grid_y, cell_size);
        this.avatar_map = new DisplayMap(grid_x, grid_y, cell_size);

        GridPane background = new GridPane();
        this.avatars = new GridPane();

        this.score_bar = new Text();

        this.sprites = new SpriteHolder("fancy");

        stack_pane.getChildren().add(0, background);
        stack_pane.getChildren().add(1, avatars);

        // Initialize score bar and add it to grid_pane
        set_score_bar(0);
        background.add(score_bar, 0, grid_y + 1, grid_x, 1);

        // Set window size
        primary_stage.setWidth(grid_x * cell_size);
        primary_stage.setHeight((grid_y * cell_size) + 65);

        // Add canvas cells to background_map and add background_map to grid_pane
        background_map.addToGrid(background);
        background_map.drawAll(sprites.getEmptyCell());
        avatar_map.addToGrid(avatars);
    }

    /**
     * Method draws the game board for a new game
     *
     * @param   snake_location: list of locations of parts of the snake
     * @param   mouse_location: location of the mouse
     * @author  Andreas Goll Rossau
     */
    public void draw_board(List<SnakeSegment> snake_location, Point mouse_location) {
        // Clear avatar grid
        avatar_map.clearAll();
        avatar_map.resetRotations();

        avatar_map.draw(mouse_location, sprites.getMouse(1));
        old_mouse_location = new Point(mouse_location);

        for (SnakeSegment s : snake_location) {
            draw_snake_segment(avatar_map, s);
            if (s.is_head()) {
                old_snake_head = new SnakeSegment(s);
            }
        }
    }

    /**
     * Method updates game window by only redrawing as needed
     *
     * @param   snake: position of the snake
     * @param   old_snake_tail: old position of the snake's tail
     * @param   mouse_location: location of the mouse
     * @author  Andreas Goll Rossau
     */
    public void update_board(List<SnakeSegment> snake, Point old_snake_tail, Point mouse_location) {
        if (!snake.get(0).get_coordinates().equals(old_snake_head.get_coordinates())) {
            SnakeSegment snake_head = snake.get(0);
            SnakeSegment snake_tail = snake.get(snake.size() - 1);

            // A mouse has been eaten
            if (!mouse_location.equals(old_mouse_location)) {
                avatar_map.draw(mouse_location, sprites.getMouse(0));
                avatar_map.clear(old_mouse_location);
                old_mouse_location.setLocation(mouse_location);
            }
            // No mouse has been eaten
            else {
                avatar_map.clear(old_snake_tail);
                avatar_map.getCanvas(old_snake_tail).setRotate(0);
                avatar_map.clear(snake_tail.get_coordinates());
                draw_snake_segment(avatar_map, snake.get(snake.size() - 1));
            }

            avatar_map.clear(old_snake_head.get_coordinates());
            draw_snake_segment(avatar_map, snake.get(1));
            draw_snake_segment(avatar_map, snake_head);
            old_snake_head = new SnakeSegment(snake_head);
        }
    }

    /**
     * Method displays game Over and game Won
     *
     * @param   status: denotes either "Game Over" or "Game Won"
     * @author  Thea Birk Berger
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
        this.avatars.add(endgame_background, 0, 0, grid_x, grid_y);
        this.avatars.add(this.endgame_text, 0,0, grid_x, grid_y);
    }

    /**
     * Method clears the endgame state by removing the text and background
     *
     * @author  Nicolai Verbaarschot
     */
    public void clear_endgame () {
        avatars.getChildren().remove(endgame_text);
        avatars.getChildren().remove(endgame_background);
    }


    /**
     * Method updates the text of the score bar to match the current game state
     *
     * @param   score: denotes how many mice have been eaten
     * @author  Thea Birk Berger
     */
    public void set_score_bar(int score) {
        // Set score bar text
        score_bar.setText("SCORE" + "   " + score);
        // Set score bar font
        score_bar.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        score_bar.setFill(Color.PINK);
        score_bar.setStroke(Color.BLACK);
    }

    private void draw_snake_segment(DisplayMap map, SnakeSegment segment) {
        Image img;
        Point coords;
        if (segment.is_head()) {
            img = sprites.getSnakeHead(1);
            coords = segment.get_previous_coordinates();
            draw_non_bent_segment(map, img, coords, segment);
        }
        else if (segment.is_tail()) {
            img = sprites.getSnakeTail(1);
            coords = segment.get_next_coordinates();
            draw_non_bent_segment(map, img, coords, segment);
        }
        else {
            int x_sum = (int) (Math.abs(segment.get_next_coordinates().getX()) + Math.abs(segment.get_previous_coordinates().getX()));
            int y_sum = (int) (Math.abs(segment.get_next_coordinates().getY()) + Math.abs(segment.get_previous_coordinates().getY()));
            if (x_sum == 0 || y_sum == 0) {
                img = sprites.getStraightSnakeBody(1);
                coords = new Point(x_sum, y_sum);
                draw_non_bent_segment(map, img, coords, segment);
            }
            else {
                draw_bent_snake_segment(map, segment);
            }
        }
    }

    private void draw_non_bent_segment(DisplayMap map, Image img, Point coords, SnakeSegment segment) {
        if (coords.getX() == 0) {
            if (coords.getY() > 0) {
                map.getCanvas(segment.get_coordinates()).setRotate(180);
                map.draw(segment.get_coordinates(), img);
            }
            else {
                map.getCanvas(segment.get_coordinates()).setRotate(0);
                map.draw(segment.get_coordinates(), img);
            }
        }
        else {
            if (coords.getX() > 0) {
                map.getCanvas(segment.get_coordinates()).setRotate(90);
                map.draw(segment.get_coordinates(), img);
            }
            else {
                map.getCanvas(segment.get_coordinates()).setRotate(270);
                map.draw(segment.get_coordinates(), img);
            }
        }
    }

    private void draw_bent_snake_segment(DisplayMap map, SnakeSegment segment) {
        if (segment.get_next_coordinates().getX() < 0 || segment.get_previous_coordinates().getX() < 0) {
            if (segment.get_previous_coordinates().getY() < 0 || segment.get_next_coordinates().getY() < 0) {
                map.getCanvas(segment.get_coordinates()).setRotate(270);
            }
            else {
                map.getCanvas(segment.get_coordinates()).setRotate(180);
            }
        }
        else {
            if (segment.get_previous_coordinates().getY() < 0 || segment.get_next_coordinates().getY() < 0) {
                map.getCanvas(segment.get_coordinates()).setRotate(0);
            }
            else {
                map.getCanvas(segment.get_coordinates()).setRotate(90);
            }
        }
        map.draw(segment.get_coordinates(), sprites.getBentSnakeBody(0));
    }
}
