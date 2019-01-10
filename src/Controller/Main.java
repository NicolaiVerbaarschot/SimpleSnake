package Controller;

// Run this class to play
// Pass grid size using terminal

public class Main {
        public static void main(String[] args) {
            SnakeTextController controller = new SnakeTextController(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }
}
