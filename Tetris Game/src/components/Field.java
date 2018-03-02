package components;

import java.util.HashMap;
import java.util.HashSet;

import javax.swing.JPanel;

public class Field extends JPanel{
	private static final long serialVersionUID = 1L;
	private int width;
	private int height;
	private Cell[][] field;
	private Location appearLocation=new Location(0,6);
	private HashMap<Integer,Integer[]> variations=new HashMap<>();
	private Tetromino currentTetromino;
	private Tetromino nextTetromino;
	private boolean hasFullRow=false;
	
	public Field(int width,int height) {
		this.width=width;
		this.height=height;
		field=new Cell[height][width];
		this.initialVariations();
		this.getNextTeromino();

	}
	
	public void initialVariations() {
//		Integer[Width, Height, point1_X, point1_Y, point2_X, point2_Y,Color_code]
//		point1 and point2 need to be null
//		-1 means the point is not null
		Integer[] i={4,1,-1,-1,-1,-1,0};
		variations.put(0, i);
		Integer[] o={2,2,-1,-1,-1,-1,1};
		variations.put(1, o);
		Integer[] t= {3,2,0,0,2,0,2};
		variations.put(2, t);
		Integer[] s= {3,2,0,0,2,1,3};
		variations.put(3, s);
		Integer[] z= {3,2,2,0,0,1,4};
		variations.put(4, z);
		Integer[] l= {2,3,1,0,1,1,5};
		variations.put(5, l);
		Integer[] j= {2,3,0,0,0,1,6};
		variations.put(6, j);
	}
	
