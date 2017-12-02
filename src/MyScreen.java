import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author Burak Kara
 */
public class MyScreen extends Screen{

    private final Player player;
    private final KeyboardListener keyboardListener;

    public static ArrayList<Platform> platforms = new ArrayList<>();

    public MyScreen(Game game, Player player) {
        super(game);
        this.player = player;
        this.keyboardListener = game.getKeyboardListener();
    }

    @Override
    public void onCreate() {
        System.out.println("Creating");
        platforms.add(new Platform(50,350));
        platforms.add(new Platform(250,350));
    }

    @Override
    public void onUpdate() {
        player.setCounterPauseShoot(player.getCounterPauseShoot() + 1);
        checkInputs();
        player.moveShots();
        player.checkJumpState();
        player.checkFalling();
    }




    @Override
    public void onDraw(Graphics2D g2d) {
        drawPlatforms(g2d);
        drawPlayer(g2d);
        drawShots(g2d);
        drawEffects(g2d);
    }

    private void drawEffects(Graphics2D g2d) {
        g2d.drawRect(player.getX(), player.getY() - player.getI_height() / 2, player.getLoadingShotRectangle().getWidth(), 10);
        g2d.fillRect(player.getX(), player.getY() - player.getI_height() / 2, (int) player.getLoadingShotRectangle().
                setRectangleWidth(player.getLoadingShotRectangle().
                        getPercentage(player.getCounterPauseShoot(), player.getPauseShootTime())), 10);
    }

    private void drawPlatforms(Graphics2D g2d) {
        platforms.forEach(platform -> g2d.drawImage(platform.getImage(),platform.getX(),platform.getY(),null));
    }

    private void drawShots(Graphics2D g2d) {
        player.getShots().forEach(shot -> g2d.drawImage(shot.getImage(),shot.getX(),shot.getY(), null));
    }

    private void drawPlayer(Graphics2D g2d) {
        g2d.drawImage(player.getImage(),player.getX(), player.getY(), null);
    }


    private void checkInputs() {

        if(keyboardListener.isKeyPressed(KeyEvent.VK_A)){
            player.setX(player.getX() - 1);
            player.setDirection(Direction.LEFT);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_D)){
            player.setX(player.getX() + 1);
            player.setDirection(Direction.RIGHT);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_CONTROL)){
            player.addShot(new Shot(player.getX(), player.getY(), player.getDirection()));
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_SPACE) && !player.isJumping()){
            player.jump();
        }

    }

}
