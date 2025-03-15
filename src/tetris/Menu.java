package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class Menu extends JPanel implements ActionListener, Runnable {

	/**
	 * Das Menu was man sieht, wenn man das Spiel staret
	 * 
	 * @author Umut-Can.Bektas
	 */
	private static final long serialVersionUID = 1389859657186841738L;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 720;

	Thread gameThread;
	final int FPS = 60;

	Button soloModeButton;
	Button vsModeButton;
	Button gridButton;
	Button exitButton;

	public Menu() {
		super();
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setLayout(null);
		this.setBackground(Color.black);
		// implements KeyListener
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		soloModeButton = new Button("SOLO", WIDTH / 4, 250);
		soloModeButton.addActionListener(this);
		add(soloModeButton);

		vsModeButton = new Button("VS (in work)", WIDTH / 4, 350);
		vsModeButton.addActionListener(this);
		add(vsModeButton);
		
		if (tetris.Frame.showGrid == true) {
			gridButton = new Button("GRID: ON", WIDTH / 4, 450);
		} else {
			gridButton = new Button("GRID: OFF", WIDTH / 4, 450);
		}
		gridButton.addActionListener(this);
		add(gridButton);
		
		exitButton = new Button("EXIT", WIDTH / 4, 550);
		exitButton.addActionListener(this);
		add(exitButton);


		gameThread = new Thread(this);
		gameThread.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Converts the Graphics into Graphics2D
		Graphics2D g2D = (Graphics2D) g;
		draw(g2D);

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
				repaint();
			}
		}
	}

	public void draw(Graphics2D g2D) {	
		int AreaX = 920;
		int AreaY = 430;
		if (tetris.Frame.showGrid == true) {
			for (int x = AreaX; x < AreaX + 24 * 3; x += 6)
				for (int y = AreaY; y < AreaY + 40 * 3; y += 6) {
					g2D.setColor(Color.darkGray);
					g2D.drawRect(x, y, 6, 6);
				}
		}
		g2D.setColor(Color.blue);
		g2D.drawRect(AreaX, AreaY, 24 * 3, 40 * 3);
		g2D.setFont(new Font("Comic Sans", Font.BOLD, 150));
		g2D.setColor(Color.red);
		g2D.drawString("JATRIS", 350, 180);
		g2D.setColor(Color.yellow);
		g2D.setFont(new Font("Comic Sans", Font.BOLD, 30));
		g2D.drawString("by Umut-Can Bektas", 470, 220);
		g2D.setColor(Color.white);
		g2D.setFont(new Font("Comic Sans", Font.BOLD, 30));
		g2D.drawString("TOPSCORE: " + tetris.Frame.HighScore, 10, 30);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == soloModeButton) {
			
			tetris.Frame.level = 1;
			tetris.Frame.mode = 0;
			tetris.Frame.changePanel = true;
		}else if(e.getSource() == vsModeButton) {
			tetris.Frame.level = 1;
			tetris.Frame.mode = 1;
			tetris.Frame.changePanel = true;
			
		}else if (e.getSource() == exitButton) {
			System.exit(0);
		} else if (e.getSource() == gridButton) {
			tetris.Frame.saveScore = true;
			if (tetris.Frame.showGrid == false) {
				gridButton.setText("GRID:ON");
				tetris.Frame.showGrid = true;
			} else {
				gridButton.setText("GRID:OFF");
				tetris.Frame.showGrid = false;
			}
		}

	}
}
