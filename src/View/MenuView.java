package View;

import Controller.MenuController;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;

/**
 * View for main menu
 * @author Andreas Goll Rossau
 */
public class MenuView {
    private Scene scene;
    private Stage stage;
    StackPane stack_pane;
    private GridPane topGrid;
    private GridPane middleGrid;
    private GridPane backGrid;

    private Canvas[] cursorCanvases;
    private DisplayMap avatar_map;

    private Point mousePosition = new Point();
    private Point snakeHeadPosition = new Point();
    private Point snakeTailPosition = new Point();
    private Point snakeBodyPosition = new Point();

    private SpriteHolder sprites = new SpriteHolder("menu");

    private int cell_size = 100;
    private int grid_x = 10;
    private int grid_y = 6;
    private int timer = 0;

    private int menuHeight = grid_y*cell_size;
    private int menuWidth = grid_x*cell_size;

    private int cursorWidth;
    private int cursorHeight;
    private int cursorCanvasWidth;

    /**
     * Constructor prepares display grids and sets key listener
     * @param stage Game window
     * @param menuController Controller for main menu
     * @author Andreas Goll Rossau
     */
    public MenuView(Stage stage, MenuController menuController, int numberOfMenuOptions) {
        stack_pane = new StackPane();
        this.scene = new Scene(stack_pane);
        this.backGrid = new GridPane();
        this.topGrid = new GridPane();
        this.middleGrid = new GridPane();
        this.stage = stage;

        cursorCanvases = new Canvas[numberOfMenuOptions];

        stack_pane.getChildren().add(0, backGrid);
        stack_pane.getChildren().add(1,  middleGrid);
        stack_pane.getChildren().add(2, topGrid);

        stage.setWidth(menuWidth);
        stage.setHeight(menuHeight);
        stage.setScene(scene);
        stage.show();

        backGridInit();
        middleGridInit();
        topGridInit();

        scene.setOnKeyPressed(
                event -> menuController.keyPress(event.getCode().toString())
        );
    }

    /**
     * Initializes middle grid display map and sets and displays initial mouse and snake
     * @author Andreas Goll Rossau
     */
    private void middleGridInit() {
        avatar_map = new DisplayMap(grid_x, grid_y, cell_size);
        avatar_map.addToGrid(middleGrid);

        mousePosition.setLocation(grid_x - 3, 3);
        avatar_map.draw(mousePosition, sprites.getMouse(1));

        snakeHeadPosition.setLocation(grid_x - 1, 3);
        avatar_map.getCanvas(snakeHeadPosition).setRotate(90);
        avatar_map.draw(snakeHeadPosition, sprites.getSnakeHead(1));

        snakeTailPosition.setLocation(1, 3);
        avatar_map.getCanvas(snakeTailPosition).setRotate(270);
        avatar_map.draw(snakeTailPosition, sprites.getSnakeTail(1));

        snakeBodyPosition.setLocation(0, 3);
        avatar_map.getCanvas(snakeBodyPosition).setRotate(90);
        avatar_map.draw(snakeBodyPosition, sprites.getStraightSnakeBody(1));
    }

    /**
     * Initializes top grid, adds menu text and cursor canvasses
     * @author Andreas Goll Rossau
     */
    private void topGridInit() {
        Text title = setTitle("Welcome to Snake");
        topGrid.add(title, 0,0, grid_x, 2);

        Text authors = setAuthors("Authors: Thea Birk Berger, Nicolai Verbaarchot, and Andreas Goll Rossau");
        topGrid.add(authors, 0, 2, grid_x, 1);

        String[] optionTexts = {"Simple Snake", "Fancy Snake", "Instructions", "High Scores", "Quit"};
        int r = 3;
        int i = 0;
        for (String s : optionTexts) {
            Text option = setOption(s);
            topGrid.add(option, 1, r, 1, 1);
            r++;

            cursorHeight = (int) option.getBoundsInLocal().getHeight();
            cursorWidth = cursorHeight;

            cursorCanvasWidth = menuWidth/3 + cursorWidth;
            cursorCanvases[i] = new Canvas(cursorCanvasWidth, cursorHeight);
            topGrid.add(cursorCanvases[i], 0, 3 + i, 1, 1);
            i++;
        }
    }

    /**
     * Initializes back grid display map, draws background images
     * @author Andreas Goll Rossau
     */
    private void backGridInit() {
        DisplayMap background_map = new DisplayMap(grid_x, grid_y, cell_size);
        background_map.addToGrid(backGrid);
        background_map.drawAll(sprites.getEmptyCell());
    }

    /**
     * Reinitializes menu display
     * @author Andreas Goll Rossau
     */
    public void reinitialize() {
        stage.setWidth(menuWidth);
        stage.setHeight(menuHeight);
        stage.setScene(scene);
        stack_pane.getChildren().clear();
        stack_pane.getChildren().add(0, backGrid);
        stack_pane.getChildren().add(1,  middleGrid);
        stack_pane.getChildren().add(2, topGrid);
        timer = 0;
    }

