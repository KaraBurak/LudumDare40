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
    private final int KNOCKBACKTHRESH = 20;
    private Direction knockbackDirection;
    private final int JUMPCOUNTERTHRESH = 30;
    private boolean falling = true;
    private boolean jumping = false;
    private int jumpCount = 0;
    private ArrayList<Shot> shots = new ArrayList<>();

    public Player(int x, int y, String pathToImage) {
        super(x, y);
        ii = new ImageIcon(this.getClass().getResource(pathToImage));
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
                }
            }
        }
        return hasHit;
    }

    public void moveShots(Player player){
        for(Iterator<Shot> it = shots.iterator(); it.hasNext();){
            Shot shot = it.next();
            if(shot.getY() > Commons.height || shot.getY() < 0 || shot.getX() > Commons.widht || shot.getX() < 0){
                it.remove();
            }else if(Sprite.intersects(player,shot)){
                it.remove();
                player.setKnockback(true);
                player.setJumping(false);
                player.setKnockbackDirection(shot.getDirection());
            }else {

                switch (shot.getDirection()) {
                    case UP:
                        shot.setY(shot.getY() - 2);
                        break;
                    case DOWN:
                        shot.setY(shot.getY() + 2);
                        break;
                    case LEFT:
                        shot.setX(shot.getX() - 2);
                        break;
                    case RIGHT:
                        shot.setX(shot.getX() + 2);
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
                y -= 5;
            }else {
                checkFalling();
                if (!falling){
                    jumpCount = jumpCount*2;
                }
                y += 2;
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
            if (y + i_height - 2 < platform.getY() && Sprite.intersects(platform, this)){
                falling = false;
            }
        }
        if (falling){
            y += 1;
        }
    }

    public void checkKockback(){
        if(knockback){
            if(knockbackCount < KNOCKBACKTHRESH*2){
                y -= 3;
            }
            knockbackCount++;
            if(knockbackCount >= KNOCKBACKTHRESH){

                if(knockbackDirection == Direction.LEFT)
                    x--;
                if(knockbackDirection == Direction.RIGHT)
                    x++;
            }
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

}
