import java.awt.*;

/**
 * @author Burak Kara
 */
public abstract class Screen {

    private final Game game;

    public Screen(Game game){
        this.game = game;
    }

    public abstract void onCreate();

    public abstract void onUpdate();

    public abstract void onDraw(Graphics2D g2d);

    public Game getGame(){
        return game;
    }

}
