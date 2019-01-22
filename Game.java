package Basics;

import javax.swing.JFrame;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;

import Basics.board;

public class Game extends JPanel{

	

	static GraphicsConfiguration gc;

	public void paint(Graphics g) {
		g.drawLine(20, 20, 300, 300);
	}

	private static int[][] x = setup();

	public static void main(String[] args) {
		JFrame frame = new JFrame(gc);
		frame.setTitle("2048");
		frame.setSize(400,400);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		runGame(x);
	}
}
