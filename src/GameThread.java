import javax.swing.*;
import java.awt.*;

/**
 * @author Burak Kara
 */
public class GameThread extends JPanel implements Runnable {

    private final Game game;

    public static volatile boolean running = true;

    public GameThread(Game game){
        this.game = game;
    }

    @Override
    public void run() {
        while (GameThread.running){
            if(game.getScreen() != null){
                game.getScreen().onUpdate();
                repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        game.getScreen().onDraw(g2d);

    }
}
