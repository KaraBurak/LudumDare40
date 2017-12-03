import javax.swing.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Burak Kara
 */
public class Player extends Sprite {
    private Direction direction;
    int pauseShootTime = 70;
    int counterPauseShoot = 71;
    private LoadingShotRectangle loadingShotRectangle;
    private boolean knockback = false;
    private int knockbackCount = 0;
    private final int KNOCKBACKTHRESH = 15;
    private Direction knockbackDirection;
    private final int JUMPCOUNTERTHRESH = 30;
    private boolean falling = true;
    private boolean jumping = false;
    private int jumpCount = 0;
    private int shotSpeed = 8;
    private ArrayList<Shot> shots = new ArrayList<>();
    private int speedDivider = 1;
    private double knockbackMultiplicator = 1;
    private int upgradeIceCounter = 0;
    private int upgradeStoneCounter = 0;
    private int upgrageLightningCounter = 0;
    private int fallingSpeed = 2;
    private int hitTimes = 0;
    private ImageIcon imageIconRight;
    private ImageIcon imageIconLeft;
    private int speed = 7;

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
                    MyScreen.screenshake = true;
                }
            }
        }
        return hasHit;
    }

    public void setSpeedDivider(int speedDivider) {
        this.speedDivider = speedDivider;
    }

    public void moveShots(Player player){
        for(Iterator<Shot> it = shots.iterator(); it.hasNext();){
            Shot shot = it.next();
            if(shot.getY() > Commons.height || shot.getY() < 0 || shot.getX() > Commons.widht || shot.getX() < 0){
                it.remove();
            }else if(Sprite.intersects(player,shot)){
                it.remove();

                if (upgradeIceCounter > 0){
                    player.setSpeed((int) (player.getSpeed() - 0.5));
                }

                if(upgradeStoneCounter > 0){
                    player.setSpeedDivider((int) (player.getSpeedDivider() + 0.5));
                }

                player.setKnockback(true);
                player.setKnockbackCount(0);
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
            falling = false;
            if(jumpCount < JUMPCOUNTERTHRESH){
                y -= 4;
            }else {
                checkFalling();
                if (falling){
                    jumpCount = JUMPCOUNTERTHRESH;
                }
                y += fallingSpeed;
            }
            jumpCount++;
            if(jumpCount >= JUMPCOUNTERTHRESH){
                jumping = false;
                falling = true;
                jumpCount = 0;
            }
        }
    }

    public void checkFalling() {
        falling = true;
        for (Platform platform : MyScreen.platforms){
            if (y + i_height - 4 < platform.getY() && Sprite.intersects(platform, this)){
                falling = false;
            }
        }
        if(jumping)
            falling = false;

        speedDivider = 1;
        if (falling){
            speedDivider = 2;
            y += fallingSpeed;
        }
    }

    public void checkKockback(){
        if(knockback){
            falling = true;
            knockbackCount++;
            y -= 2 * ((hitTimes / 3) + 1) * knockbackMultiplicator;

            if(knockbackDirection == Direction.LEFT)
                x -= 2 * ((hitTimes / 1.5) + 1) * knockbackMultiplicator;
            if(knockbackDirection == Direction.RIGHT)
                x += 2 * ((hitTimes / 1.5) + 1) * knockbackMultiplicator;

            if(knockbackCount >= KNOCKBACKTHRESH * 2){
                knockback = false;
                falling = true;
                knockbackCount = 0;
            }

        }
    }

    public void setKnockbackCount(int knockbackCount) {
        this.knockbackCount = knockbackCount;
    }

    public int getHitTimes() {
        return hitTimes;
    }

    public void setHitTimes(int hitTimes) {
        this.hitTimes = hitTimes;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getFallingSpeed() {
        return fallingSpeed;
    }

    public void setFallingSpeed(int fallingSpeed) {
        this.fallingSpeed = fallingSpeed;
    }

    public int getSpeedDivider() {
        return speedDivider;
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


    public void setUpgradeIceCounter(int upgradeIceCounter) {
        this.upgradeIceCounter = upgradeIceCounter;
        speed -= this.upgradeIceCounter - 1;
    }

    public int getUpgradeStoneCounter() {
        return upgradeStoneCounter;
    }

    public void setUpgradeStoneCounter(int upgradeStoneCounter) {
        this.upgradeStoneCounter = upgradeStoneCounter;
        speedDivider += this.upgradeStoneCounter;
    }

    public int getUpgrageLightningCounter() {
        return upgrageLightningCounter;
    }

    public void setUpgrageLightningCounter(int upgrageLightningCounter) {
        this.upgrageLightningCounter = upgrageLightningCounter;
        knockbackMultiplicator += 0.3;
        if(this.upgrageLightningCounter - 1 == 0)
            speed += 1;
    }

}
