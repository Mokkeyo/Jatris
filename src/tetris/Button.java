/**
 * 
 */
package tetris;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

/**
 * Button für das Start Menu
 * 
 * @author Umut-Can.Bektas
 */

public class Button extends JButton {

	private static final long serialVersionUID = 3775162369395035483L;

	/**
	 * @param str       (Der angezeigt Text)
	 * @param XPosition (X Position auf dem Panel)
	 * @param YPosition (Y Position auf dem Panel)
	 */

	public Button(String str, int XPosition, int YPosition) {
		super(str);
		setFocusable(false);
		setBounds(XPosition, YPosition, 600, 90);
		setBackground(Color.black);
		setForeground(Color.white);
		setFont(new Font("Comic Sans", Font.BOLD, 70));
	}

}
