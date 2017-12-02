import javax.swing.*;

/**
 * @author Burak Kara
 */
public class MyGame {

    private final Game game;

    public MyGame(){
        game = new Game();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyGame();
            }
        });
    }

}
