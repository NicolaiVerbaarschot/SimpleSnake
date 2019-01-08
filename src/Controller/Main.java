package Controller;

import Model.SimpleSnake;

public class Main {
        public static void main(String[] args) {


            int grid_x = Integer.parseInt(args[0]);
            int grid_y = Integer.parseInt(args[1]);

            SimpleSnake game1 = new SimpleSnake(grid_x, grid_y);

        }
}
