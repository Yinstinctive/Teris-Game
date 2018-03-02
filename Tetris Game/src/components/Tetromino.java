package components;

import java.awt.Color;
import java.util.HashMap;

public class Tetromino {
	private int width;
	private int height;
	private Integer[] variationNum;
	private Cell[][] shape;
	private Color color;
	private HashMap<Integer,Color> colorList=new HashMap<>();
	private int x;
	private int y;
	private int[] emptyCell1= {-1,-1};
	private int[] emptyCell2= {-1,-1};

	public Tetromino(Integer[] variationNum,int width,int height,int x,int y) {
		this.variationNum=variationNum;
		this.width=width;
		this.height=height;
		shape=new Cell[height][width];
		this.initialColor();
		for(int row=0;row<height;row++) {
			for(int col=0;col<width;col++) {
				TetrominoCell tc=new TetrominoCell(color);
				shape[row][col]=tc;
			}
		}
		if(variationNum[2]!=-1)
		{
			shape[variationNum[3]][variationNum[2]]=null;
			emptyCell1[0]=(int)variationNum[3];
			emptyCell1[1]=(int)variationNum[2];
		}
		if(variationNum[4]!=-1)
		{
			shape[variationNum[5]][variationNum[4]]=null;
			emptyCell2[0]=(int)variationNum[5];
			emptyCell2[1]=(int)variationNum[4];
		}
		this.x=x;
		this.y=y;
	}
	
	public int[] getEmpty1() {
		return emptyCell1;
	}
	
	public int[] getEmpty2() {
		return emptyCell2;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void updateColor(Color c) {
		this.color=c;
	}
	
	public void initialColor() {
		colorList.put(0,Color.cyan);
		colorList.put(1,Color.green);
		colorList.put(2,Color.magenta);
		colorList.put(3,Color.red);
		colorList.put(4,Color.yellow);
		colorList.put(5,Color.orange);
		colorList.put(6,Color.pink);
		color=colorList.get(variationNum[6]);
	}
	
	public Integer[] getVariationNum() {
		return variationNum;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void updateX(int x) {
		this.x=x;
	}
	
	public void updateY(int y) {
		this.y=y;
	}
	
	public Cell[][] getShape(){
		return shape;
	}
}
