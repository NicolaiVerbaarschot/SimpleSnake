package View;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;

import java.awt.*;
import java.util.HashMap;

public class DisplayMap {
    private HashMap<Integer, HashMap<Integer, Canvas>> map = new HashMap<>();
    private int cell_size;

    public DisplayMap(int grid_x, int grid_y, int cell_size) {
        this.cell_size = cell_size;
        for (int i = 0; i < grid_x; i++) {
            map.put(i, new HashMap<>());
            for (int j = 0; j < grid_y; j++) {
                map.get(i).put(j, new Canvas(cell_size, cell_size));
            }
        }
    }

    public void addToGrid(GridPane pane) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                pane.add(getCanvas(i, j), i, j, 1, 1);
            }
        }
    }

    public Canvas getCanvas(int i, int j) {
        return map.get(i).get(j);
    }

    public Canvas getCanvas(Point p) {
        return map.get((int) p.getX()).get((int) p.getY());
    }

    public void drawAll(Image img) {
        for (int i = 0; i < map.size(); i++) {
            for (int j = 0; j < map.get(i).size(); j++) {
                getCanvas(i, j).getGraphicsContext2D().drawImage(img, 0, 0, cell_size, cell_size);
            }
        }
    }

    public void draw(Point p, Image img) {
        getCanvas(p).getGraphicsContext2D().drawImage(img, 0, 0, cell_size, cell_size);
    }

    public void draw(int i, int j, Image img) {
        getCanvas(i, j).getGraphicsContext2D().drawImage(img, 0, 0, cell_size, cell_size);
    }

    public void clear(int i, int j) {
        getCanvas(i, j).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
    }

    public void clear(Point p) {
        getCanvas(p).getGraphicsContext2D().clearRect(0, 0, cell_size, cell_size);
    }
}
