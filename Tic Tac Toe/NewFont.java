package TicTacToe;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NewFont {
	static Font font = null;
	static String fontPath = "src/TicTacToe/Chococooky.ttf";
	
	public static Font getFont(int type, int size)
			throws FileNotFoundException, FontFormatException, IOException {
		FileInputStream fis = new FileInputStream(fontPath);
		font = Font.createFont(Font.TRUETYPE_FONT, fis);
		font = font.deriveFont(type, size);
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(font);
		return font;
	}
}
