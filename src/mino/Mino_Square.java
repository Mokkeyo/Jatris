package mino;

import java.awt.Color;

/**
 * Die Mino "Square" wird mit hilfe der Mutterklasse Mino definiert
 * 
 * @author Umut-Can.Bektas
 */

public class Mino_Square extends Mino {

	public Mino_Square() {
		create(Color.orange);
	}

	public void setXY(int x, int y) {
		// o o
		// o o
		b[0].x = x;
		b[0].y = y;
		b[1].x = b[0].x - Block.SIZE;
		b[1].y = b[0].y;
		b[2].x = b[0].x;
		b[2].y = b[0].y - Block.SIZE;
		b[3].x = b[0].x - Block.SIZE;
		b[3].y = b[0].y - Block.SIZE;
	}

	public void getDirection1() {
	}

	public void getDirection2() {
	}

	public void getDirection3() {
	}

	public void getDirection4() {
	}
}