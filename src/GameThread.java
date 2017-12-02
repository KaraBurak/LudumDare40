import javax.swing.*;

/**
 * @author Burak Kara
 */
public class GameThread extends JPanel implements Runnable {

    private final Game game;

    public GameThread(Game game){
        this.game = game;
    }

    @Override
    public void run() {
        while (true){
            System.out.println("game thread");
        }
    }
}
