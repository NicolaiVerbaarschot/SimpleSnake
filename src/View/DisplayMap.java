package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.HashMap;

/**
 * Class implements a map of canvasses to display images on
 *
 * @author Andreas Goll Rossau
 */
class DisplayMap {
    private HashMap<Integer, HashMap<Integer, Canvas>> map = new HashMap<>();
    private int cell_size;

    /**
     * Contructor adds canvasses to map
     * @param grid_x Number of canvasses in x dimension
     * @param grid_y Number of canvasses in y dimension
     * @param cell_size Size of each canvas
     *
     * @author Andreas Goll Rossau
     */
    DisplayMap(int grid_x, int grid_y, int cell_size) {
        this.cell_size = cell_size;
        for (int i = 0; i < grid_x; i++) {
            map.put(i, new HashMap<>());
            for (int j = 0; j < grid_y; j++) {
                map.get(i).put(j, new Canvas(cell_size, cell_size));
            }
        }
    }

    /**
     * Method adds all canvasses in map to a GridPane
     * @param pane GridPane to add canvasses to
     * @author Andreas Goll Rossau
     */
    void addToGrid(GridPane pane) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                pane.add(getCanvas(i, j), i, j, 1, 1);
            }
        }
    }

    /**
     * Returns canvas based on indexes
     * @param i Index in x dimension
     * @param j Index in y dimension
     * @return canvas found by index
     * @author Andreas Goll Rossau
     */
    private Canvas getCanvas(int i, int j) {
        return map.get(i).get(j);
    }

    /**
     * Returns canvas indexed by Point location
     * @param p Point to index by
     * @return canvas found by index
     * @author Andreas Goll Rossau
     */
    Canvas getCanvas(Point p) {
        return map.get((int) p.getX()).get((int) p.getY());
    }

    /**
     * Method draws same image all canvasses
     * @param img Image to draw
     * @author Andreas Goll Rossau
     */
    void drawAll(Image img) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                draw(i, j, img);
            }
        }
    }

    /**
     * Method draws image on canvas indexed by Point
     * @param p Point to index by
     * @param img Image to draw
     * @author Andreas Goll Rossau
     */
    void draw(Point p, Image img) {
        getCanvas(p).getGraphicsContext2D().drawImage(img, 0, 0, cell_size, cell_size);
    }

    /**
     * Method draws image on canvas based on indexes
     * @param i Index in x dimension
     * @param j Index in y dimension
     * @param img Image to draw
     * @author Andreas Goll Rossau
     */
    private void draw(int i, int j, Image img) {
        getCanvas(i, j).getGraphicsContext2D().drawImage(img, 0, 0, cell_size, cell_size);
    }

    /**
     * Method clears canvas found by index
     * @param i Index in x dimension
     * @param j Index in y dimension
     * @author Andreas Goll Rossau
     */
    private void clear(int i, int j) {
        getCanvas(i, j).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
    }

    /**
     * Method clears canvas indexed by Point
     * @param p Point to index by
     * @author Andreas Goll Rossau
     */
    void clear(Point p) {
        getCanvas(p).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
    }

    /**
     * Method clears all canvasses
     * @author Andreas Goll Rossau
     */
    void clearAll() {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                clear(i, j);
            }
        }
    }

    /**
     * Method resets all canvas rotations
     * @author Andreas Goll Rossau
     */
    void resetRotations() {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                getCanvas(i, j).setRotate(0);
            }
        }
    }
}
