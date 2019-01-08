package Model;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;


public class Gameboard {

    private LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> board;

    public Gameboard(int grid_x, int grid_y) {

        // Create gameboard
        this.board = new LinkedHashMap<>();

        // Create gameboard map
        for (int i = 0; i < grid_x; i++) {
            board.put(i, new LinkedHashMap<>());
            for (int j = 0; j < grid_y; j++) {
                board.get(i).put(j, 0);
            }
        }

    }

    public void add(Point p) {
        board.putIfAbsent((int) p.getX(), new LinkedHashMap<>()).put((int) p.getY(), 0);
    }

    public void remove(Point p) {
        // Remove Y from X map
        board.get((int) p.getX()).remove((int) p.getY());
        // Remove X map if empty
        if (board.get((int) p.getX()).isEmpty()) {
            board.remove((int) p.getX());
        }
    }

    public LinkedHashMap<Integer, LinkedHashMap<Integer, Integer>> get_board() {
        return board;
    }


}
