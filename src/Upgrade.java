import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Burak Kara
 */
public class Upgrade extends Sprite {

    private List<UpgradeType> upgradeTypes = Arrays.asList(UpgradeType.values());
    private UpgradeType upgradeType;


    public UpgradeType getUpgradeType() {
        return upgradeType;
    }

    public Upgrade(int x, int y, int random) {
        super(x, y);
        setType(random);
        ii = new ImageIcon(this.getClass().getResource("resources/upgradeMock.png"));
        image = ii.getImage();

        isVisible = true;
        i_width = image.getWidth(null);
        i_height = image.getHeight(null);
    }

    public void setType(int type) {
        upgradeType = upgradeTypes.get(type);
    }
}
