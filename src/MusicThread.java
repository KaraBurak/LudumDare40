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
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
