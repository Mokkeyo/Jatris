/**
 * 
 */
package tetris;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Frame extends JFrame implements Runnable {

	/**
	 * Fenster wird hier erstellt
	 * 
	 * @author Umut-Can.Bektas
	 */
	private static final long serialVersionUID = 6821178782253941478L;
	final int FPS = 60;
	Thread gameThread;
	
	//0 = Solo 1 = Vs
	public static int mode = 0;
	
	public static boolean showGrid = true;
	public static int level = 0;
	public static int HighScore = 0;

	public static boolean saveScore = false;

	public static boolean changePanel = false;
	boolean stop = false;

	public Frame() {
		super("JATRIS by Umut-Can Bektas");
		loadData();
		setBounds(100, 100, 1280, 720);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		ImageIcon img = new ImageIcon("icon.png");
		setIconImage(img.getImage());
		Menu m = new Menu();
		m = new Menu();
		setLocationRelativeTo(null);
		add(m);
		setVisible(true);
		// enables button Inputs
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		gameThread = new Thread(this);
		// calls the run Method
		gameThread.start();
	}

	public void run() {
		double drawInterval = 1000000000 / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;

		while (gameThread != null) {

			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if (delta >= 1) {
				delta--;

				if (changePanel) {
					changePanel = false;
					loadLevel();
				}
				if (saveScore) {
					saveScore = false;
					saveData();
				}
			}

		}
	}

	public void loadLevel() {
		getContentPane().removeAll();
		switch (level) {
		case 0:
			Menu m = new Menu();
			getContentPane().add(m);
			break;
		case 1:
			Panel p = new Panel();
			getContentPane().add(p);
			p.launchGame();
			break;
		}
		revalidate();
		pack();
	}

	public void loadData() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("Score.txt"));
			HighScore = Integer.parseInt(br.readLine());
			showGrid = Boolean.parseBoolean(br.readLine());
			br.close();
		} catch (Exception e) {

		}
	}

	public void saveData() {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("Score.txt"));
			writer.write(HighScore + "");
			writer.newLine();
			writer.write(showGrid + "");
			writer.close();
		} catch (Exception e) {

		}
	}

}
