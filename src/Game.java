import javax.swing.*;

/**
 * @author Burak Kara
 */
public class Game {

    private final JFrame window = new JFrame();
    private final GameThread gameThread;

    public Game(){
        window.setSize(Commons.widht,Commons.height);
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setFocusable(true);
        window.setLocationRelativeTo(null);
        window.setTitle(Commons.title);
        window.setVisible(true);

        gameThread = new GameThread(this);

        window.add(gameThread);

        new Thread(gameThread).start();
    }

}
