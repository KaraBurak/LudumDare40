import javax.swing.*;

/**
 * @author Burak Kara
 */
public class Upgrade extends Sprite {
    public Upgrade(int x, int y) {
        super(x, y);
        ii = new ImageIcon(this.getClass().getResource("resources/upgradeMock.png"));
        image = ii.getImage();
        isVisible = true;
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
    }
}
