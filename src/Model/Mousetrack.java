package Model;

import java.awt.Point;
import java.util.LinkedHashMap;


/**
 * Class keeps track of the empty spaces available for the mouse
 *
 * @author  Andreas Goll Rossau
 */
class Mousetrack {

    private LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> track;

    /**
     * Constructor initially adds all spaces in the grid
     *
     * @param   grid_x: grid size in x dimension
     * @param   grid_y: grid size in x dimension
     * @author  Andreas Goll Rossau
     */
    Mousetrack(int grid_x, int grid_y) {

        // Create mousetrack
        this.track = new LinkedHashMap<>();

        // Create mousetrack map of all X times Y cells
        for (int i = 0; i < grid_x; i++) {
            track.put(i, new LinkedHashMap<>());
            for (int j = 0; j < grid_y; j++) {
                track.get(i).put(j, 0);
            }
        }
    }

    /**
     * Method returns the map
     *
     * @return  map of spaces available for the mouse
     * @author  Andreas Goll Rossau
     */
    LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> get_track() {
        return track;
    }

    /**
     * Method adds points to map.
     *
     * @param   p: the point to be added
     * @author  Andreas Goll Rossau
     */
    void add(Point p) {
        // If no y coordinates in one x-column were available, we have to create a new nested map
        track.computeIfAbsent((int) p.getX(), k -> new LinkedHashMap<>()).put((int) p.getY(), 0);
    }

    /**
     * Method removes points from map
     *
     * @param   p: point to be removed
     * @author  Andreas Goll Rossau
     */
    void remove(Point p) {
        if (track.get((int) p.getX()) != null) {
            // Remove Y from X map
            track.get((int) p.getX()).remove((int) p.getY());
            // Remove nested map if empty
            if (track.get((int) p.getX()).isEmpty()) {
                track.remove((int) p.getX());
            }
        }
    }

}
