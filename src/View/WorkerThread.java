package View;

import java.awt.*;

public class WorkerThread implements Runnable {

    private DisplayMap map;
    private Point location;
    private int grid_x;
    private int grid_y;

    WorkerThread(DisplayMap map, Point location, int grid_x, int grid_y){
        this.map = map;
        this.location = location;
        this.grid_x = grid_x;
        this.grid_y = grid_y;
    }

    @Override
    public void run() {
        try {
            time_and_remove_blood();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void time_and_remove_blood() throws InterruptedException {
        Thread.sleep(5000);

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                Point p = new Point(location);
                p.translate(i, j);
                collision_check(p, grid_x - 1, grid_y - 1);
                map.clear(p);
            }
        }

        System.out.println("Clear");
    }
    private void collision_check(Point p, int x_max, int y_max) {
        if (p.getX() == -1) {
            p.x = x_max;
        } else if (p.getX() == x_max + 1) {
            p.x = 0;
        }
        if (p.getY() == -1) {
            p.y = y_max;
        }
        else if (p.getY() == y_max + 1) {
            p.y = 0;
        }
    }
}