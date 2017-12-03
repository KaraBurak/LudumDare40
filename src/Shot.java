import javax.swing.*;

/**
 * @author Burak Kara
 */
public class Shot extends Sprite{

    private Direction direction;

    public Shot(int x, int y, Direction direction) {
        super(x, y);
        ImageIcon ii = new ImageIcon(this.getClass().getResource("resources/shot.png"));
        image = ii.getImage();
        isVisible = true;
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
