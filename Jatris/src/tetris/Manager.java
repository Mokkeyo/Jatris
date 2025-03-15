/**
 * 
 */
package tetris;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import mino.Block;
import mino.Mino;
import mino.Mino_Bar;
import mino.Mino_L1;
import mino.Mino_L2;
import mino.Mino_Square;
import mino.Mino_T;
import mino.Mino_Z1;
import mino.Mino_Z2;

/**
 * Der Solo Modus wird hier gestartet
 * 
 * @author Umut-Can.Bektas
 */
public class Manager {

	final int WIDTH = 360;
	final int HEIGHT = 600;
	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;

	boolean save = true;
	int oldHighscore = tetris.Frame.HighScore;

	// Mino
	Mino currentMino;
	int MINO_START_X;
	int MINO_START_Y;
	Mino nextMino;
	int NEXTMINO_X;
	int NEXTMINO_Y;
	Mino holdMino;
	int HOLDMINO_X;
	int HOLDMINO_Y;

	int[] dropSpeed = { 48, 43, 38, 33, 28, 23, 18, 13, 8, 6, 5, 5, 5, 4, 4, 4, 3, 3, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
			1 };

	public static ArrayList<Block> staticBlocks = new ArrayList<>();

	// Others
	public static int dropInterval; // drop speed in frames
	public static boolean gameOver;
	boolean canHold = true;
	boolean[] Array = { false, false, false, false, false, false, false };

	// Score
	int level = 1;
	int lines;
	public static int score;

	public Manager() {
		// sets DropSpeed
		if (level - 1 < dropSpeed.length) {
			dropInterval = dropSpeed[level - 1];
		} else {
			dropInterval = 1;
		}
		// Sets the Positions of the Area and sets its Size
		left_x = (Panel.WIDTH / 2) - (WIDTH / 2);
		right_x = left_x + WIDTH;
		top_y = 50;
		bottom_y = top_y + HEIGHT;

		MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
		MINO_START_Y = top_y + Block.SIZE;

		// spawns the block at the Position
		currentMino = pickMino();
		ChangeCurrentMinoPosition();
		currentMino.setXY(MINO_START_X, MINO_START_Y);
		nextMino = pickMino();
		nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
	}

	private Mino pickMino() {
		Mino mino = null;
		int i = new Random().nextInt(7);

		int count = 0;
		// checks if all blocks where Used
		for (int k = 0; k < Array.length; k++) {
			if (Array[k] == true) {
				count++;
			}
			// if blocks are all used, reset all blocks to not used
			if (count == 7) {
				for (int l = 0; l < Array.length; l++) {
					Array[l] = false;
				}
			}
		}
		// checks if the picked block was already used
		for (int j = 0; j < Array.length; j++) {
			if (Array[i] == false) {
				Array[i] = true;
				break;

				// changes the block to one which wasnt used
			} else {
				if (i != 6) {
					i++;
				} else {
					i = 0;
				}
			}
		}

		final Mino[] minoBlock = { new Mino_L1(), new Mino_L2(), new Mino_Square(), new Mino_Bar(), new Mino_T(),
				new Mino_Z1(), new Mino_Z2() };
		mino = minoBlock[i];
		if (i == 2) {
			NEXTMINO_X = right_x + 200;
			NEXTMINO_Y = top_y + 110;
		} else if (i == 3) {
			NEXTMINO_X = right_x + 170;
			NEXTMINO_Y = top_y + 110;
		} else {
			NEXTMINO_X = right_x + 185;
			NEXTMINO_Y = top_y + 110;
		}

		return mino;
	}

