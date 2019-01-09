package View;

import java.awt.*;
import java.util.List;
import java.util.Scanner;

public class SnakeTextView {
    Scanner console = new Scanner(System.in);

    public SnakeTextView() {

    }

    public String getInput() {
        return console.next();
    }

    public void drawBoard(int grid_x, int grid_y, List<Point> snake_location, Point mouse_location) {
        for (int i = 0; i < grid_y; i++) {
            for (int j = 0; j < grid_x; j++) {
                Point p = new Point(j, i);
                if (p.equals(mouse_location)) {
                    System.out.print("M");
                }
                else if (snake_location.contains(p)) {
                    System.out.print("S");
                }
                else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }
}
