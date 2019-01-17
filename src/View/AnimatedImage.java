package View;


import javafx.scene.image.Image;

public class AnimatedImage {
    private Image[] frames;
    private int duration;

    public AnimatedImage(Image[] frames, int duration) {
        this.frames = frames;
        this.duration = duration;
    }

    public Image getFrame(double t) {
        int i = (int) (t%(frames.length * duration) / duration);
        return frames[i];
    }
}
