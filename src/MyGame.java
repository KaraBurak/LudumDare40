import javax.swing.*;

/**
 * @author Burak Kara
 */
public class MyGame {

    private final Game game;
    private final Player player1;
    private final Player player2;
    private final String player1PathImageRight = "resources/wizard1Right.png";
    private final String player1PathImageLeft = "resources/wizard1Left.png";
    private final String player2PathImageLeft = "resources/wizard2Left.png";
    private final String player2PathImageRight = "resources/wizard2Right.png";

    public MyGame(){
        game = new Game();
        player1 = new Player(50,250, player1PathImageRight, player1PathImageLeft);
        player2 = new Player(250,250, player2PathImageRight, player2PathImageLeft);
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
