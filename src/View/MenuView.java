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

public class MenuView {
    private Scene scene;
    private Stage stage;
    private GridPane topGrid;
    private GridPane middleGrid;
    private GridPane backGrid;

    private Canvas[] cursorCanvases = new Canvas[3];
    private DisplayMap avatar_map;
    private DisplayMap background_map;

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

    public MenuView(Stage stage, MenuController menuController) {
        StackPane stack_pane = new StackPane();
        this.scene = new Scene(stack_pane);
        this.backGrid = new GridPane();
        this.topGrid = new GridPane();
        this.middleGrid = new GridPane();
        this.stage = stage;

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

    private void topGridInit() {
        Text title = setTitle("Welcome to Snake");
        topGrid.add(title, 0,0, grid_x, 2);

        Text authors = setAuthors("Authors: Thea Birk Berger, Nicolai Verbaarchot, and Andreas Goll Rossau");
        topGrid.add(authors, 0, 2, grid_x, 1);

        String[] optionTexts = {"Simple Snake", "Fancy Snake", "Quit"};
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

    private void backGridInit() {
        background_map = new DisplayMap(grid_x, grid_y, cell_size);
        background_map.addToGrid(backGrid);
        background_map.drawAll(sprites.getEmptyCell());
    }

    public void reinitialize() {
        stage.setWidth(menuWidth);
        stage.setHeight(menuHeight);
        stage.setScene(scene);
        timer = 0;
    }

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

    private Text setOption(String text) {
        Text option = new Text(text);
        option.setFont(Font.font("Verdana", FontWeight.MEDIUM, 70));
        option.setTextAlignment(TextAlignment.LEFT);
        option.setFill(Color.BLUEVIOLET);
        option.setStroke(Color.BLACK);

        return option;
    }

    private Text setAuthors(String text) {
        Text authors = new Text(text);
        authors.setTextAlignment(TextAlignment.CENTER);
        authors.setWrappingWidth(menuWidth);
        authors.setFont(Font.font("Verdana", FontWeight.LIGHT, 20));
        authors.setFill(Color.BLACK);
        authors.setStroke(Color.BLACK);

        return authors;
    }

    private Text setTitle(String text) {
        Text title = new Text(text);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setWrappingWidth(menuWidth);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        title.setFill(Color.CHOCOLATE);
        title.setStroke(Color.BLACK);

        return title;
    }
}
