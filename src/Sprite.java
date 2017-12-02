import javax.swing.*;
import java.awt.*;

/**
 * @author Burak Kara
 */
public abstract class Sprite {

    public Sprite(int x, int y){

    }

    protected boolean isVisible = false;
    protected int x, y;
    protected int i_width, i_height;
    protected Image image;
    protected ImageIcon ii;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getI_width() {
        return i_width;
    }

    public void setI_width(int i_width) {
        this.i_width = i_width;
    }

    public int getI_height() {
        return i_height;
    }

    public void setI_height(int i_height) {
        this.i_height = i_height;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
