import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * @author Burak Kara
 */
public class MyScreen extends Screen{

    private final Player player;
    private final KeyboardListener keyboardListener;

    public MyScreen(Game game, Player player) {
        super(game);
        this.player = player;
        this.keyboardListener = game.getKeyboardListener();
    }

    @Override
    public void onCreate() {
        System.out.println("Creating");
    }

    @Override
    public void onUpdate() {
        checkInputs();
    }


    @Override
    public void onDraw(Graphics2D g2d) {
        drawPlayer(g2d);
    }

    private void drawPlayer(Graphics2D g2d) {
        g2d.drawImage(player.getImage(),player.getX(), player.getY(), null);
    }


    private void checkInputs() {
        if(keyboardListener.isKeyPressed(KeyEvent.VK_W)){
            player.setY(player.getY() - 1);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_A)){
            player.setX(player.getX() - 1);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_S)){
            player.setY(player.getY() + 1);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_D)){
            player.setX(player.getX() + 1);
        }
    }

}
