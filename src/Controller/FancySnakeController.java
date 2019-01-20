package Controller;

import Model.SimpleSnake;
import View.FancySnakeView;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
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

    Stage stage;
    Scene gameScene;

    private List<String> stored_keyboard_inputs = new ArrayList<>();
    private String requested_display;
    private String display;
    private String last_succeeded_display;

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
    public FancySnakeController(int grid_x, int grid_y, StackPane stack_pane, Stage primary_stage, MenuController menuController) throws IOException {

        this.game = new SimpleSnake(grid_x, grid_y);
        this.view = new FancySnakeView(grid_x, grid_y, stack_pane, primary_stage);
        this.menuController = menuController;
        this.stage = primary_stage;

        endgame_flag = false;

        this.last_succeeded_display = "none";
        this.display = "up";

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
    // endgameT -> r || escape
    public void set_direction(String keyboard_input){

        if (stored_keyboard_inputs.size() < 3 && (!endgame_flag || (keyboard_input.equals("r") || keyboard_input.equals("escape")))) {
            stored_keyboard_inputs.add(0, keyboard_input);
        }
    }

    /**
     * Method passes key input code to model and updates view according to game status returned from model
     *
     * @param   code key: Input code
     * @author  Andreas Goll Rossau
     */
    void key_press(String code) throws IOException {

        // Do not proceed display if the game has ended (Game Over or Game Won) unless the display attempt is "r" or "escape"
        if (endgame_flag && !(code.equals("r") || code.equals("escape")))
            return;

        // Update Model and return game status
        String game_status = game.game_action(code);

        // Note the most recently displayed display
        last_succeeded_display = display;

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
                view.clear_board();
                view.draw_board(game.get_snake_segments(), game.get_mouse_location());
                view.set_score_bar(game.get_score());
                display = "up";
                last_succeeded_display = "none";
                endgame_flag = false;
                break;
            case "Exit":
                menuController.reinitialize();
                timer.stop();
                System.gc();
                break;
            default:
                // Display Game Over or Game Won
                StackPane stack_pane = new StackPane();
                Scene scene = new Scene(stack_pane);
                gameScene = stage.getScene();
                stage.setScene(scene);

                view.gameOverScreen(game.isHighScore(game.get_score()), game_status, game.get_score(), stack_pane);

                scene.setOnKeyPressed(
                        event -> {
                            try {
                                gameOverKeyPress(event.getCode().toString(), stack_pane, game_status);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                );

                //view.print_status(game_status);
                last_succeeded_display = "none";
                endgame_flag = true;
                break;
        }
    }

    private void gameOverKeyPress(String input, StackPane stackPane, String game_status) throws IOException {
        if (input.length() == 1 && Character.isAlphabetic(input.charAt(0))) {
            view.addToName(input);
        }
        else if (input.equals("BACK_SPACE")) {
            view.removeFromName();
        }
        else if (input.equals("ENTER")) {
            if (game.isHighScore(game.get_score())) {
                game.update_high_scores(view.getName());
            }
            stage.setScene(gameScene);
            key_press("r");
            view.resetName();
        }
        else if (input.equals("ESCAPE")) {
            stage.setScene(gameScene);
                key_press(input.toLowerCase());
            view.resetName();
        }
        view.updatePlayerName();
    }

    /**
     * Method checks validity of the keyboard input's requested display of the snake, and calls View for displaying accordingly
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

                view.update_board(game.get_snake_segments(), game.get_mouse_location(), t);

                if (now - last_update_1 >= 100000000) {

                    // Initiate inner in-between-frames time keeping variable - in case of more inputs between frames (with a maximum of three)
                    long last_update_2 = last_update_1;

                    // Display frame for each stored input (with a minimum of one and a maximum of three)
                    for (int i = 0; i < stored_keyboard_inputs.size() + 1; i++) {

                        if (now - last_update_2 >= 90000000) {

                            // If there has been no new inputs, prepare to check the validity of the most recent shown display
                            if (stored_keyboard_inputs.isEmpty()) {
                                requested_display = last_succeeded_display;
                            } // Otherwise prepare to check the validity of the new inputs
                            else {
                                requested_display = stored_keyboard_inputs.get(stored_keyboard_inputs.size() - 1);
                            }

                            // Check the validity of requested display
                            if (requested_display.equals("up") && !(last_succeeded_display.equals("down")) ||
                                    requested_display.equals("down") && !(last_succeeded_display.equals("up")) ||
                                    requested_display.equals("left") && !(last_succeeded_display.equals("right")) ||
                                    requested_display.equals("right") && !(last_succeeded_display.equals("left")) ||
                                    requested_display.equals("escape") ||
                                    requested_display.equals("r")) {
                                // Set this.display to be used for displaying
                                display = requested_display;
                            }

                            // Display display
                            try {
                                key_press(display);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        } last_update_2 = now;
                    } last_update_1 = now;
                }
            }
        };
    }
}