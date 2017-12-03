/**
 * @author Burak Kara
 */
public class LoadingShotRectangle extends Sprite {

    int width;

    public LoadingShotRectangle(int x, int y, int width) {
        super(x, y);
        this.width = width;
    }

    public double getPercentage(double currentCounter, double pause){
        if(currentCounter == 0)
            return 0;
        if(pause <= currentCounter)
            return 1;

        return currentCounter / pause;
    }

    public double setRectangleWidth(double percentage){
        return width * percentage;
    }

    public int getWidth() {
        return width;
    }
}
