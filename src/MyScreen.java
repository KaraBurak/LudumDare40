import java.awt.*;

/**
 * @author Burak Kara
 */
public class MyScreen extends Screen{

    private final Player player;

    public MyScreen(Game game, Player player) {
        super(game);
        this.player = player;
    }

    @Override
    public void onCreate() {
        System.out.println("Creating");
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onDraw(Graphics2D g2d) {
        g2d.drawImage(player.getImage(),player.getX(), player.getY(), null);
    }
}
