package Controller;

/**
 * Run this class to play text snake
 *
 * @author  Andreas Goll Rossau
 */
public class Main {
    /**
     * main method initializes text snake controller
     *
     * @param   args command line arguments should be x and y dimensions of game grid
     * @author  Andreas Goll Rossau
     */
    public static void main(String[] args) {
        new SnakeTextController(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
}
