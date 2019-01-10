package Controller;
import java.awt.Point;
import java.util.List;

import Model.SimpleSnake;

// Run this class to play
// Pass grid size using terminal

public class Main {
        public static void main(String[] args) {

            SnakeController controller = new SnakeController(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        }
}
