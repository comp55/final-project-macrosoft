import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Sound {
    private String filePath;
    private AdvancedPlayer player;
    private Thread musicThread;
    private boolean loop;
    private boolean playing;

    public Sound(String filePath, boolean loop) {
    	
        this.filePath = filePath;
        this.loop = loop;
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
        playing = true;
    }

    public Boolean isPlaying() {
    	return playing;
    }
    
    public void stop() {
        if (player != null) {
        	loop = false;
        	playing = false;
            player.close();
        }
        if (musicThread != null) {
            musicThread.interrupt();
        }
    }
}