    /**
     * Updates menu cursor, snake and mouse
     * @param selected Current menu selection
     * @param t Animation timer
     * @author Andreas Goll Rossau
     */
    public void updateMenu(int selected, long t) {
        for (Canvas c : cursorCanvases) {
            c.getGraphicsContext2D().clearRect(cursorCanvasWidth - cursorWidth, 0, cursorWidth, cursorHeight);
        }
        cursorCanvases[selected - 1].getGraphicsContext2D().drawImage(sprites.getCursor(t), cursorCanvasWidth - cursorWidth, 0, cursorWidth, cursorHeight);

        avatar_map.getCanvas(mousePosition).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
        avatar_map.clear(snakeHeadPosition);
        avatar_map.clear(snakeBodyPosition);
        avatar_map.clear(snakeTailPosition);

        if (t > timer) {
            avatar_map.getCanvas(snakeTailPosition).setRotate(0);
            mousePosition.translate(-1, 0);
            snakeHeadPosition.translate(-1, 0);
            snakeTailPosition.translate(-1, 0);
            snakeBodyPosition.translate(-1, 0);
            if (mousePosition.getX() < 0) {
                mousePosition.x = grid_x - 1;
            }
            else if (snakeHeadPosition.getX() < 0) {
                snakeHeadPosition.x = grid_x - 1;
            }
            else if (snakeTailPosition.getX() < 0) {
                snakeTailPosition.x = grid_x - 1;
            }
            else if (snakeBodyPosition.getX() < 0) {
                snakeBodyPosition.x = grid_x - 1;
            }
            timer += 10;
        }
        avatar_map.draw(mousePosition, sprites.getMouse(t));
        avatar_map.getCanvas(snakeHeadPosition).setRotate(90);
        avatar_map.draw(snakeHeadPosition, sprites.getSnakeHead(t));
        avatar_map.draw(snakeBodyPosition, sprites.getStraightSnakeBody(t));
        avatar_map.getCanvas(snakeTailPosition).setRotate(270);
        avatar_map.draw(snakeTailPosition, sprites.getSnakeTail(t));
    }

    /**
     * Sets parameters for menu option texts
     * @param text Text for menu option
     * @return Text object for menu option
     * @author Andreas Goll Rossau
     */
    private Text setOption(String text) {
        Text option = new Text(text);
        option.setFont(Font.font("Verdana", FontWeight.MEDIUM, 70));
        option.setTextAlignment(TextAlignment.LEFT);
        option.setFill(Color.BLUEVIOLET);
        option.setStroke(Color.BLACK);

        return option;
    }

    /**
     * Method sets parameters for author text
     * @param text Text for author
     * @return Text object for authors
     * @author Andreas Goll Rossau
     */
    private Text setAuthors(String text) {
        Text authors = new Text(text);
        authors.setTextAlignment(TextAlignment.CENTER);
        authors.setWrappingWidth(menuWidth);
        authors.setFont(Font.font("Verdana", FontWeight.LIGHT, 20));
        authors.setFill(Color.GREENYELLOW);
        authors.setStroke(Color.GREENYELLOW);

        return authors;
    }

    /**
     * Method sets paramenters for title text
     * @param text Text for title
     * @return Text object for title
     * @author Andreas Goll Rossau
     */
    private Text setTitle(String text) {
        Text title = new Text(text);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setWrappingWidth(menuWidth);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        title.setFill(Color.CHOCOLATE);
        title.setStroke(Color.BLACK);

        return title;
    }

    public void showInstructions(StackPane instructionPane) {
        GridPane textPane = new GridPane();
        instructionPane.getChildren().clear();
        instructionPane.getChildren().add(0, backGrid);
        instructionPane.getChildren().add(1, middleGrid);
        instructionPane.getChildren().add(2, textPane);

        Text title = setTitle("Instructions");
        Text instructions = setAuthors("Welcome to Snake!\n " +
                                        "This game was made in January of 2019 as part of an assignment for the course Introduction to Software Technology at the Technical University of Denmark (DTU).\n \n " +
                                        "In order to play, you must first select one of the two games in the main menu. \n \n " +
                                        "SIMPLE SNAKE:\n " +
                                        "In this game, the snake will only move when you tell it to by pressing the arrow keys. The objective of the game is to eat mice. Once you eat a mouse, your snake will grow.\n" +
                                        "You must also take care not to die by moving into yourself. You can, however, move into the tail of the snake, as it will move at the same time.\n " +
                                        "You can restart the game at any time by pressing the 'r' key, or quit by pressing 'escape'.\n \n" +
                                        "FANCY SNAKE: \n" +
                                        "This game is much the same as Simple Snake, except it has fancier graphics, and the snake keeps moving in the direction you gave it last.\n \n" +
                                        "We hope you enjoy it,\n" +
                                        "-Thea Birk Berger, Nicolai Verbaarchot, and Andreas Goll Rossau");
        textPane.add(title, 0, 0);
        textPane.add(instructions, 0, 2);
    }

    public void showHighScores(StackPane stack_pane) {
        GridPane highScorePane = new GridPane();
        stack_pane.getChildren().clear();
        stack_pane.getChildren().add(0, backGrid);
        stack_pane.getChildren().add(1, middleGrid);
        stack_pane.getChildren().add(2, highScorePane);

        Text title = setTitle("High Scores");
        Text placeholder = setAuthors("1: Mista Snaaku - 100 \n 2: Earth Worm Jim - 90 \n 3: Snakey McSnakeFace - 80 \n 4: Snakemaster Flash - 70 \n 5: Such Snake, Wow! - 60 \n 6: Twedledee the Wonderdummy - 50 \n 7: Sssssssmith - 40 \n 8: Solid Snake - 30 \n 9: Liquid Snake - 20 \n 10: Naked Snake - 10 \n \n Aim for the top Mr. Snake!");
        highScorePane.add(title, 0, 0);
        highScorePane.add(placeholder, 0, 2);
    }
}
