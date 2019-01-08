package Model;

import java.util.Random;

/**
 * The Mouse Class implements the 'food' for the SimpleSnake game.
 * Instances are instantiated with random coordinates, which can be obtained publicly through get methods.
 * There exits a public 'randomizer' method which will shuffle the mouses coordinates.
 *
 * @author  Nicolai Verbaarschot
 */
public class Mouse {

    private Random random_number_generator = new Random();
    private int x;
    private int y;

    /**
     * Constructor
     * @author  Nicolai Verbaarschot
     */
    public Mouse(int grid_x, int grid_y) {
        this.x = random_number_generator.nextInt(grid_x);
        this.y = random_number_generator.nextInt(grid_y);
    }

    /**
     * get-method returns the value contained in the private coordinate field: x
     * @return mouse objects x-coordinate
     * @author  Nicolai Verbaarschot
     */
    public int get_x_coordinates() {
        return this.x;
    }

    /**
     * get-method returns the value contained in the private coordinate field: y
     * @return mouse objects y-coordinate
     * @author  Nicolai Verbaarschot
     */
    public int get_y_coordinates() {
        return this.y;
    }

    /**
     * This is a randomizer method that will randomize the grid coordinates of a Mouse Instance
     * @author  Nicolai Verbaarschot
     */
    public void randomize_coordinates(int grid_x, int grid_y) {
        this.x = random_number_generator.nextInt(grid_x);
        this.y = random_number_generator.nextInt(grid_y);
    }
}
