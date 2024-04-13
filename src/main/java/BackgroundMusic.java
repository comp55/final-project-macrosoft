import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BackgroundMusic {
    private String filePath;
    private AdvancedPlayer player;
    private Thread musicThread;

    public BackgroundMusic(String filePath) {
        this.filePath = filePath;
    }

    public void play() {
        musicThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fileInputStream = new FileInputStream(filePath);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                    player = new AdvancedPlayer(bufferedInputStream);
                    player.play();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        });
        musicThread.start();
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
        if (musicThread != null) {
            musicThread.interrupt();
        }
    }
}
