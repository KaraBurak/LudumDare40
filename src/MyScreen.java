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
        player.moveShots();
    }


    @Override
    public void onDraw(Graphics2D g2d) {
        drawPlayer(g2d);
        drawShots(g2d);
    }

    private void drawShots(Graphics2D g2d) {
        player.getShots().forEach(shot -> g2d.drawImage(shot.getImage(),shot.getX(),shot.getY(), null));
    }

    private void drawPlayer(Graphics2D g2d) {
        g2d.drawImage(player.getImage(),player.getX(), player.getY(), null);
    }


    private void checkInputs() {
        if(keyboardListener.isKeyPressed(KeyEvent.VK_W)){
            player.setY(player.getY() - 1);
            player.setDirection(Direction.UP);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_A)){
            player.setX(player.getX() - 1);
            player.setDirection(Direction.LEFT);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_S)){
            player.setY(player.getY() + 1);
            player.setDirection(Direction.DOWN);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_D)){
            player.setX(player.getX() + 1);
            player.setDirection(Direction.RIGHT);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_SPACE)){
            player.addShot(new Shot(player.getX(), player.getY(), player.getDirection()));
        }

    }

}
