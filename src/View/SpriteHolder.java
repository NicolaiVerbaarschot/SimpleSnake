package View;

import javafx.scene.image.Image;

/**
 * Library of animated sprites for Snake game
 * @author Andreas Goll Rossau
 */
class SpriteHolder {
    private AnimatedImage cursor;
    private AnimatedImage mouse;
    private AnimatedImage snakeHead;
    private AnimatedImage straightSnakeBody;
    private AnimatedImage snakeTail;
    private AnimatedImage bentSnakeBody;
    private Image emptyCell;

    /**
     * Constructor initializes sprite library based on type
     * @param type Type of SpriteHolder
     * @author Andreas Goll Rossau
     */
    SpriteHolder(String type) {
        switch (type) {
            case "menu":
                menuInit();
                break;
            case "fancy":
                fancyInit();
                break;
        }
    }

    /**
     * Initializes sprite library for use in Fancy Snake game
     * @author Andreas Goll Rossau
     */
    private void fancyInit() {
        Image[] mouseFrames = new Image[]{
                new Image("/image/mouse_01.png"),
                new Image("/image/mouse_02.png")};
        mouse = new AnimatedImage(mouseFrames, 5);

        Image[] snakeHeadFrames = new Image[]{
                new Image("/image/snakeHead_01.png"),
                new Image("/image/snakeHead_02.png"),
                new Image("/image/snakeHead_03.png"),
                new Image("/image/snakeHead_02.png")};
        snakeHead = new AnimatedImage(snakeHeadFrames, 3);

        Image[] straightSnakeBodyFrames = new Image[]{
                new Image("/image/snakeStraight_01.png"),
                new Image("/image/snakeStraight_02.png"),
                new Image("/image/snakeStraight_03.png"),
                new Image("/image/snakeStraight_04.png"),
                new Image("/image/snakeStraight_05.png"),
                new Image("/image/snakeStraight_06.png"),
                new Image("/image/snakeStraight_05.png"),
                new Image("/image/snakeStraight_04.png"),
                new Image("/image/snakeStraight_03.png"),
                new Image("/image/snakeStraight_02.png")};
        straightSnakeBody = new AnimatedImage(straightSnakeBodyFrames, 3);

        Image[] bentSnakeBodyFrames = new Image[]{
                new Image("/image/snakeBent_01.png")};
        bentSnakeBody = new AnimatedImage(bentSnakeBodyFrames, 90);

        Image[] snakeTailFrames = new Image[]{
                new Image("/image/snakeTail_01.png")};
        snakeTail = new AnimatedImage(snakeTailFrames, 90);

        emptyCell = new Image("/image/emptyCell_00.png");
    }

    /**
     * Initializes sprite library for use in main menu
     * @author Andreas Goll Rossau
     */
    private void menuInit() {
        Image[] cursorFrames = new Image[18];
        for (int i = 0; i < 18; i++) {
            cursorFrames[i] = new Image("/image/cursor/cursor" + i + ".png");
        }
        cursor = new AnimatedImage(cursorFrames, 1);

        Image[] mouseFrames = new Image[]{
                new Image("/image/mouse_01.png"),
                new Image("/image/mouse_02.png")};
        mouse = new AnimatedImage(mouseFrames, 5);

        Image[] snakeHeadFrames = new Image[]{
                new Image("/image/snakeHead_01.png"),
                new Image("/image/snakeHead_02.png"),
                new Image("/image/snakeHead_03.png"),
                new Image("/image/snakeHead_02.png")};
        snakeHead = new AnimatedImage(snakeHeadFrames, 3);

        Image[] straightSnakeBodyFrames = new Image[]{
                new Image("/image/snakeStraight_01.png"),
                new Image("/image/snakeStraight_02.png"),
                new Image("/image/snakeStraight_03.png"),
                new Image("/image/snakeStraight_04.png"),
                new Image("/image/snakeStraight_05.png"),
                new Image("/image/snakeStraight_06.png"),
                new Image("/image/snakeStraight_05.png"),
                new Image("/image/snakeStraight_04.png"),
                new Image("/image/snakeStraight_03.png"),
                new Image("/image/snakeStraight_02.png")};
        straightSnakeBody = new AnimatedImage(straightSnakeBodyFrames, 3);

        Image[] snakeTailFrames = new Image[]{
                new Image("/image/snakeTail_01.png")};
        snakeTail = new AnimatedImage(snakeTailFrames, 90);

        emptyCell = new Image("/image/emptyCell_00.png");
    }

    /**
     * Getter for frame of cursor animation
     * @param t Animation timer variable
     * @return Frame of animation
     * @author Andreas Goll Rossau
     */
    Image getCursor(long t) {
        return cursor.getFrame(t);
    }

    /**
     * Getter for frame of mouse animation
     * @param t Animation timer variable
     * @return Frame of animation
     * @author Andreas Goll Rossau
     */
    Image getMouse(long t) {
        return mouse.getFrame(t);
    }

    /**
     * Getter for frame of snake head animation
     * @param t Animation timer variable
     * @return Frame of animation
     * @author Andreas Goll Rossau
     */
    Image getSnakeHead(long t) {
        return snakeHead.getFrame(t);
    }

    /**
     * Getter for frame of straight snake body animation
     * @param t Animation timer variable
     * @return Frame of animation
     * @author Andreas Goll Rossau
     */
    Image getStraightSnakeBody(long t) {
        return straightSnakeBody.getFrame(t);
    }

    /**
     * Getter for frame of snake tail animation
     * @param t Animation timer variable
     * @return Frame of animation
     * @author Andreas Goll Rossau
     */
    Image getSnakeTail(long t) {
        return snakeTail.getFrame(t);
    }

    /**
     * Getter for empty cell image
     * @return Image
     * @author Andreas Goll Rossau
     */
    Image getEmptyCell() { return emptyCell;    }

    /**
     * Getter for frame of bent snake body animation
     * @param t Animation timer variable
     * @return Frame of animation
     * @author Andreas Goll Rossau
     */
    Image getBentSnakeBody(long t) { return bentSnakeBody.getFrame(t); }
}
