import javax.swing.*;

/**
 * @author Burak Kara
 */
public class Background extends Sprite {
    public Background(int x, int y) {
        super(x, y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource("resources/Background.png"));
        image = ii.getImage();
    }
}
