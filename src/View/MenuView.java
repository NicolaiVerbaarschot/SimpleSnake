package View;

import Controller.MenuController;
import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MenuView {
    private Scene scene;
    private Stage stage;
    private GridPane gridPane;
    private int menuHeight = 400;
    private int menuWidth = 800;
    private AnimatedImage cursor;

    public MenuView(Stage stage, MenuController menuController) {
        this.gridPane = new GridPane();
        scene = new Scene(gridPane);
        this.stage = stage;
        stage.setWidth(menuWidth);
        stage.setHeight(menuHeight);
        stage.setScene(scene);
        stage.show();

        imageInit();

        scene.setOnKeyPressed(
                event -> menuController.keyPress(event.getCode().toString())
        );
    }

    private void imageInit() {
        Image[] cursorFrames = new Image[]{new Image("/image/mouse2.png"), new Image("/image/head2.png")};
        cursor = new AnimatedImage(cursorFrames, 1);
    }

    public void reinitialize() {
        stage.setWidth(menuWidth);
        stage.setHeight(menuHeight);
        stage.setScene(scene);
        drawMenu(1);
    }

    public void drawMenu(int selected) {
        gridPane.getChildren().clear();

        Rectangle menuBackground = new Rectangle(menuWidth, menuHeight, Color.CADETBLUE);
        gridPane.add(menuBackground, 0, 0, 100, 100);

        Text title = setTitle("Welcome to Snake");
        gridPane.add(title, 0,0, 2, 2);

        Text authors = setAuthors("Authors: Thea Birk Berger, Nicolai Verbaarchot, and Andreas Goll Rossau");
        gridPane.add(authors, 0, 2, 2, 1);

        int cursorWidth = 0;
        int cursorHeight = 0;

        String[] optionTexts = {"Simple Snake", "Fancy Snake", "Quit"};
        int r = 3;
        for (String s : optionTexts) {
            Text option = setOption(s);
            gridPane.add(option, 1, r, 1, 1);
            r++;

            cursorHeight = (int) option.getBoundsInLocal().getHeight();
            cursorWidth = cursorHeight;
        }

        int canvasWidth = menuWidth/3 + cursorWidth;
        Canvas cursorCanvas = new Canvas(canvasWidth, cursorHeight);
        gridPane.add(cursorCanvas, 0, 2 + selected, 1, 1);

        int finalCursorWidth = cursorWidth;
        int finalCursorHeight = cursorHeight;
        new AnimationTimer() {
            @Override
            public void handle(long nanoTime) {
                long t = nanoTime / 1000000000;
                cursorCanvas.getGraphicsContext2D().clearRect(canvasWidth - finalCursorWidth, 0, finalCursorWidth, finalCursorHeight);
                cursorCanvas.getGraphicsContext2D().drawImage(cursor.getFrame(t), canvasWidth - finalCursorWidth, 0, finalCursorWidth, finalCursorHeight);
            }
        }.start();
    }

    private Text setOption(String text) {
        Text option = new Text(text);
        option.setFont(Font.font("Verdana", FontWeight.MEDIUM, 40));
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
