import javax.swing.*;
import java.util.ArrayList;

/**
 * @author Burak Kara
 */
public class Player extends Sprite {

    private Direction direction;

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
}
