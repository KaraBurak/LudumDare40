import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Burak Kara
 */
public class Player extends Sprite {

    private Direction direction;

    private final int JUMPCOUNTERTHRESH = 30;

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    private boolean falling = true;

    private boolean jumping = false;
    private int jumpCount = 0;

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    private final String pathToImage = "resources/playerMock.png";

    private ArrayList<Shot> shots = new ArrayList<>();

    public Player(int x, int y) {
        super(x, y);
        ii = new ImageIcon(this.getClass().getResource(pathToImage));
        image = ii.getImage();
        isVisible = true;
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
    }

    public void addShot(Shot shot){
        shots.add(shot);
    }

    public void moveShots(){
        for(Shot shot : shots){
            switch (shot.getDirection()){
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

    public ArrayList<Shot> getShots() {
        return shots;
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

}