	public Cell getCell(int row, int col) {
		return field[row][col];
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void dropToBottom() {
		while(!this.reachBottom()&&!this.reachBottomCell()) 
		{
			this.removeTetromino(currentTetromino);
			currentTetromino.updateY(currentTetromino.getY()+1);
		}
		this.addTetromino();
	}
	
	public boolean rowIsFull(int rowNum) {
		boolean result=true;
		for(int i=0;i<width;i++) {
			if(field[rowNum][i]==null) {
				result=false;
				break;
			}
		}
		return result;
	}
	
	public void removeFullRow() {
		HashSet<Integer> fullRows=new HashSet<>();
		for(int i=height-1;i>0;i--) {
			if(rowIsFull(i)) {
				hasFullRow=true;
				fullRows.add(i);
				for(int j=0;j<width;j++) {
					field[i][j]=null;
				}
			}
		}
		if(hasFullRow) {
			Cell[][] newField=new Cell[height][width];
			int newFieldRow=height-1;
			for(int i=height-1;i>=0;i--) 
			{
				if(fullRows.contains(i)) {
					continue;
				}
				else{
					for(int j=0;j<width;j++) {
						newField[newFieldRow][j]=field[i][j];
					}
					newFieldRow--;
				}
			}
			for(int i=0;i<height;i++) {
				for(int j=0;j<width;j++) {
					field[i][j]=newField[i][j];
				}
			}
			hasFullRow=false;
		}
	}
	
	
	public void getNextTeromino() {
		int range=variations.size();
		Integer key=(int)Math.round((Math.random()*range-0.5));
		Integer [] value=variations.get(key);
		Tetromino t=new Tetromino(value,(int)value[0],(int)value[1],appearLocation.getCol(),appearLocation.getRow());
		nextTetromino=t;
		int random=(int)(Math.round(Math.random()*4-0.5));
		for(int i=1;i<=random;i++) 
		{
			nextTetromino=this.rotateNext();
		}
	}
	
	public void getCurrentTeromino() {
		currentTetromino=nextTetromino;
		this.getNextTeromino();;
	}
	
	public void removeTetromino(Tetromino tetromino) {
		int r=tetromino.getY();
		int c=tetromino.getX();
		Cell[][] s=tetromino.getShape();
		for(int i=0;i<s.length;i++) {
			for(int j=0;j<s[i].length;j++){
				if(s[i][j]!=null) {
					field[i+r][j+c]=null;
				}
			}
		}
	}
	
	public void placeTetromino() {
		this.getCurrentTeromino();
		currentTetromino.updateX(appearLocation.getCol());
		currentTetromino.updateY(appearLocation.getRow());
		this.addTetromino();
	}
	
	public void addTetromino() {
		Cell[][] c=currentTetromino.getShape();
		for(int i=0;i<c.length;i++) {
			for(int j=0;j<c[i].length;j++) {
				if(field[i+currentTetromino.getY()][j+currentTetromino.getX()]==null)
				field[i+currentTetromino.getY()][j+currentTetromino.getX()]=c[i][j];
			}
		}
	}
	
	public boolean reachBottomCell() {
		boolean result=false;
		Cell[][] c=currentTetromino.getShape();
		int[] checkY = new int[c[0].length];
		for(int i:checkY) {
			checkY[i]=0;
		}
		for(int i=0;i<c.length;i++) {
			for(int j=0;j<c[i].length;j++) {
				if(c[i][j]!=null) {
					if (i>checkY[j]) {
						checkY[j]=i;
					}
				}
			}
		}
		for(int i=0;i<checkY.length;i++) {
			if(field[currentTetromino.getY()+checkY[i]+1][currentTetromino.getX()+i]!=null) {
				result=true;
				break;
			}
		}
		return result;
	}
	
	public boolean reachLeftCell() {
		boolean result=false;
		int minX=currentTetromino.getX();
		Cell[][] c=currentTetromino.getShape();
		if(minX>0) 
		{
			for(int i=0;i<c.length;i++) {
				if(c[i][c[0].length-1]==null)
					continue;
				if((field[i+currentTetromino.getY()][minX-1]!=null)) {
					result=true;
					break;
				}
			}
		}
		return result;
	}
	
	public boolean reachRightCell() {
		boolean result=false;
		Cell[][] c=currentTetromino.getShape();
		int maxX=currentTetromino.getX()+c[0].length-1;
		if(maxX<width-1) 
		{
			for(int i=0;i<c.length;i++) {
				if(c[i][c[0].length-1]==null)
					continue;
				if((field[i+currentTetromino.getY()][maxX+1]!=null)) {
					result=true;
					break;
				}
			}
		}
		return result;
	}
	
	public boolean reachBottom() {
		if (currentTetromino.getY()==height-currentTetromino.getHeight())
			return true;
		return false;
	}
	
	public boolean reachLeftWall() {
		if (currentTetromino.getX()==0)
			return true;
		return false;
	}
	
	public boolean reachRightWall() {
		if (currentTetromino.getX()==width-currentTetromino.getWidth())
			return true;
		return false;
	}
	
	public Tetromino rotate(Tetromino tetromino) {
		Integer[] copy=tetromino.getVariationNum();
		Integer[] temp= {copy[1],copy[0],copy[3],copy[2],copy[5],copy[4],copy[6]};
		Tetromino t=new Tetromino(temp,tetromino.getHeight(),tetromino.getWidth(),tetromino.getX(),tetromino.getY());
		t.updateColor(tetromino.getColor());
		for(int row=0;row<t.getHeight();row++) {
			for(int col=0;col<t.getWidth();col++) {
				t.getShape()[row][col]=tetromino.getShape()[col][tetromino.getWidth()-1-row];
			}
		}
		return t;
	}
	
	public void rotateCurrent() {
		Tetromino temp=this.rotate(currentTetromino);
		if(((temp.getX()>=0)&&(temp.getX()<=width-temp.getWidth())&&(temp.getY()<=height-temp.getHeight())))
		{
			if(!this.reachBottom()&&!this.reachBottomCell()) {
				this.removeTetromino(currentTetromino);
				currentTetromino=temp;
				this.addTetromino();
			}
		}
	}
	
	public Tetromino rotateNext() {
		Tetromino temp=this.rotate(nextTetromino);
		return temp;
	}
	
	public void moveLeft() {
		if((!this.reachLeftCell())&&(!this.reachLeftWall()))
		{
			this.removeTetromino(currentTetromino);
			currentTetromino.updateX(currentTetromino.getX()-1);
			this.addTetromino();
		}
	}
	
	public void moveRight() {
		if((!this.reachRightCell())&&(!this.reachRightWall()))
		{
			this.removeTetromino(currentTetromino);
			currentTetromino.updateX(currentTetromino.getX()+1);
			this.addTetromino();
		}
	}
	
	public void moveDown() {
		if((!this.reachBottom())&&(!this.reachBottomCell()))
		{
			this.removeTetromino(currentTetromino);
			currentTetromino.updateY(currentTetromino.getY()+1);
			this.addTetromino();
		}
	}
	
}
