package View;

import Controller.MenuController;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.util.HashMap;

public class MenuView {
    private Scene scene;
    private Stage stage;
    private GridPane topGrid;
    private GridPane middleGrid;
    private GridPane backGrid;
    private int cell_size = 100;
    private int grid_x = 10;
    private int grid_y = 6;
    private int menuHeight = grid_y*cell_size;
    private int menuWidth = grid_x*cell_size;
    private Point mousePosition = new Point();
    private AnimatedImage cursor;
    private AnimatedImage mouse;
    private Canvas[] cursorCanvases = new Canvas[3];
    private int cursorWidth;
    private int cursorHeight;
    private int canvasWidth;
    private int timer = 0;
    private HashMap<Integer, HashMap<Integer, Canvas>> avatar_map = new HashMap<>();
    private AnimatedImage snakeHead;
    private AnimatedImage snakeBody;
    private AnimatedImage snakeTail;
    private Point snakeHeadPosition = new Point();
    private Point snakeTailPosition = new Point();
    private Point snakeBodyPosition = new Point();

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

        imageInit();

        backGridInit();
        middleGridInit();
        topGridInit();

        scene.setOnKeyPressed(
                event -> menuController.keyPress(event.getCode().toString())
        );
    }

    private void middleGridInit() {
        for (int i = 0; i < grid_x; i++) {
            avatar_map.put(i, new HashMap<>());
            for (int j = 0; j < grid_y; j++) {
                avatar_map.get(i).put(j, new Canvas(cell_size, cell_size));
                middleGrid.add(avatar_map.get(i).get(j), i, j, 1, 1);
            }
        }
        mousePosition.setLocation(grid_x - 3, 3);
        avatar_map.get(grid_x - 3).get(3).getGraphicsContext2D().drawImage(mouse.getFrame(1), 0, 0, cell_size, cell_size);

        snakeHeadPosition.setLocation(grid_x - 1, 3);
        avatar_map.get(grid_x - 1).get(3).setRotate(90);
        avatar_map.get(grid_x - 1).get(3).getGraphicsContext2D().drawImage(snakeHead.getFrame(1), 0, 0, cell_size, cell_size);

        snakeTailPosition.setLocation(1, 3);
        avatar_map.get(1).get(3).setRotate(270);
        avatar_map.get(1).get(3).getGraphicsContext2D().drawImage(snakeTail.getFrame(1), 0, 0, cell_size, cell_size);

        snakeBodyPosition.setLocation(0, 3);
        avatar_map.get(0).get(3).setRotate(90);
        avatar_map.get(0).get(3).getGraphicsContext2D().drawImage(snakeBody.getFrame(1), 0,0, cell_size, cell_size);
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

            canvasWidth = menuWidth/3 + cursorWidth;
            cursorCanvases[i] = new Canvas(canvasWidth, cursorHeight);
            topGrid.add(cursorCanvases[i], 0, 3 + i, 1, 1);
            i++;
        }
    }

    private void backGridInit() {
        for (int i = 0; i < grid_x; i++) {
            for (int j = 0; j < grid_y; j++) {
                Canvas canvas = new Canvas(cell_size,cell_size);
                canvas.getGraphicsContext2D().drawImage(new Image("/image/emptyCell20x20.png"), 0, 0, cell_size, cell_size);
                backGrid.add(canvas, i, j, 1, 1);
            }
        }
    }

    private void imageInit() {
        Image[] cursorFrames = new Image[18];
        for (int i = 0; i < 18; i++) {
            cursorFrames[i] = new Image("/image/cursor/cursor" + i + ".png");
        }
        cursor = new AnimatedImage(cursorFrames, 1);

        Image[] mouseFrames = new Image[]{new Image("/image/mouse2.png"), new Image("/image/mouse20x20.png")};
        mouse = new AnimatedImage(mouseFrames, 90);

        Image[] snakeHeadFrames = new Image[]{new Image("/image/head2.png"), new Image("/image/snakeHead20x20.png")};
        snakeHead = new AnimatedImage(snakeHeadFrames, 90);

        Image[] snakeBodyFrames = new Image[]{new Image("/image/snake2.png"), new Image("/image/snakeStraight20x20.png")};
        snakeBody = new AnimatedImage(snakeBodyFrames, 90);

        Image[] snakeTailFrames = new Image[]{new Image("/image/snake2.png"), new Image("/image/snakeTail20x20.png")};
        snakeTail = new AnimatedImage(snakeTailFrames, 90);
    }

    public void reinitialize() {
        stage.setWidth(menuWidth);
        stage.setHeight(menuHeight);
        stage.setScene(scene);
        timer = 0;
    }

    public void updateMenu(int selected, long t) {
        for (Canvas c : cursorCanvases) {
            c.getGraphicsContext2D().clearRect(canvasWidth - cursorWidth, 0, cursorWidth, cursorHeight);
        }
        cursorCanvases[selected - 1].getGraphicsContext2D().drawImage(cursor.getFrame(t), canvasWidth - cursorWidth, 0, cursorWidth, cursorHeight);

        avatar_map.get((int) mousePosition.getX()).get((int) mousePosition.getY()).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
        avatar_map.get((int) snakeHeadPosition.getX()).get((int) snakeHeadPosition.getY()).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
        avatar_map.get((int) snakeTailPosition.getX()).get((int) snakeTailPosition.getY()).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
        avatar_map.get((int) snakeBodyPosition.getX()).get((int) snakeBodyPosition.getY()).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);

        if (t > timer) {
            avatar_map.get((int) snakeTailPosition.getX()).get((int) snakeTailPosition.getY()).setRotate(0);
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
        avatar_map.get((int) mousePosition.getX()).get((int) mousePosition.getY()).getGraphicsContext2D().drawImage(mouse.getFrame(t), 0, 0, cell_size, cell_size);
        avatar_map.get((int) snakeHeadPosition.getX()).get((int) snakeHeadPosition.getY()).setRotate(90);
        avatar_map.get((int) snakeHeadPosition.getX()).get((int) snakeHeadPosition.getY()).getGraphicsContext2D().drawImage(snakeHead.getFrame(t), 0, 0 , cell_size, cell_size);
        avatar_map.get((int) snakeBodyPosition.getX()).get((int) snakeBodyPosition.getY()).getGraphicsContext2D().drawImage(snakeBody.getFrame(t), 0, 0, cell_size, cell_size);
        avatar_map.get((int) snakeTailPosition.getX()).get((int) snakeTailPosition.getY()).setRotate(270);
        avatar_map.get((int) snakeTailPosition.getX()).get((int) snakeTailPosition.getY()).getGraphicsContext2D().drawImage(snakeTail.getFrame(t), 0, 0, cell_size, cell_size);
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