	public void update() {
		if (KeyHandler.holdPressed == true) {
			KeyHandler.holdPressed = false;
			if (canHold == true) {
				canHold = false;
				if (holdMino == null) {
					// Code to Hold the Mino
					holdMino = currentMino;
					// changes the position of the Hold Mino
					changeHoldMinoPosition();
					holdMino.setXY(HOLDMINO_X, HOLDMINO_Y);
					currentMino = nextMino;
					ChangeCurrentMinoPosition();
					currentMino.setXY(MINO_START_X, MINO_START_Y);
					nextMino = pickMino();
					nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
				} else {
					// Change current Minio with Hold Mino
					Mino temporaryMino;
					temporaryMino = holdMino;
					holdMino = currentMino;
					// changes the position of the Hold Mino
					changeHoldMinoPosition();
					holdMino.setXY(HOLDMINO_X, HOLDMINO_Y);
					currentMino = temporaryMino;
					ChangeCurrentMinoPosition();
					currentMino.setXY(MINO_START_X, MINO_START_Y);
				}
			}
		}
		// check if current Mino is active
		if (currentMino.active == false) {
			// if mino is not active, put it into static blocks
			staticBlocks.add(currentMino.b[0]);
			staticBlocks.add(currentMino.b[1]);
			staticBlocks.add(currentMino.b[2]);
			staticBlocks.add(currentMino.b[3]);

			if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
				// curentMino immediatly collided and couldnt move.
				// xy are the same with currentMinos
				gameOver = true;
			}
			currentMino.deactivating = false;
			// replace currentMino with nextMino
			currentMino = nextMino;
			ChangeCurrentMinoPosition();
			currentMino.setXY(MINO_START_X, MINO_START_Y);
			nextMino = pickMino();
			nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
			canHold = true;
			checkDelete();
		} else {
			currentMino.update();
		}
	}

	private void changeHoldMinoPosition() {
		if (holdMino.toString().startsWith("mino.Mino_Bar")) {
			HOLDMINO_X = left_x - 230;
			HOLDMINO_Y = top_y + 110;
		} else if (holdMino.toString().startsWith("mino.Mino_Square")) {
			HOLDMINO_X = left_x - 200;
			HOLDMINO_Y = top_y + 110;
		} else {
			HOLDMINO_X = left_x - 215;
			HOLDMINO_Y = top_y + 110;
		}
	}

	private void ChangeCurrentMinoPosition() {
		if (currentMino.toString().startsWith("mino.Mino_Bar")) {
			MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
			MINO_START_Y = top_y;
		} else {
			MINO_START_X = left_x + (WIDTH / 2) - Block.SIZE;
			MINO_START_Y = top_y + Block.SIZE;
		}
	}

	private void checkDelete() {

		int x = left_x;
		int y = top_y;
		int blockCount = 0;
		int lineCount = 0;

		while (x < right_x && y < bottom_y) {
			for (int i = 0; i < staticBlocks.size(); i++) {
				if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
					blockCount++;
				}
			}
			x += Block.SIZE;

			if (x == right_x) {
				// if blockCount hits 12, current y line is filled with blocks
				// delete the line
				if (blockCount == 12) {
					for (int i = staticBlocks.size() - 1; i > -1; i--) {
						// remove all blocks in the current y line
						if (staticBlocks.get(i).y == y) {
							staticBlocks.remove(i);
						}
					}
					lineCount++;
					lines++;
					// Drop Speed
					// if line score hits a certain number, increase the drop speed
					// 1 is the fastest
					if (lines % 10 == 0) {
						level++;
						if (level - 1 < dropSpeed.length)
							dropInterval = dropSpeed[level - 1];
					}
					// line got deleted. Slides down all blocks above it
					for (int i = 0; i < staticBlocks.size(); i++) {
						// if block is above curernt y, move it down by the block size
						if (staticBlocks.get(i).y < y) {
							staticBlocks.get(i).y += Block.SIZE;
						}
					}
				}
				blockCount = 0;
				x = left_x;
				y += Block.SIZE;
			}
		}

		if (lineCount > 0) {
			// sets the score
			final int[] Score = { 40, 100, 300, 1200 };
			score += Score[lineCount - 1] * level;
			if (score > tetris.Frame.HighScore) {
				tetris.Frame.HighScore = score;
			}
		}
	}

	public void restart() {
		oldHighscore = tetris.Frame.HighScore;
		KeyHandler.downPressed = false;
		KeyHandler.leftPressed = false;
		KeyHandler.rightPressed = false;
		KeyHandler.upPressed = false;
		KeyHandler.holdPressed = false;
		gameOver = false;
		level = 1;
		score = 0;
		canHold = false;
		holdMino = null;
		currentMino = null;
		nextMino = null;
		lines = 0;

		dropInterval = 48;
		staticBlocks = new ArrayList<>();

//		NEXTMINO_X = right_x + 200;
//		NEXTMINO_Y = top_y + 110;

//		HOLDMINO_X = left_x - 200;
//		HOLDMINO_Y = top_y + 110;

		for (int i = 0; i < Array.length; i++) {
			Array[i] = false;
		}

		currentMino = pickMino();
		currentMino.setXY(MINO_START_X, MINO_START_Y);
		nextMino = pickMino();
		nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
	}

	public void draw(Graphics2D g2D) {
		// draws the grid for the Play Area
		if (tetris.Frame.showGrid == true) {
			g2D.setColor(Color.darkGray);
			for (int x = left_x; x < right_x; x += Block.SIZE)
				for (int y = top_y; y < bottom_y; y += Block.SIZE) {
					g2D.drawRect(x, y, Block.SIZE, Block.SIZE);
				}
		}
		// Draws the Play Area
		g2D.setColor(Color.blue);
		g2D.setStroke(new BasicStroke(4f));
		g2D.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);

		// Draws the Box to show which block is next
		int x = right_x + 100;
		int y = bottom_y - 600;
		g2D.setColor(Color.orange);
		g2D.drawRect(x, y, 200, 200);
		g2D.setFont(g2D.getFont().deriveFont(30f));
		g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2D.drawString("NEXT", x + 60, y + 60);

		// Draw Hold
		int x2 = left_x - 300;
		int y2 = bottom_y - 600;
		g2D.setColor(Color.red);
		g2D.drawRect(x2, y2, 200, 200);
		g2D.drawString("HOLD", x2 + 60, y2 + 60);

		// Draw Controlls
		g2D.setFont(g2D.getFont().deriveFont(25f));
		g2D.setColor(Color.white);
		g2D.drawRect(x2 - 80, y2 + 300, 350, 300);
		int x3 = x2 - 55;
		int y3 = y2 = top_y + 355;
		g2D.drawString("LEFT: A / LEFT", x3, y3);
		y3 += 40;
		g2D.drawString("RIGHT: D / RIGHT", x3, y3);
		y3 += 40;
		g2D.drawString("ROTATE: W / UP", x3, y3);
		y3 += 40;
		g2D.drawString("FAST DROP: W / DOWN", x3, y3);
		y3 += 40;
		g2D.drawString("HOLD: SHIFT:", x3, y3);
		y3 += 40;
		g2D.drawString("PAUSE: ESCAPE", x3, y3);
		y3 += 40;

		// Draw Score
		g2D.setColor(Color.magenta);
		g2D.setFont(g2D.getFont().deriveFont(30f));
		g2D.drawRect(x - 70, top_y + 300, 350, 300);
		x += 10;
		y = top_y + 360;
		g2D.drawString("LEVEL: " + level, x - 70, y);
		y += 70;
		g2D.drawString("LINES: " + lines, x - 70, y);
		y += 70;
		g2D.drawString("TOPSCORE: " + tetris.Frame.HighScore, x - 70, y);
		y += 70;
		g2D.drawString("SCORE: " + score, x - 70, y);

		// Draw currentMino
		if (currentMino != null) {
			currentMino.draw(g2D);
		}
		// Draw holdMino
		if (holdMino != null) {
			holdMino.draw(g2D);
		}
		// Draw nextMino
		if (nextMino != null) {
			nextMino.draw(g2D);
		}

		// Draw Static Blocks
		for (int i = 0; i < staticBlocks.size(); i++) {
			staticBlocks.get(i).draw(g2D);
		}

		if (gameOver) {
			x = left_x + 25;
			y = top_y + 320;
			g2D.setColor(Color.black);
			g2D.setStroke(new BasicStroke(4f));
			g2D.fillRect(left_x - 1, top_y - 2, WIDTH + 3, HEIGHT + 4);
			g2D.setColor(Color.red);
			g2D.setFont(g2D.getFont().deriveFont(50f));
			g2D.drawString("GAME OVER", x, y);
			g2D.setColor(Color.white);
			g2D.setFont(g2D.getFont().deriveFont(25f));
			g2D.drawString("PRESS [SPACE] TO RETRY", x - 5, y + 60);
			g2D.drawString("  PRESS [ENTER] TO EXIT", x - 5, y + 90);
			if (oldHighscore < tetris.Frame.HighScore) {
				g2D.setColor(Color.yellow);
				g2D.setFont(g2D.getFont().deriveFont(40f));
				g2D.drawString("NEW TOPSCORE", x - 10, y - 250);
			}
			if (save) {
				save = false;
				tetris.Frame.saveScore = true;
			}
		} else if (KeyHandler.pausePressed) {
			x = left_x + 70;
			y = top_y + 320;
			g2D.setColor(Color.black);
			g2D.setStroke(new BasicStroke(4f));
			g2D.fillRect(left_x - 1, top_y - 2, WIDTH + 3, HEIGHT + 4);
			g2D.setColor(Color.yellow);
			g2D.setFont(g2D.getFont().deriveFont(50f));
			g2D.drawString("PAUSED", x, y);
			x = left_x + 25;
			y = top_y + 320;
			g2D.setColor(Color.white);
			g2D.setFont(g2D.getFont().deriveFont(25f));
			g2D.drawString("  PRESS [ESC] TO CONTINUE", x - 25, y + 60);
		}
	}
}
