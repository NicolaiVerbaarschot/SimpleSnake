package View;

import javafx.scene.image.Image;

public class SpriteHolder {
    private AnimatedImage cursor;
    private AnimatedImage mouse;
    private AnimatedImage snakeHead;
    private AnimatedImage straightSnakeBody;
    private AnimatedImage snakeTail;
    private AnimatedImage bentSnakeBody;
    private Image emptyCell;

    public SpriteHolder(String type) {
        switch (type) {
            case "menu":
                menuInit();
                break;
            case "fancy":
                fancyInit();
                break;
        }
    }

    private void fancyInit() {
        Image[] mouseFrames = new Image[]{new Image("/image/mouse20x20.png")};
        mouse = new AnimatedImage(mouseFrames, 90);

        Image[] snakeHeadFrames = new Image[]{new Image("/image/snakeHead20x20.png")};
        snakeHead = new AnimatedImage(snakeHeadFrames, 90);

        Image[] straightSnakeBodyFrames = new Image[]{new Image("/image/snakeStraight20x20.png")};
        straightSnakeBody = new AnimatedImage(straightSnakeBodyFrames, 90);

        Image[] bentSnakeBodyFrames = new Image[]{new Image("/image/snakeBent20x20.png")};
        bentSnakeBody = new AnimatedImage(bentSnakeBodyFrames, 90);

        Image[] snakeTailFrames = new Image[]{new Image("/image/snakeTail20x20.png")};
        snakeTail = new AnimatedImage(snakeTailFrames, 90);

        emptyCell = new Image("/image/emptyCell20x20.png");
    }


    private void menuInit() {
        Image[] cursorFrames = new Image[18];
        for (int i = 0; i < 18; i++) {
            cursorFrames[i] = new Image("/image/cursor/cursor" + i + ".png");
        }
        cursor = new AnimatedImage(cursorFrames, 1);

        Image[] mouseFrames = new Image[]{new Image("/image/mouse2.png"), new Image("/image/mouse20x20.png")};
        mouse = new AnimatedImage(mouseFrames, 90);

        Image[] snakeHeadFrames = new Image[]{new Image("/image/head2.png"), new Image("/image/snakeHead20x20.png")};
        snakeHead = new AnimatedImage(snakeHeadFrames, 90);

        Image[] straightSnakeBodyFrames = new Image[]{new Image("/image/snake2.png"), new Image("/image/snakeStraight20x20.png")};
        straightSnakeBody = new AnimatedImage(straightSnakeBodyFrames, 90);

        Image[] snakeTailFrames = new Image[]{new Image("/image/snake2.png"), new Image("/image/snakeTail20x20.png")};
        snakeTail = new AnimatedImage(snakeTailFrames, 90);

        emptyCell = new Image("/image/emptyCell20x20.png");
    }

    public Image getCursor(long t) {
        return cursor.getFrame(t);
    }

    public Image getMouse(long t) {
        return mouse.getFrame(t);
    }

    public Image getSnakeHead(long t) {
        return snakeHead.getFrame(t);
    }

    public Image getStraightSnakeBody(long t) {
        return straightSnakeBody.getFrame(t);
    }

    public Image getSnakeTail(long t) {
        return snakeTail.getFrame(t);
    }

    public Image getEmptyCell() { return emptyCell;    }

    public Image getBentSnakeBody(long t) { return bentSnakeBody.getFrame(t); }
}
