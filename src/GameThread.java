import javax.swing.*;
import java.awt.*;

/**
 * @author Burak Kara
 */
public class GameThread extends JPanel implements Runnable {

    private final Game game;
    public static volatile boolean running = true;
    private final int TICKS_PER_SECOND = 60;
    private final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    private final int MAX_FRAMESKIP = 1;

    public GameThread(Game game){
        this.game = game;
    }

    @Override
    public void run() {
        double next_game_tick = System.currentTimeMillis();
        int loops;
        while (running){
            loops = 0;
            while (loops < MAX_FRAMESKIP && System.currentTimeMillis() > next_game_tick) {
                if (game.getScreen() != null) {
                    game.getScreen().onUpdate();
                    repaint();
                }
                next_game_tick += SKIP_TICKS;
                loops++;
            }
        }
        JOptionPane.showMessageDialog(null, MyScreen.winningMessage);
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
