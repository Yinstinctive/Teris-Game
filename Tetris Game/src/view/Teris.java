package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import components.View;

public class Teris extends JFrame{
	private static final long serialVersionUID = 1L;
	private static View theView=new View(15,25);
	private long level=1000;
	private Timer timer=new Timer();
	private MyTimerTask task=new MyTimerTask();
	private KeyboardHandler keyboard=new KeyboardHandler();
	
	public Teris() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setTitle("Teris Game");
		this.add(theView);
		this.pack();
		this.setVisible(true);
		theView.addKeyListener(keyboard);
		theView.requestFocus();
	}
		
	private class MyTimerTask extends TimerTask {
		
		@Override
		public void run() {
			theView.getField().moveDown();
			theView.repaint();
			if((theView.getField().reachBottom())||(theView.getField().reachBottomCell()))
			{
					theView.getField().removeFullRow();
					theView.getField().getCurrentTeromino();
					theView.repaint();
			}
		}

	}
	
	private class KeyboardHandler implements KeyListener{

		@Override
		public void keyTyped(KeyEvent e) {
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			int key=e.getKeyCode();
			switch(key) {
			case KeyEvent.VK_LEFT:
				theView.getField().moveLeft();
				break;
			case KeyEvent.VK_RIGHT:
				theView.getField().moveRight();
				break;
			case KeyEvent.VK_UP:
				theView.getField().rotateCurrent();
				break;
			case KeyEvent.VK_DOWN:
				theView.getField().moveDown();
				break;
			case KeyEvent.VK_SPACE:
				theView.getField().dropToBottom();
				theView.getField().removeFullRow();
				theView.getField().getCurrentTeromino();
				break;
			}
			theView.repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
			
		}
		
	}
	
	public void start() {
		theView.getField().placeTetromino();
		theView.repaint();
		timer.schedule(task, level, level);
	}
	
	public static void main(String[] args) {
		Teris game=new Teris();
		game.start();
	}

}
