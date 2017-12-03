import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Burak Kara
 */
public class KeyboardListener implements KeyListener {

    private boolean[] keys = new boolean[256];

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public boolean isKeyPressed(int keyCode){
        return keys[keyCode];
    }

}
