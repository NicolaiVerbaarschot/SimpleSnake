package Controller;
import java.awt.Point;
import java.util.List;

import Model.SimpleSnake;

public class Main {
        public static void main(String[] args) {


            int grid_x = Integer.parseInt(args[0]);
            int grid_y = Integer.parseInt(args[1]);

            // Create game1
            SimpleSnake game1 = new SimpleSnake(grid_x, grid_y);
            // Pass key action "up"
            game1.gameAction("up", grid_x, grid_y);
            // Pass key action "right"
            game1.gameAction("right", grid_x, grid_y);
            // Get snake location
            List<Point> snake1 = game1.get_snake_location();
            // Print snake location
            for (Point p : snake1) {
                System.out.println(p);
            }

        }
}
