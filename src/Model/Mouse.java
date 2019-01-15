package Model;

import sun.awt.image.ImageWatched;

import java.util.Random;
import java.util.LinkedHashMap;

/**
 * The Mouse Class implements the 'food' for the SimpleSnake game.
 * Instances are instantiated with random coordinates, which can be obtained publicly through get methods.
 *
 * @author  Nicolai Verbaarschot
 */
class Mouse {

    private Random random_number_generator = new Random();
    private int x;
    private int y;

    /**
     * Constructor
     * @author  Nicolai Verbaarschot
     */
    Mouse(LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> possible_locations) {
        update_location(possible_locations);
    }

    /**
     * get-method returns the value contained in the private coordinate field: x
     * @return mouse objects x-coordinate
     * @author  Nicolai Verbaarschot
     */
    int get_x_coordinate() {
        return this.x;
    }

    /**
     * get-method returns the value contained in the private coordinate field: y
     * @return mouse objects y-coordinate
     * @author  Nicolai Verbaarschot
     */
    int get_y_coordinate() {
        return this.y;
    }

    /**
     * This method will calculate a new and valid location for the mouse
     * @param possible_locations nested map providing the possible mouse locations
     * @author  Nicolai Verbaarschot
     */
    void update_location(LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> possible_locations) {

        // Calculate outer map size and corresponding index
        int map_size = possible_locations.size();
        int outer_index = random_number_generator.nextInt(map_size);

        // Retrieve nested map
        LinkedHashMap nested_map = possible_locations.get((possible_locations.keySet().toArray())[outer_index]);

        // Calculate nested map size and corresponding index
        int nested_map_size = nested_map.size();
        int inner_index = random_number_generator.nextInt(nested_map_size);

        // Update coordinate fields
        this.x = (Integer) possible_locations.keySet().toArray()[outer_index];
        this.y = (Integer) nested_map.keySet().toArray()[inner_index];
    }
}
