package View;


import javafx.scene.image.Image;

/**
 * Class implements animated image with frames switching after a certain duration
 * @author Andreas Goll Rossau
 */
class AnimatedImage {
    private Image[] frames;
    private int duration;


    /**
     * Constructor
     * @param frames: Array of frames in animation
     * @param duration: How long to display each frame
     * @author Andreas Goll Rossau
     */
    AnimatedImage(Image[] frames, int duration) {
        this.frames = frames;
        this.duration = duration;
    }

    /**
     * Gets frame based on time variable
     * @param t: Time variable
     * @return Current animation frame
     * @author Andreas Goll Rossau
     */
    Image getFrame(double t) {
        int i = (int) (t%(frames.length * duration) / duration);
        return frames[i];
    }
}
