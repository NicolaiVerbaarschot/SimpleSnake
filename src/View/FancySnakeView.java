package View;

import Model.SnakeSegment;
import javafx.animation.AnimationTimer;
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
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private DisplayMap middle_map;

    private GridPane avatars;
    private GridPane gameOverFrontGrid;
    AnimationTimer timer;
    Text playerName;

    private Text endgame_text = new Text();
    private Text score_bar;
    private Rectangle endgame_background = new Rectangle();
    private String name = "";

    private SpriteHolder sprites;
    private Image[] blood = {
            new Image( "/image/blood_01/blood_01.png"),
            new Image( "/image/blood_01/blood_02.png"),
            new Image( "/image/blood_01/blood_03.png"),
            new Image( "/image/blood_01/blood_04.png"),
            new Image( "/image/blood_01/blood_05.png"),
            new Image( "/image/blood_01/blood_06.png"),
            new Image( "/image/blood_01/blood_07.png"),
            new Image( "/image/blood_01/blood_08.png"),
            new Image( "/image/blood_01/blood_09.png")};
    private Point old_mouse_location;
    private SnakeSegment old_snake_tail;

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
        this.middle_map = new DisplayMap(grid_x, grid_y, cell_size);
        this.avatar_map = new DisplayMap(grid_x, grid_y, cell_size);

        GridPane background = new GridPane();
        GridPane middle = new GridPane();
        this.avatars = new GridPane();

        this.score_bar = new Text();

        this.sprites = new SpriteHolder("fancy");

        stack_pane.getChildren().add(0, background);
        stack_pane.getChildren().add(1, middle);
        stack_pane.getChildren().add(2, avatars);

        // Initialize score bar and add it to grid_pane
        set_score_bar(0);
        background.add(score_bar, 0, grid_y + 1, grid_x, 1);

        // Set window size
        primary_stage.setWidth(grid_x * cell_size);
        primary_stage.setHeight((grid_y * cell_size) + 65);

        // Add canvas cells to background_map and add background_map to grid_pane
        background_map.addToGrid(background);
        background_map.drawAll(sprites.getEmptyCell());
        middle_map.addToGrid(middle);
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
            draw_snake_segment(avatar_map, s, 1);
        }
        old_snake_tail = new SnakeSegment(snake_location.get(snake_location.size() - 1));
    }

    /**
     * Method updates game window by only redrawing as needed
     *
     * @param   snake: position of the snake
     * @param   mouse_location: location of the mouse
     * @author  Andreas Goll Rossau
     */
    public void update_board(List<SnakeSegment> snake, Point mouse_location, long t) {
        avatar_map.clear(old_mouse_location);
        avatar_map.draw(mouse_location, sprites.getMouse(t));

        // A mouse has been eaten
        if (!mouse_location.equals(old_mouse_location)) {
            draw_blood_splatter();
            old_mouse_location.setLocation(mouse_location);
        }

        if (!this.old_snake_tail.get_coordinates().equals(snake.get(snake.size() -1 ).get_coordinates())) {
            avatar_map.clear(this.old_snake_tail.get_coordinates());
            avatar_map.getCanvas(this.old_snake_tail.get_coordinates()).setRotate(0);
            this.old_snake_tail = snake.get(snake.size() - 1);
        }

        for (SnakeSegment s : snake) {
            avatar_map.clear(s.get_coordinates());
            draw_snake_segment(avatar_map, s, t);
        }
    }

    /**
     * This method draws blood splatter in the location of an eaten mouse
     *
     * @author Nicolai verbaarschot
     */
    private void draw_blood_splatter() {

        ArrayList<FadeTransition> fades = new ArrayList<>();

        for (int i=0; i<9; i++) {
            fades.add(new FadeTransition());
        }

        for (FadeTransition fade : fades) {
            fade.setDuration(Duration.millis(5000));
            fade.setFromValue(10);
            fade.setToValue(0);
            fade.setCycleCount(1);
            fade.setAutoReverse(false);
        }


        int blood_segment = 0;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Point p = new Point(old_mouse_location);
                p.translate(i, j);
                collision_check(p, grid_x - 1, grid_y - 1);
                middle_map.draw(p, blood[blood_segment]);

                fades.get(blood_segment).setNode(middle_map.getCanvas(p));
                blood_segment++;
            }
        }
        for (FadeTransition fade : fades) {
            fade.play();
        }
        ExecutorService executor = Executors.newFixedThreadPool(1);
        Runnable worker = new WorkerThread(middle_map, old_mouse_location, grid_x, grid_y);
        executor.execute(worker);
        executor.shutdown();

    }

    /**
     * This method checks and corrects a point if it lies outside the game border
     *
     * @param p Point to check
     * @param x_max size of the grid in the x dimension
     * @param y_max size of the grid in he y dimension
     * @author Andreas Goll Rossau
     */
    private void collision_check(Point p, int x_max, int y_max) {
        if (p.getX() == -1) {
            p.x = x_max;
        } else if (p.getX() == x_max + 1) {
            p.x = 0;
        }
        if (p.getY() == -1) {
            p.y = y_max;
        }
        else if (p.getY() == y_max + 1) {
            p.y = 0;
        }
    }

    /**
     * Method clears the endgame state by removing the text and background
     *
     * @author  Nicolai Verbaarschot
     */
    public void clear_board() {
        middle_map.clearAll();
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

    /**
     * Method finds correct way to draw snake segment
     * @param map Map of canvasses to draw on
     * @param segment Snake segment to draw
     * @author Andreas Goll Rossau
     */
    private void draw_snake_segment(DisplayMap map, SnakeSegment segment, long t) {
        Image img;
        Point coords;
        if (segment.is_head()) {
            img = sprites.getSnakeHead(t);
            coords = segment.get_previous_coordinates();
            draw_non_bent_segment(map, img, coords, segment);
        }
        else if (segment.is_tail()) {
            img = sprites.getSnakeTail(t);
            coords = segment.get_next_coordinates();
            draw_non_bent_segment(map, img, coords, segment);
        }
        else {
            int x_sum = (int) (Math.abs(segment.get_next_coordinates().getX()) + Math.abs(segment.get_previous_coordinates().getX()));
            int y_sum = (int) (Math.abs(segment.get_next_coordinates().getY()) + Math.abs(segment.get_previous_coordinates().getY()));
            if (x_sum == 0 || y_sum == 0) {
                img = sprites.getStraightSnakeBody(t);
                coords = new Point(x_sum, y_sum);
                draw_non_bent_segment(map, img, coords, segment);
            }
            else {
                img = sprites.getBentSnakeBody(t);
                draw_bent_snake_segment(map, img, segment);
            }
        }
    }

    /**
     * Method draws non bent segments of snake
     * @param map Map of canvasses to draw on
     * @param img Image to draw
     * @param coords Coordinates which determine drawing method
     * @param segment Segment to draw
     * @author Andreas Goll Rossau
     */
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

    /**
     * Method draws bent segments of snake
     * @param map Map of canvasses to draw on
     * @param segment Segment to draw
     * @author Andreas Goll Rossau
     */
    private void draw_bent_snake_segment(DisplayMap map, Image img, SnakeSegment segment) {
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
        map.draw(segment.get_coordinates(), img);
    }

    public void gameOverScreen(boolean highScore, String game_status, int score, StackPane stackPane) {
        GridPane gameOverBackGrid = new GridPane();
        gameOverFrontGrid = new GridPane();

        stackPane.getChildren().add(0, gameOverBackGrid);
        stackPane.getChildren().add(1, gameOverFrontGrid);

        setGameOverBackGround(gameOverBackGrid);

        String title;
        if (game_status.equals("Game Won")) {
            title = "CONGRATULATIONS YOU WON!";
        }
        else {
            title = "GAME OVER!";
        }
        Text titleText = new Text(title);
        titleText.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        titleText.setFill(Color.DEEPPINK);
        titleText.setStroke(Color.BLACK);
        titleText.setTextAlignment(TextAlignment.CENTER);
        titleText.setWrappingWidth(grid_x * cell_size);

        Text bottomMessage = new Text(title);
        bottomMessage.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        bottomMessage.setFill(Color.DEEPPINK);
        bottomMessage.setStroke(Color.BLACK);
        bottomMessage.setTextAlignment(TextAlignment.CENTER);
        bottomMessage.setWrappingWidth(grid_x * cell_size);

        Text score_message_text = new Text();
        if (highScore) {
            score_message_text.setText("YOU ARE ON THE LEADER BOARD");
            bottomMessage.setText("Please enter your name:");
        } else {
            score_message_text.setText("YOU ATE " + score + " MICE");
            bottomMessage.setText("Better luck next time");
        }
        score_message_text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        score_message_text.setFill(Color.LIGHTGRAY);
        score_message_text.setStroke(Color.BLACK);
        score_message_text.setTextAlignment(TextAlignment.CENTER);
        score_message_text.setWrappingWidth(grid_x * cell_size);

        gameOverFrontGrid.add(titleText, 0, 0, grid_x, 1);
        gameOverFrontGrid.add(score_message_text, 0, 1, grid_x, 1 );

        String highScores = "";
        try {
            highScores = get_high_score_list();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        Text highScoreList = new Text(highScores);
        highScoreList.setTextAlignment(TextAlignment.CENTER);
        highScoreList.setWrappingWidth(grid_x*cell_size);
        highScoreList.setFont(Font.font("Verdana", FontWeight.LIGHT, 20));
        highScoreList.setFill(Color.GREENYELLOW);
        highScoreList.setStroke(Color.GREENYELLOW);
        gameOverFrontGrid.add(highScoreList, 0, 2, grid_x, 1);

        gameOverFrontGrid.add(bottomMessage, 0, 3, grid_x, 1);
    }

    public void updatePlayerName() {
        gameOverFrontGrid.getChildren().remove(playerName);

        playerName = new Text(name);
        playerName.setTextAlignment(TextAlignment.CENTER);
        playerName.setWrappingWidth(grid_x*cell_size);
        playerName.setFont(Font.font("Verdana", FontWeight.LIGHT, 20));
        playerName.setFill(Color.GREENYELLOW);
        playerName.setStroke(Color.GREENYELLOW);
        gameOverFrontGrid.add(playerName, 0, 4, grid_x, 1);
    }

    private String get_high_score_list() throws IOException {
        File file = new File("res/highscores.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));

        String tempScores = "";
        String str;
        int leader_board_position = 1;
        while ((str = br.readLine()) != null) {
            tempScores += leader_board_position + ":  " + str + "\n";
            leader_board_position++;
        }
        br.close();

        return tempScores;
    }

    public void setGameOverBackGround(GridPane gameOverBackGrid) {

        // Initialize rectangle parameters
        endgame_background.setHeight((int) grid_y * cell_size + 65);
        endgame_background.setWidth((int) grid_x * cell_size);
        endgame_background.setFill(Color.CADETBLUE);

        // Add endgame_background to grid_pane
        gameOverBackGrid.add(endgame_background, 0, 0, grid_x, grid_y);
    }

    public void addToName(String input) {
        name += input;
    }

    public void removeFromName() {
        if (name.length() != 0) {
            name = name.substring(0, name.length() - 1);
        }
    }

    public String getName() {
        return name;
    }

    public void resetName() {
        name = "";
    }
}
