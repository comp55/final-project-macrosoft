import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BackgroundMusic {
    private String filePath;
    private AdvancedPlayer player;
    private Thread musicThread;
    private boolean loop;

    public BackgroundMusic(String filePath, boolean loop) {
    	this.loop = loop;
        this.filePath = filePath;
    }

    public void play() {
        musicThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                	do {
                		FileInputStream fileInputStream = new FileInputStream(filePath);
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                        player = new AdvancedPlayer(bufferedInputStream);
                        player.play();
                	} while (loop);
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
        	loop = false;
            player.close();
        }
        if (musicThread != null) {
            musicThread.interrupt();
        }
    }
}
