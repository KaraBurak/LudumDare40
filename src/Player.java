import javax.swing.*;

/**
 * @author Burak Kara
 */
public class Player extends Sprite {

    private final String pathToImage = "resources/playerMock.png";

    public Player(int x, int y) {
        super(x, y);
        ii = new ImageIcon(this.getClass().getResource(pathToImage));
        image = ii.getImage();
        isVisible = true;
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
    }



}
