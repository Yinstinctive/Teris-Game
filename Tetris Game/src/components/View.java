package components;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class View extends JPanel{
	private static final long serialVersionUID = 1L;
	private static final int GRID_SIZE = 15;
	private Field theField;
	
	public View(int fieldWidth, int fieldHeight) {
		theField=new Field(fieldWidth,fieldHeight);
	}
	
	public Field getField() {
		return theField;
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for(int row=0;row<theField.getHeight();row++) {
			for(int col=0;col<theField.getWidth();col++) {
				Cell cell=theField.getCell(row, col);
				if(cell!=null) {
					cell.draw(g, col*GRID_SIZE, row*GRID_SIZE, GRID_SIZE);
				}
			}
		}	
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(theField.getWidth()*GRID_SIZE+1, theField.getHeight()*GRID_SIZE+1);
	}
}
