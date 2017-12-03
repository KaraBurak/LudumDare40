import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * @author Burak Kara
 */
public class MyScreen extends Screen{

    private final Player player1;
    private final Player player2;
    private final KeyboardListener keyboardListener;

    public static ArrayList<Platform> platforms = new ArrayList<>();

    public MyScreen(Game game, Player player1, Player player2) {
        super(game);
        this.player1 = player1;
        this.player2 = player2;
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
        checkPlayers();
        player1.collisionShots(player2.getShots());
        checkInputs();
    }

    private void checkPlayers() {
        player1.setCounterPauseShoot(player1.getCounterPauseShoot() + 1);
        player1.moveShots(player2);
        player1.checkJumpState();
        player1.checkFalling();

        player1.checkKockback();
        player2.checkKockback();


        player2.setCounterPauseShoot(player2.getCounterPauseShoot() + 1);
        player2.moveShots(player1);
        player2.checkJumpState();
        player2.checkFalling();
    }


    @Override
    public void onDraw(Graphics2D g2d) {
        drawPlatforms(g2d);
        drawPlayer(g2d);
        drawShots(g2d);
        drawEffects(g2d);
    }

    private void drawEffects(Graphics2D g2d) {
        g2d.drawRect(player1.getX(), player1.getY() - player1.getI_height() / 2, player1.getLoadingShotRectangle().getWidth(), 10);
        g2d.fillRect(player1.getX(), player1.getY() - player1.getI_height() / 2, (int) player1.getLoadingShotRectangle().
                setRectangleWidth(player1.getLoadingShotRectangle().
                        getPercentage(player1.getCounterPauseShoot(),
                                player1.getPauseShootTime())), 10);

        g2d.drawRect(player2.getX(), player2.getY() - player2.getI_height() / 2, player2.getLoadingShotRectangle().getWidth(), 10);
        g2d.fillRect(player2.getX(), player2.getY() - player2.getI_height() / 2, (int) player2.getLoadingShotRectangle().
                setRectangleWidth(player2.getLoadingShotRectangle().
                        getPercentage(player2.getCounterPauseShoot(),
                                player2.getPauseShootTime())), 10);

    }

    private void drawPlatforms(Graphics2D g2d) {
        platforms.forEach(platform -> g2d.drawImage(platform.getImage(),platform.getX(),platform.getY(),null));
    }

    private void drawShots(Graphics2D g2d) {
        player1.getShots().forEach(shot -> g2d.drawImage(shot.getImage(),shot.getX(),shot.getY(), null));
        player2.getShots().forEach(shot -> g2d.drawImage(shot.getImage(),shot.getX(),shot.getY(), null));
    }

    private void drawPlayer(Graphics2D g2d) {
        g2d.drawImage(player1.getImage(), player1.getX(), player1.getY(), null);
        g2d.drawImage(player2.getImage(), player2.getX(), player2.getY(), null);
    }


    private void checkInputs() {

        if(keyboardListener.isKeyPressed(KeyEvent.VK_A)){
            player1.setX(player1.getX() - 1);
            player1.setDirection(Direction.LEFT);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_D)){
            player1.setX(player1.getX() + 1);
            player1.setDirection(Direction.RIGHT);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_CONTROL)){
            player1.addShot(new Shot(player1.getX(), player1.getY(), player1.getDirection()));
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_SPACE) && !player1.isJumping()){
            player1.jump();
        }

        if(getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_LEFT)) {
            player2.setX(player2.getX() - 2);
            player2.setDirection(Direction.LEFT);
        }

        if(getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_RIGHT)) {
            player2.setX(player2.getX() + 2);
            player2.setDirection(Direction.RIGHT);
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_UP) && !player2.isJumping()){
            player2.jump();
        }

        if(keyboardListener.isKeyPressed(KeyEvent.VK_DOWN)){
            player2.addShot(new Shot(player2.getX(), player2.getY(), player2.getDirection()));
        }

    }

}
