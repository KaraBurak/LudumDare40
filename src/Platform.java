import javax.swing.*;

/**
 * @author Burak Kara
 */
public class Platform extends Sprite {

    public Platform(int x, int y) {
        super(x, y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource("resources/platformMock.png"));
        image = ii.getImage();
        isVisible = true;
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
    }

}
