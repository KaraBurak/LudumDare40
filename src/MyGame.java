import javax.swing.*;

/**
 * @author Burak Kara
 */
public class MyGame {

    private final Game game;
    private final Player player;

    public MyGame(){
        game = new Game();
        player = new Player(100,100);
        game.showScreen(new MyScreen(game,player));
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
