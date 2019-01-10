package Model;

import java.awt.*;
import java.util.LinkedHashMap;


public class Mousetrack {

    private LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> track;

    public Mousetrack(int grid_x, int grid_y) {

        // Create mousetrack
        this.track = new LinkedHashMap<>();

        // Create mousetrack map
        for (int i = 0; i < grid_x; i++) {
            track.put(i, new LinkedHashMap<>());
            for (int j = 0; j < grid_y; j++) {
                track.get(i).put(j, 0);
            }
        }

    }

    public void add(Point p) {
        track.computeIfAbsent((int) p.getX(), k -> new LinkedHashMap<>()).put((int) p.getY(), 0);
    }

    public void remove(Point p) {
        // Remove Y from X map
        track.get((int) p.getX()).remove((int) p.getY());
        // Remove X map if empty
        if (track.get((int) p.getX()).isEmpty()) {
            track.remove((int) p.getX());
        }
    }

    public LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> get_track() {
        return track;
    }


}
