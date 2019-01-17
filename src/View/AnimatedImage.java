package View;


import javafx.scene.image.Image;

class AnimatedImage {
    private Image[] frames;
    private int duration;

    AnimatedImage(Image[] frames, int duration) {
        this.frames = frames;
        this.duration = duration;
    }

    Image getFrame(double t) {
        int i = (int) (t%(frames.length * duration) / duration);
        return frames[i];
    }
}
