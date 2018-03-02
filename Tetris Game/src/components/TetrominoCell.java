package components;

import java.awt.Color;
import java.awt.Graphics;

public class TetrominoCell implements Cell{
	private Color color;
	
	public TetrominoCell(Color color) {
		this.color=color;
	}
	
	
	@Override
	public void draw(Graphics g, int x, int y, int size) {
		g.setColor(color);
		g.fillRect(x, y, size, size);
		g.setColor(Color.black);
		g.drawRect(x, y, size, size);
	}
}
