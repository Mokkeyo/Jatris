/**
 * 
 */
package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * Der Panel um das Spiel zu starten wird erzeugt
 * 
 * @author Umut-Can.Bektas
 */
public class Panel extends JPanel implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7179300168021162213L;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 720;
	final int FPS = 60;
	Thread gameThread;
	Manager manager;

	VsMode vsMode;

	public Panel() {
		super();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(null);
		this.setBackground(Color.black);
	}

//Start Game
	public void launchGame() {
//	this.addKeyListener(new KeyHandler());
//	this.setFocusable(true);
		if (tetris.Frame.mode == 0) {
			manager = new Manager();
		} else {
			vsMode = new VsMode();
		}
		// Allows Multytasking
		gameThread = new Thread(this);
		// calls the run Method
		gameThread.start();
	}

//loop the Game
	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {
			// Calls the update and repaint function 60 Times a second
			currentTime = System.nanoTime();

			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				if (KeyHandler.retryPressed && Manager.gameOver) {
					KeyHandler.retryPressed = false;
					if (tetris.Frame.mode == 0) {
						manager.restart();
					} else {
						vsMode.restart();
					}
				}
				if (KeyHandler.exitPressed && Manager.gameOver) {
					gameThread = null;

					if (tetris.Frame.mode == 0) {
						manager.restart();
					} else {
						vsMode.restart();
					}

					KeyHandler.exitPressed = false;
					tetris.Frame.level = 0;
					tetris.Frame.changePanel = true;
				}
				update();
				// repaint() Calls the paintComponent() Function
				repaint();
				delta--;
			}
		}
	}

//Update Position
	void update() {
		if (KeyHandler.pausePressed == false && Manager.gameOver == false) {

			if (tetris.Frame.mode == 0) {
				manager.update();
			} else {
				vsMode.update();
			}

		}

	}

//Draw
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Converts the Graphics into Graphics2D
		Graphics2D g2D = (Graphics2D) g;
		if (manager != null) {
			manager.draw(g2D);
		}
		if (vsMode != null) {
			vsMode.draw(g2D);
		}

	}

}
