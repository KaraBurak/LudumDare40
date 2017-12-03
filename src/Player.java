import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Burak Kara
 */
public class Player extends Sprite {
    private Direction direction;
    int pauseShootTime = 100;
    int counterPauseShoot = 101;
    private LoadingShotRectangle loadingShotRectangle;
    private boolean knockback = false;
    private int knockbackCount = 0;
    private final int KNOCKBACKTHRESH = 15;
    private Direction knockbackDirection;
    private final int JUMPCOUNTERTHRESH = 30;
    private boolean falling = true;
    private boolean jumping = false;
    private int jumpCount = 0;
    private int shotSpeed = 5;
    private ArrayList<Shot> shots = new ArrayList<>();

    private int upgradeIceCounter = 0;
    private int upgradeStoneCounter = 0;
    private int upgrageLightningCounter = 0;

    private double fallingSpeed = 2;

    private int hitTimes = 0;

    public int getHitTimes() {
        return hitTimes;
    }

    public void setHitTimes(int hitTimes) {
        this.hitTimes = hitTimes;
    }

    private ImageIcon imageIconRight;
    private ImageIcon imageIconLeft;

    private double speed = 2;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public Player(int x, int y, String pathToImage1, String pathToImage2) {
        super(x, y);
        ii = new ImageIcon(this.getClass().getResource(pathToImage1));

        imageIconRight = new ImageIcon(this.getClass().getResource(pathToImage1));
        imageIconLeft = new ImageIcon(this.getClass().getResource(pathToImage2));

        image = ii.getImage();
        isVisible = true;
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
        loadingShotRectangle = new LoadingShotRectangle(x,y,i_width);
        direction = Direction.RIGHT;
    }

    public int getUpgradeIceCounter() {
        return upgradeIceCounter;
    }

    public void setUpgradeIceCounter(int upgradeIceCounter) {
        this.upgradeIceCounter = upgradeIceCounter;
    }

    public int getUpgradeStoneCounter() {
        return upgradeStoneCounter;
    }

    public void setUpgradeStoneCounter(int upgradeStoneCounter) {
        this.upgradeStoneCounter = upgradeStoneCounter;
    }

    public int getUpgrageLightningCounter() {
        return upgrageLightningCounter;
    }

    public void setUpgrageLightningCounter(int upgrageLightningCounter) {
        this.upgrageLightningCounter = upgrageLightningCounter;
    }

    public void addShot(Shot shot){
        if(counterPauseShoot >= pauseShootTime){
            counterPauseShoot = 0;

            shots.add(shot);
        }
    }

    public boolean collisionShots(ArrayList<Shot> shots){
        boolean hasHit = false;
        for (int i = this.shots.size() - 1; i >= 0; i--) {
            Shot shot = this.shots.get(i);
            for (int j = shots.size() - 1; j >= 0; j--) {
                Shot shot2 = shots.get(j);
                if (intersects(shot, shot2)) {
                    this.shots.remove(shot);
                    shots.remove(shot2);
                    hasHit = true;
                }
            }
        }
        return hasHit;
    }

    public double getFallingSpeed() {
        return fallingSpeed;
    }

    public void setFallingSpeed(double fallingSpeed) {
        this.fallingSpeed = fallingSpeed;
    }

    public void moveShots(Player player){
        for(Iterator<Shot> it = shots.iterator(); it.hasNext();){
            Shot shot = it.next();
            if(shot.getY() > Commons.height || shot.getY() < 0 || shot.getX() > Commons.widht || shot.getX() < 0){
                it.remove();
            }else if(Sprite.intersects(player,shot)){
                it.remove();

                if (upgradeIceCounter > 0){
                    player.setSpeed(player.getSpeed() - 0.2);
                }

                if(upgradeStoneCounter > 0){
                    player.setFallingSpeed(player.getFallingSpeed() + 0.2);
                }

                player.setKnockback(true);
                player.setJumping(false);
                player.setHitTimes(player.getHitTimes() + 1);
                player.setKnockbackDirection(shot.getDirection());
            }else {

                switch (shot.getDirection()) {
                    case LEFT:
                        shot.setX(shot.getX() - shotSpeed);
                        break;
                    case RIGHT:
                        shot.setX(shot.getX() + shotSpeed);
                        break;
                }
            }
        }
    }

    public void jump() {
        if(!falling) {
            jumping = true;
            falling = false;
            jumpCount = 0;
        }
    }

    public void checkJumpState(){
        if(jumping){
            if(jumpCount < JUMPCOUNTERTHRESH){
                y -= 6;
            }else {
                checkFalling();
                if (falling){
                    jumpCount = JUMPCOUNTERTHRESH*2;
                }
                y += fallingSpeed;
            }
            jumpCount++;
            if(jumpCount >= JUMPCOUNTERTHRESH*2){
                jumping = false;
                falling = true;
                jumpCount = 0;
            }
        }
    }

    public void checkFalling() {
        falling = true;
        for (Platform platform : MyScreen.platforms){
            if (y + i_height - 10 < platform.getY() && Sprite.intersects(platform, this)){
                falling = false;
            }
        }
        if (falling){
            y += fallingSpeed;
        }
    }

    public void checkKockback(){
        if(knockback){

            knockbackCount++;
            y -= 2 * ((hitTimes / 4) + 1);

            if(knockbackDirection == Direction.LEFT)
                x -= 2 * ((hitTimes / 2) + 1);
            if(knockbackDirection == Direction.RIGHT)
                x += 2 * ((hitTimes / 2) + 1);

            if(knockbackCount >= KNOCKBACKTHRESH * 2){
                knockback = false;
                falling = true;
                knockbackCount = 0;
            }

        }
    }

    public void setKnockbackDirection(Direction knockbackDirection) {
        this.knockbackDirection = knockbackDirection;
    }

    public void setCounterPauseShoot(int counterPauseShoot) {
        this.counterPauseShoot = counterPauseShoot;
    }

    public int getCounterPauseShoot() {
        return counterPauseShoot;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getPauseShootTime() {
        return pauseShootTime;
    }

    public LoadingShotRectangle getLoadingShotRectangle() {
        return loadingShotRectangle;
    }

    public ArrayList<Shot> getShots() {
        return shots;
    }

    public void setKnockback(boolean knockback) {
        this.knockback = knockback;
    }

    public void setImageLeft() {
        image = imageIconLeft.getImage();
    }

    public void setImageRight() {
        image = imageIconRight.getImage();
    }

}
