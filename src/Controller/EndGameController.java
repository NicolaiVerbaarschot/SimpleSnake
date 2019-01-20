package Controller;

import View.EndGameView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import java.util.List;

public class EndGameController {

    private EndGameView endGameView;
    /*
    private int grid_x;
    private int grid_y;
    private StackPane stackPane;
    private Stage primary_stage;
    */

    public EndGameController(int grid_x, int grid_y, StackPane stack_pane, Stage primary_stage) {

        /*this.grid_x = grid_x;
        this.grid_y = grid_y;
        this.stackPane = stack_pane;
        this.primary_stage = primary_stage;
        */
        this.endGameView = new EndGameView(grid_x, grid_y, primary_stage, this);
    }

    public void initialize_window() {

        endGameView.open_end_game();
    }

    public void stop_window() {
        endGameView.clear_endgame();
    }

    // take input from EndGameView
    public void key_press(String keyboard_input) {

        if (keyboard_input.equals("r") || keyboard_input.equals("escape")) {
            endGameView.clear_endgame();
        }
        // if restart button
        // clear endGameView

        // if exit button
        // nothing - I think
    }

    /*public void set_high_score_name(String name) {
        this.high_score_name = name;
    }

    public String get_high_score_name() {
        return high_score_name;
    }*/
}
