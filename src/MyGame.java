import javax.swing.*;

/**
 * @author Burak Kara
 */
public class MyGame {

    private final Game game;
    private final Player player1;
    private final Player player2;
    private final String pathToImage1 = "resources/playerMock.png";
    private final String pathToImage2 = "resources/playerMock.png";

    public MyGame(){
        game = new Game();
        player1 = new Player(50,250, pathToImage1);
        player2 = new Player(250,250, pathToImage2);
        game.showScreen(new MyScreen(game,player1,player2));
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
