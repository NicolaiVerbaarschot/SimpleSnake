package Model;

import sun.awt.image.ImageWatched;

import java.util.Random;
import java.util.LinkedHashMap;

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
    public Mouse(LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> possible_locations) {
        update_location(possible_locations);
    }

    /**
     * get-method returns the value contained in the private coordinate field: x
     * @return mouse objects x-coordinate
     * @author  Nicolai Verbaarschot
     */
    public int get_x_coordinate() {
        return this.x;
    }

    /**
     * get-method returns the value contained in the private coordinate field: y
     * @return mouse objects y-coordinate
     * @author  Nicolai Verbaarschot
     */
    public int get_y_coordinate() {
        return this.y;
    }

    /**
     * This method will calculate a new and valid location for the mouse
     * @param possible_locations nested map providing the possible mouse locations
     * @author  Nicolai Verbaarschot
     */
    public void update_location(LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> possible_locations) {

        // Calculate outer map size and corresponding index
        int map_size = possible_locations.size();
        int outer_index = random_number_generator.nextInt(map_size);

        // Retrieve nested map
        LinkedHashMap nested_map = (LinkedHashMap) getElementByIndex(possible_locations, outer_index);

        // Calculate nested map size and corresponding index
        int nested_map_size = nested_map.size();
        int inner_index = random_number_generator.nextInt(nested_map_size);

        // Update coordinate fields
        this.x = (Integer) possible_locations.keySet().toArray()[outer_index];
        this.y = (Integer) nested_map.keySet().toArray()[inner_index];
    }

    /**
     * This is a helper method that relates a maps key with an index
     * @param map A nested LinkedHashMap containing all grid cells not occupied by the snake
     * @param index Used to get map values not by key but by index
     * @return the Object linked by the map, given by the index
     * @author Nicolai Verbaarschot
     */
    private Object getElementByIndex(LinkedHashMap map, int index) {
        return map.get((map.keySet().toArray())[index]);
    }
}
