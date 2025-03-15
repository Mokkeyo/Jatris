package tetris;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * hier wird die Musik gespielt
 * 
 * @author Umut-Can.Bektas
 */

public class MusicPlayer {

	MusicPlayer() {
		String filepath = "Tetris.wav";
		playMusic(filepath);
	}

	public static void playMusic(String sound) {
		File musicPath = new File(sound);
		try {

			if (musicPath.exists()) {
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				clip.open(audioInput);
				clip.loop(Clip.LOOP_CONTINUOUSLY);
				FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
				gainControl.setValue(-30f);
				clip.start();
			} else {
				System.out.println("music couldn't be found");
			}
		} catch (Exception e) {
		}
	}
}