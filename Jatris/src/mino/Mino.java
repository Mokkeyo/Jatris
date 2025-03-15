/**
 * 
 */
package mino;

import java.awt.Color;
import java.awt.Graphics2D;

import tetris.Manager;

/**
 * Mutter Klasse für die Tetris Bausteine (Minos)
 * @author Umut-Can.Bektas
 */
public class Mino {

	public Block b[] = new Block[4];
	public Block tempB[] = new Block[4];
	int autoDropCounter = 0;
	public int direction = 1; // there are 4 directions(1/2/3/4)
	boolean leftCollision, rightCollision, bottomCollision, topCollision;
	public boolean active = true;
	public boolean deactivating;
	int deactivateCounter = 0;

	public void create(Color c) {
		b[0] = new Block(c);
		b[1] = new Block(c);
		b[2] = new Block(c);
		b[3] = new Block(c);
		tempB[0] = new Block(c);
		tempB[1] = new Block(c);
		tempB[2] = new Block(c);
		tempB[3] = new Block(c);
	}

	public void setXY(int x, int y) {
	}

	public void updateXY(int direction) {

		checkRotationCollision();
		if (!leftCollision && !rightCollision && !bottomCollision && !topCollision) {

			this.direction = direction;
			for (int i = 0; i < b.length; i++) {
				b[i].x = tempB[i].x;
				b[i].y = tempB[i].y;
			}
		}
	}

	public void getDirection1() {
	}

	public void getDirection2() {
	}

	public void getDirection3() {
	}

	public void getDirection4() {
	}

	public void checkMovementCollision() {

		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;

		checkStaticBlockCollision();

		for (int i = 0; i < b.length; i++) {
			if (b[i].x == tetris.Manager.left_x) {
				leftCollision = true;
			}
			if (b[i].x + Block.SIZE == tetris.Manager.right_x) {
				rightCollision = true;
			}
			if (b[i].y + Block.SIZE == tetris.Manager.bottom_y) {
				bottomCollision = true;
			}
		}
	}

	public void checkRotationCollision() {

		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;
		topCollision = false;

		// checksStaticBlocksCollision
		for (int i = 0; i < tetris.Manager.staticBlocks.size(); i++) {
			int targetX = tetris.Manager.staticBlocks.get(i).x;
			int targetY = tetris.Manager.staticBlocks.get(i).y;

			for (int j = 0; j < b.length; j++) {
				if (tempB[j].y == targetY && tempB[j].x == targetX) {
					bottomCollision = true;
				}
				if (tempB[j].y == targetY && tempB[j].x == targetX) {
					leftCollision = true;
				}
				if (tempB[j].y == targetY && tempB[j].x == targetX) {
					rightCollision = true;
				}
			}
		}
		// checks borderCollision
		for (int i = 0; i < b.length; i++) {
			if (tempB[i].x < tetris.Manager.left_x) {
				leftCollision = true;
			}
			if (tempB[i].x + Block.SIZE > tetris.Manager.right_x) {
				rightCollision = true;
			}
			if (tempB[i].y + Block.SIZE > tetris.Manager.bottom_y) {
				bottomCollision = true;
			}
			if (tempB[i].y < tetris.Manager.top_y) {
				topCollision = true;
			}
		}
	}

	private void checkStaticBlockCollision() {

		for (int i = 0; i < tetris.Manager.staticBlocks.size(); i++) {
			int targetX = tetris.Manager.staticBlocks.get(i).x;
			int targetY = tetris.Manager.staticBlocks.get(i).y;

			for (int j = 0; j < b.length; j++) {
				if (b[j].y + Block.SIZE == targetY && b[j].x == targetX) {
					bottomCollision = true;
				}
				if (b[j].y == targetY && b[j].x - Block.SIZE == targetX) {
					leftCollision = true;
				}
				if (b[j].y == targetY && b[j].x + Block.SIZE == targetX) {
					rightCollision = true;
				}
			}
		}
	}

	public void update() {
		if (deactivating) {
			deactivating();
		}

		if (tetris.KeyHandler.upPressed) {
			switch (direction) {
			case 1:
				getDirection2();
				break;
			case 2:
				getDirection3();
				break;
			case 3:
				getDirection4();
				break;
			case 4:
				getDirection1();
				break;
			}
			tetris.KeyHandler.upPressed = false;
		}

		checkMovementCollision();

		if (tetris.KeyHandler.downPressed) {
			if (bottomCollision == false) {
				for (int i = 0; i < b.length; i++) {
					Manager.score += 1;
					b[i].y += Block.SIZE;
				}
				// resets autodrop timer
				autoDropCounter = 0;
			}
			tetris.KeyHandler.downPressed = false;
		}

		if (tetris.KeyHandler.leftPressed) {
			if (leftCollision == false) {
				for (int i = 0; i < b.length; i++) {
					b[i].x -= Block.SIZE;
				}
			}
			tetris.KeyHandler.leftPressed = false;
		}

		if (tetris.KeyHandler.rightPressed) {
			if (rightCollision == false) {
				for (int i = 0; i < b.length; i++) {
					b[i].x += Block.SIZE;
				}
			}
			tetris.KeyHandler.rightPressed = false;
		}

		if (bottomCollision) {
			deactivating = true;
		} else {
			deactivateCounter = 0;
			autoDropCounter++;
			if (autoDropCounter == Manager.dropInterval) {
				// mino goes down
				for (int i = 0; i < b.length; i++) {
					b[i].y += Block.SIZE;
				}
				autoDropCounter = 0;
			}
		}
	}

	private void deactivating() {
		deactivateCounter++;

		if (deactivateCounter == 45) {
			deactivateCounter = 0;
			checkMovementCollision();
			if (bottomCollision) {
				active = false;
			}
		}
	}

	public void draw(Graphics2D g2D) {

		int margin = 2;
		g2D.setColor(b[0].c);
		g2D.fillRect(b[0].x + margin, b[0].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
		g2D.fillRect(b[1].x + margin, b[1].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
		g2D.fillRect(b[2].x + margin, b[2].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
		g2D.fillRect(b[3].x + margin, b[3].y + margin, Block.SIZE - (margin * 2), Block.SIZE - (margin * 2));
	}
}
