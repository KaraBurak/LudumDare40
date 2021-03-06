import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Burak Kara
 */
public class Game {

    private final JFrame window = new JFrame();
    private final GameThread gameThread;
    private final MusicThread musicThread;
    private Screen screen;
    private final KeyboardListener keyboardListener;

    public Game(){
        window.setSize(Commons.widht,Commons.height);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setFocusable(true);
        window.setLocationRelativeTo(null);
        window.setTitle(Commons.title);
        window.setVisible(true);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                GameThread.running = false;
            }
        });

        gameThread = new GameThread(this);
        musicThread = new MusicThread();
        keyboardListener = new KeyboardListener();

        window.add(gameThread);
        window.addKeyListener(keyboardListener);

        new Thread(gameThread).start();
        new Thread(musicThread).start();
    }

    public void showScreen(MyScreen myScreen){
        this.screen = myScreen;
        screen.onCreate();
    }

    public Screen getScreen() {
        return screen;
    }

    public KeyboardListener getKeyboardListener() {
        return keyboardListener;
    }
}
