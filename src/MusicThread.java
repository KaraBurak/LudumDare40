import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * @author Burak Kara
 */
public class MusicThread implements Runnable {

    @Override
    public void run() {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(this.getClass().getResource("resources/backgroundMusic.wav"));
            Clip clip = AudioSystem.getClip();

            clip.open(ais);
            clip.start();

            while (!clip.isRunning())
                Thread.sleep(10);
            while (clip.isRunning())
                Thread.sleep(10);

            clip.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
