package View;

import javafx.scene.image.Image;

public class SpriteHolder {
    private AnimatedImage cursor;
    private AnimatedImage mouse;
    private AnimatedImage snakeHead;
    private AnimatedImage snakeBody;
    private AnimatedImage snakeTail;

    public SpriteHolder() {
        imageInit();
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

    public Image getCursor(long t) {
        return cursor.getFrame(t);
    }

    public Image getMouse(long t) {
        return mouse.getFrame(t);
    }

    public Image getSnakeHead(long t) {
        return snakeHead.getFrame(t);
    }

    public Image getSnakeBody(long t) {
        return snakeBody.getFrame(t);
    }

    public Image getSnakeTail(long t) {
        return snakeTail.getFrame(t);
    }
}
