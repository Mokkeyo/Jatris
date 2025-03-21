/**
 * 
 */
package tetris;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Händelt Tastaturen Inputs
 * 
 * @author Umut-Can.Bektas
 */
public class KeyHandler implements KeyListener {

	public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed, holdPressed, retryPressed,
			exitPressed;

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
			upPressed = true;
		}
		if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
			leftPressed = true;
		}
		if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
			downPressed = true;
		}
		if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
			rightPressed = true;
		}

		if (code == KeyEvent.VK_SHIFT) {
			holdPressed = true;
		}

		if (code == KeyEvent.VK_SPACE) {
			retryPressed = true;
		}

		if (code == KeyEvent.VK_ENTER) {
			exitPressed = true;
		}

		if (code == KeyEvent.VK_ESCAPE) {
			if (pausePressed) {
				pausePressed = false;
			} else {
				pausePressed = true;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

}
