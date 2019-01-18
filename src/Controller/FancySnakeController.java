package Controller;

import Model.SimpleSnake;
import View.FancySnakeView;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class controls the Simple Snake game
 *
 * @author  Andreas Goll Rossau
 */
public class FancySnakeController {
    private SimpleSnake game;
    private FancySnakeView view;
    private boolean endgame_flag;
    private MenuController menuController;

    AnimationTimer timer;

    public boolean frame_running;
    private String latest_keyboard_input;
    private List<String> stored_keyboard_inputs = new ArrayList<>();
    private String requested_direction;
    private String direction;
    private String latest_displayed_direction;

    /**
     * Constructor. The program never leaves this constructor unless the game ends
     *
     * @param   grid_x : The grid size in the x dimension
     * @param   grid_y : The grid size in the y dimension
     * @param   stack_pane : JavaFX Node
     * @param   primary_stage : JavaFX Node
     * @param   menuController : Controller for main menu
     * @author  Andreas Goll Rossau
     */
    public FancySnakeController(int grid_x, int grid_y, StackPane stack_pane, Stage primary_stage, MenuController menuController) {

        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new FancySnakeView(grid_x, grid_y, stack_pane, primary_stage);
        this.menuController = menuController;

        endgame_flag = false;

        this.latest_displayed_direction = "none";
        this.direction = "up";

        // Initialize window
        view.draw_board(game.get_snake_segments(), game.get_mouse_location());
        view.set_score_bar(game.get_score());

        // Start animation
        animation_loop();
        timer.start();
    }

    /**
     * Method saves keyboard inputs in an array list of size three and keeps track of the most recent input
     * @param keyboard_input from the user passed from MainFancyApp in lowercase String form
     * @author Thea Birk Berger
     */
    public void set_direction(String keyboard_input){

        latest_keyboard_input = keyboard_input;

        if (stored_keyboard_inputs.size() < 3) {
            stored_keyboard_inputs.add(0, keyboard_input);
        }
    }

    /**
     * Method passes key input code to model and updates view according to game status returned from model
     *
     * @param   code key: Input code
     * @author  Andreas Goll Rossau
     */
    void key_press(String code) {

        if (endgame_flag && !(code.equals("r") || code.equals("escape")))
            return;

        // Update Model and return game status
        String game_status = game.game_action(code);

        // Note the most recently displayed direction
        latest_displayed_direction = direction;

        // Remove stored input if it has been displayed
        if (!stored_keyboard_inputs.isEmpty()) {
            stored_keyboard_inputs.remove(stored_keyboard_inputs.size() - 1);
        }

        // Display game according to game status (playing, new game, game over, game won or exit)
        switch (game_status) {
            case "Playing":
                view.set_score_bar(game.get_score());
                break;
            case "Restart":
                game.reset_game();
                view.clear_endgame();
                view.draw_board(game.get_snake_segments(), game.get_mouse_location());
                view.set_score_bar(game.get_score());
                view.set_score_bar(game.get_score());
                direction = "up";
                latest_displayed_direction = "none";
                endgame_flag = false;
                break;
            case "Exit":
                menuController.reinitialize();
                timer.stop();
                System.gc();
                break;
            default:
                // Display Game Over or Game Won
                view.print_status(game_status);
                endgame_flag = true;
                break;
        }
    }

    /**
     * Method checks validity of the keyboard input's requested direction of the snake, and calls View for displaying accordingly
     * Keeps track of the time between each frame displayed to create illusion of movement - animation
     * @author Thea Birk Berger
     */
    public void animation_loop() {

        // Extract timer from System
        final long startNanoTime = System.nanoTime();

        timer = new AnimationTimer() {

            // Initiate outer in-between-frames time keeping variable
            private long last_update_1 = 0 ;

            @Override
            public void handle(long now) {

                long t = (now - startNanoTime) / 100000000;

                view.update_board(game.get_snake_segments(), game.get_tail(), game.get_mouse_location(), t);

                if (now - last_update_1 >= 280000000) {

                    // Initiate inner in-between-frames time keeping variable - in case of more inputs between frames (with a maximum of three)
                    long last_update_2 = last_update_1;

                    // Display frame for each stored input (with a minimum of one and a maximum of three)
                    for (int i = 0; i < stored_keyboard_inputs.size() + 1; i++) {

                        if (now - last_update_2 >= 220000000) {

                            // If there has been no new inputs, prepare to check the validity of the most recent shown direction
                            if (stored_keyboard_inputs.isEmpty()) {
                                requested_direction = latest_displayed_direction;
                            } // Otherwise prepare to check the validity of the new inputs
                            else {
                                requested_direction = stored_keyboard_inputs.get(stored_keyboard_inputs.size() - 1);
                            }

                            // Check the validity of requested direction
                            if (requested_direction.equals("up") && !(latest_displayed_direction.equals("down")) ||
                                    requested_direction.equals("down") && !(latest_displayed_direction.equals("up")) ||
                                    requested_direction.equals("left") && !(latest_displayed_direction.equals("right")) ||
                                    requested_direction.equals("right") && !(latest_displayed_direction.equals("left")) ||
                                    requested_direction.equals("escape") ||
                                    requested_direction.equals("r")) {
                                // Set this.direction to be used for displaying
                                direction = requested_direction;
                            }

                            // Display direction
                            key_press(direction);

                        } last_update_2 = now;
                    } last_update_1 = now;
                }
            }
        };
    }
}