import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * @author Burak Kara
 */
public class MyScreen extends Screen{

    private final Player player1;
    private final Player player2;
    private final KeyboardListener keyboardListener;
    private int newUpgradeCounter = 0;
    private int newUpgradeThresh = 100;
    private Random random = new Random();
    public static ArrayList<Platform> platforms = new ArrayList<>();
    private ArrayList<Upgrade> upgrades = new ArrayList<>();
    static boolean screenshake = false;
    private int screenshakeCounter = 0;
    private final Background background;
    static String winningMessage;

    public MyScreen(Game game, Player player1, Player player2) {
        super(game);
        this.player1 = player1;
        this.player2 = player2;
        background = new Background(0,0);
        this.keyboardListener = game.getKeyboardListener();
    }

    @Override
    public void onCreate() {
        platforms.add(new Platform(160,480));
        platforms.add(new Platform(320,480));
        platforms.add(new Platform(480,480));
        platforms.add(new Platform(640,480));
        platforms.add(new Platform(800,480));
        platforms.add(new Platform(960,480));
        platforms.add(new Platform(400, 380));
        platforms.add(new Platform(720, 380));
        platforms.add(new Platform(560, 280));
        upgrades.add(new Upgrade(615, 240, random.nextInt(3)));
        player2.setImageLeft();
        player2.setDirection(Direction.LEFT);
    }

    @Override
    public void onUpdate() {
        addUpgrade();
        checkPlayers();
        player1.collisionShots(player2.getShots());
        checkCollisionUpgrades();
        checkInputs();
    }

    @Override
    public void onDraw(Graphics2D g2d) {
        drawBackground(g2d);
        drawPlatforms(g2d);
        drawUpgrades(g2d);
        drawPlayer(g2d);
        drawShots(g2d);
        drawEffects(g2d);
    }

    private void drawBackground(Graphics2D g2d) {

        if(screenshake){
            if(screenshakeCounter % 2 == 0){
                g2d.drawImage(background.getImage(), background.getX() - 32,background.getY() - 32,null);
            }else if(screenshakeCounter % 3 == 0){
                g2d.drawImage(background.getImage(), background.getX() + 32,background.getY() + 32,null);
            }else if (screenshakeCounter % 5 == 0){
                g2d.setColor(Color.BLACK);
                g2d.fillRect(0,0,Commons.widht, Commons.height);
            }
            if(screenshakeCounter >= 21)
                screenshake = false;

            screenshakeCounter++;
        }else {
            screenshakeCounter = 0;
            g2d.drawImage(background.getImage(), background.getX(),background.getY(),null);
        }
    }

    private void addUpgrade() {
        newUpgradeCounter++;
        if(newUpgradeCounter >= newUpgradeThresh){
            newUpgradeCounter = 0;
            newUpgradeThresh = random.nextInt((500 - 200) + 1) + 200;
            upgrades.add(new Upgrade(random.nextInt((1000 - 200) + 1) + 200,random.nextInt((408-280) + 1) + 280,random.nextInt(3)));
        }
    }

    private void checkCollisionUpgrades() {
        for(Iterator<Upgrade> it = upgrades.iterator(); it.hasNext();){
            Upgrade upgrade = it.next();
            if(Sprite.intersects(player1, upgrade)){
                it.remove();
                switch (upgrade.getUpgradeType()){
                    case ICE:
                        player1.setUpgradeIceCounter(player1.getUpgradeStoneCounter() + 1);
                        break;
                    case STONE:
                        player1.setUpgradeStoneCounter(player1.getUpgradeStoneCounter() + 1);
                        break;
                    case LIGHTNING:
                        player1.setUpgrageLightningCounter(player1.getUpgrageLightningCounter() + 1);
                        break;
                }
            }
            if (Sprite.intersects(player2, upgrade)){
                it.remove();
                switch (upgrade.getUpgradeType()){
                    case ICE:
                        player2.setUpgradeIceCounter(player1.getUpgradeStoneCounter() + 1);
                        break;
                    case STONE:
                        player2.setUpgradeStoneCounter(player1.getUpgradeStoneCounter() + 1);
                        break;
                    case LIGHTNING:
                        player2.setUpgrageLightningCounter(player1.getUpgrageLightningCounter() + 1);
                        break;
                }
            }
        }
    }

    private void checkPlayers() {

        if(player1.getX() < 0 || player1.getY() < 0 || player1.getX() + player1.getI_width() > Commons.widht || player1.getY() + player1.getI_height() > Commons.height){
            winningMessage = "Player 2 wins";
            GameThread.running = false;
        }

        if(player2.getX() < 0 || player2.getY() < 0 || player2.getX() + player2.getI_width() > Commons.widht || player2.getY() + player2.getI_height() > Commons.height){
            winningMessage = "Player 1 wins";
            GameThread.running = false;
        }

        player1.setCounterPauseShoot(player1.getCounterPauseShoot() + 1);
        player1.moveShots(player2);
        player1.checkJumpState();
        player1.checkKockback();
        player1.checkFalling();

        player2.setCounterPauseShoot(player2.getCounterPauseShoot() + 1);
        player2.moveShots(player1);
        player2.checkJumpState();
        player2.checkKockback();
        player2.checkFalling();
    }

    private void drawUpgrades(Graphics2D g2d) {
        upgrades.forEach(upgrade -> g2d.drawImage(upgrade.getImage(), upgrade.getX(), upgrade.getY(), null));
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
            player1.setX(player1.getX() - player1.getSpeed() / player1.getSpeedDivider());
            player1.setDirection(Direction.LEFT);
            player1.setImageLeft();
        }
        if(keyboardListener.isKeyPressed(KeyEvent.VK_D)){
            player1.setX(player1.getX() + player1.getSpeed() / player1.getSpeedDivider());
            player1.setDirection(Direction.RIGHT);
            player1.setImageRight();

        }
        if(keyboardListener.isKeyPressed(KeyEvent.VK_CONTROL)){
            player1.addShot(new Shot(player1.getX(), player1.getY(), player1.getDirection()));
        }
        if(keyboardListener.isKeyPressed(KeyEvent.VK_W) && !player1.isJumping()){
            player1.jump();
        }
        if(getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_LEFT)) {
            player2.setX(player2.getX() - player2.getSpeed() / player2.getSpeedDivider());
            player2.setDirection(Direction.LEFT);
            player2.setImageLeft();
        }
        if(getGame().getKeyboardListener().isKeyPressed(KeyEvent.VK_RIGHT)) {
            player2.setX(player2.getX() + player2.getSpeed() / player2.getSpeedDivider());
            player2.setDirection(Direction.RIGHT);
            player2.setImageRight();

        }
        if(keyboardListener.isKeyPressed(KeyEvent.VK_UP) && !player2.isJumping()){
            player2.jump();
        }
        if(keyboardListener.isKeyPressed(KeyEvent.VK_SPACE)){
            player2.addShot(new Shot(player2.getX(), player2.getY(), player2.getDirection()));
        }
    }

}
