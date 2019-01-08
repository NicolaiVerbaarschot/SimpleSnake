package Model;

import java.awt.Point;
import java.util.List;
import java.util.ArrayList;

public class Snake {
    private List<Point> snake_locations = new ArrayList<>();

    public Snake(int grid_x, int grid_y) {
        snake_locations.add(0, new Point(grid_x/2, grid_y/2));
        snake_locations.add(0, new Point(grid_x/2, grid_y/2 + 1));
    }

    public void move(int x, int y, boolean will_grow) {
        snake_locations.add(0, new Point(x, y));

        if (!will_grow) {
            snake_locations.remove(snake_locations.size() - 1);
        }
    }
}