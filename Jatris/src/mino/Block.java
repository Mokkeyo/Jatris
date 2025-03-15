/**
 * 
 */
package mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 * Die Größe und Farbe der Blöcke aus denn die Minos(Tetris Steine) bestehen wird hier definiert
 * @author Umut-Can.Bektas
 */

public class Block extends Rectangle {

	private static final long serialVersionUID = 5765224461913990929L;
	public int x, y;
	public static final int SIZE = 30;
	public Color c;

	public Block(Color c) {
		//Farbe des Minos wird definiert
		this.c = c;
	}

	public void draw(Graphics2D g2D) {
		//Größe der Steine aus denn ein Mino besteht wird hier definiert
		int margine = 2;
		g2D.setColor(c);
		g2D.fillRect(x + margine, y + margine, SIZE - (margine * 2), SIZE - (margine * 2));
	}
}
