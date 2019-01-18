package View;

import Model.SnakeSegment;
import javafx.scene.canvas.Canvas;
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
import java.util.HashMap;
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
    private HashMap<Integer, HashMap<Integer, Canvas>> background_map;
    private HashMap<Integer, HashMap<Integer, Canvas>> avatar_map;
    private StackPane stack_pane;
    private Text endgame_text = new Text();
    private Rectangle endgame_background = new Rectangle();
    private GridPane background;
    private GridPane avatars;
    private Text score_bar;
    private Image mouse = new Image("/image/mouse20x20.png");
    private Image blood = new Image( "/image/blood.png");
    private Image head = new Image("/image/snakeHead20x20.png");
    private Image tail = new Image("/image/snakeTail20x20.png");
    private Image straight_snake = new Image("/image/snakeStraight20x20.png");
    private Image bent_snake = new Image("/image/snakeBent20x20.png");
    private Image emptyCell;
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
        this.background_map = new HashMap<>();
        this.avatar_map = new HashMap<>();
        this.stack_pane = stack_pane;
        this.score_bar = new Text();
        this.background = new GridPane();
        this.avatars = new GridPane();
        this.emptyCell = new Image("/image/emptyCell20x20.png");

        stack_pane.getChildren().add(0, background);
        stack_pane.getChildren().add(1, avatars);

        // Initialize score bar and add it to grid_pane
        set_score_bar(0);
        background.add(score_bar, 0, grid_y + 1, grid_x, 1);

        // Set window size
        primary_stage.setWidth(grid_x * cell_size);
        primary_stage.setHeight((grid_y * cell_size) + 65);

        // Add canvas cells to background_map and add background_map to grid_pane
        for (int i = 0; i < grid_x; i++) {
            avatar_map.put(i, new HashMap<>());
            background_map.put(i, new HashMap<>());
            for (int j = 0; j < grid_y; j++) {

                background_map.get(i).put(j, new Canvas(cell_size, cell_size));
                background.add(background_map.get(i).get(j), i, j, 1, 1);
                background_map.get(i).get(j).getGraphicsContext2D().drawImage(emptyCell, 0, 0, cell_size, cell_size);

                avatar_map.get(i).put(j, new Canvas(cell_size, cell_size));
                avatars.add(avatar_map.get(i).get(j), i, j, 1, 1);
            }
        }
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
        for (int i = 0; i < grid_y; i++) {
            for (int j = 0; j < grid_x; j++) {
                background_map.get(i).get(j).getGraphicsContext2D().drawImage(emptyCell, 0, 0, cell_size, cell_size);
                avatar_map.get(j).get(i).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
                avatar_map.get(j).get(i).setRotate(0);
            }
        }

        get_canvas(avatar_map, mouse_location).getGraphicsContext2D().drawImage(mouse, 0, 0, cell_size, cell_size);
        old_mouse_location = new Point(mouse_location);

        for (SnakeSegment s : snake_location) {
            draw_snake_segment(get_canvas(avatar_map, s), s);
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
                get_canvas(avatar_map, mouse_location).getGraphicsContext2D().drawImage(mouse, 0, 0, cell_size, cell_size);
                get_canvas(avatar_map, old_mouse_location).getGraphicsContext2D().clearRect(0, 0 , cell_size, cell_size);
                draw_blood_splatter();
                old_mouse_location.setLocation(mouse_location);
            }
            // No mouse has been eaten
            else {
                get_canvas(avatar_map, old_snake_tail).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
                get_canvas(avatar_map, old_snake_tail).setRotate(0);
                get_canvas(avatar_map, snake_tail).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
                draw_snake_segment(get_canvas(avatar_map, snake_tail), snake.get(snake.size() - 1));
            }

            get_canvas(avatar_map, old_snake_head).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
            draw_snake_segment(get_canvas(avatar_map, snake.get(1)), snake.get(1));
            draw_snake_segment(get_canvas(avatar_map, snake_head), snake_head);
            old_snake_head = new SnakeSegment(snake_head);
        }
    }

    private void draw_blood_splatter() {
        Point right_cell = new Point(collision_check((int) old_mouse_location.getX()+1, grid_x-1), collision_check((int) old_mouse_location.getY(), grid_y-1));
        Point top_right_cell = new Point(collision_check((int) old_mouse_location.getX()+1, grid_x-1), collision_check((int) old_mouse_location.getY()-1, grid_y-1));
        Point top_cell = new Point(collision_check((int) old_mouse_location.getX(), grid_x-1), collision_check((int) old_mouse_location.getY()-1, grid_y-1));
        Point top_left_cell = new Point(collision_check((int) old_mouse_location.getX()-1, grid_x-1), collision_check((int) old_mouse_location.getY()-1, grid_y-1));
        Point left_cell = new Point(collision_check((int) old_mouse_location.getX()-1, grid_x-1), collision_check((int) old_mouse_location.getY(), grid_y-1));
        Point bottom_left_cell = new Point(collision_check((int) old_mouse_location.getX()-1, grid_x-1), collision_check((int) old_mouse_location.getY()+1, grid_y-1));
        Point bottom_cell = new Point(collision_check((int) old_mouse_location.getX(), grid_x-1), collision_check((int) old_mouse_location.getY()+1, grid_y-1));
        Point bottom_right_cell = new Point(collision_check((int) old_mouse_location.getX()+1, grid_x-1), collision_check((int) old_mouse_location.getY()+1, grid_y-1));

        get_canvas(background_map, old_mouse_location).getGraphicsContext2D().drawImage(blood, 0, 0 , cell_size, cell_size);
        get_canvas(background_map, right_cell).getGraphicsContext2D().drawImage(blood, 0, 0 , cell_size, cell_size);
        get_canvas(background_map, top_right_cell).getGraphicsContext2D().drawImage(blood, 0, 0 , cell_size, cell_size);
        get_canvas(background_map, top_cell).getGraphicsContext2D().drawImage(blood, 0, 0 , cell_size, cell_size);
        get_canvas(background_map, top_left_cell).getGraphicsContext2D().drawImage(blood, 0, 0 , cell_size, cell_size);
        get_canvas(background_map, left_cell).getGraphicsContext2D().drawImage(blood, 0, 0 , cell_size, cell_size);
        get_canvas(background_map, bottom_left_cell).getGraphicsContext2D().drawImage(blood, 0, 0 , cell_size, cell_size);
        get_canvas(background_map, bottom_cell).getGraphicsContext2D().drawImage(blood, 0, 0 , cell_size, cell_size);
        get_canvas(background_map, bottom_right_cell).getGraphicsContext2D().drawImage(blood, 0, 0 , cell_size, cell_size);

    }
    private int collision_check(int coordinate, int coordinate_max) {

        if (coordinate == -1) {
            coordinate = coordinate_max;
        } else if (coordinate == coordinate_max + 1) {
            coordinate = 0;
        }
        return coordinate;
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

    private void draw_snake_segment(Canvas canvas, SnakeSegment segment) {
        Image img;
        Point coords;
        if (segment.is_head()) {
            img = head;
            coords = segment.get_previous_coordinates();
            draw_non_bent_segment(canvas, img, coords);
        }
        else if (segment.is_tail()) {
            img = tail;
            coords = segment.get_next_coordinates();
            draw_non_bent_segment(canvas, img, coords);
        }
        else {
            int x_sum = (int) (Math.abs(segment.get_next_coordinates().getX()) + Math.abs(segment.get_previous_coordinates().getX()));
            int y_sum = (int) (Math.abs(segment.get_next_coordinates().getY()) + Math.abs(segment.get_previous_coordinates().getY()));
            if (x_sum == 0 || y_sum == 0) {
                img = straight_snake;
                coords = new Point(x_sum, y_sum);
                draw_non_bent_segment(canvas, img, coords);
            }
            else {
                draw_bent_snake_segment(canvas, segment);
            }
        }
    }

    private void draw_non_bent_segment(Canvas canvas, Image img, Point coords) {
        if (coords.getX() == 0) {
            if (coords.getY() > 0) {
                canvas.setRotate(180);
                canvas.getGraphicsContext2D().drawImage(img, 0, 0, cell_size, cell_size);
            }
            else {
                canvas.setRotate(0);
                canvas.getGraphicsContext2D().drawImage(img, 0, 0, cell_size, cell_size);
            }
        }
        else {
            if (coords.getX() > 0) {
                canvas.setRotate(90);
                canvas.getGraphicsContext2D().drawImage(img, 0, 0, cell_size, cell_size);
            }
            else {
                canvas.setRotate(270);
                canvas.getGraphicsContext2D().drawImage(img, 0, 0, cell_size, cell_size);
            }
        }
    }

    private void draw_bent_snake_segment(Canvas canvas, SnakeSegment segment) {
        if (segment.get_next_coordinates().getX() < 0 || segment.get_previous_coordinates().getX() < 0) {
            if (segment.get_previous_coordinates().getY() < 0 || segment.get_next_coordinates().getY() < 0) {
                canvas.setRotate(270);
                canvas.getGraphicsContext2D().drawImage(bent_snake, 0, 0, cell_size, cell_size);
            }
            else {
                canvas.setRotate(180);
                canvas.getGraphicsContext2D().drawImage(bent_snake, 0, 0, cell_size, cell_size);
            }
        }
        else {
            if (segment.get_previous_coordinates().getY() < 0 || segment.get_next_coordinates().getY() < 0) {
                canvas.setRotate(0);
                canvas.getGraphicsContext2D().drawImage(bent_snake, 0, 0, cell_size, cell_size);
            }
            else {
                canvas.setRotate(90);
                canvas.getGraphicsContext2D().drawImage(bent_snake, 0, 0, cell_size, cell_size);
            }
        }
    }


    private Canvas get_canvas(HashMap<Integer, HashMap<Integer, Canvas>> map, Point p) {
        return map.get((int) p.getX()).get((int) p.getY());
    }

    private Canvas get_canvas(HashMap<Integer, HashMap<Integer, Canvas>> map, SnakeSegment segment) {
        return map.get((int) segment.get_coordinates().getX()).get((int) segment.get_coordinates().getY());
    }
}
