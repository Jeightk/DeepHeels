package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

import Tools.KeyInput;

public class Window extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3574977469333721379L;
	
	public Window(int width, int height, String title, boolean FULLSCREEN, Game game) {
		JFrame frame = new JFrame(title);
		
		
		//For full screen environment // REMEMBERE CHANGE THE TWO VALUE IN WIDTH OF THE GAME CLASS TO THREE!
		
		
		if(FULLSCREEN) {
			frame.setSize(width, height);
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			frame.setUndecorated(true);
			frame.setVisible(true);
			frame.add(game);
			game.start();
		}else {
			frame.setPreferredSize(new Dimension(width, height));
			frame.setMaximumSize(new Dimension(width, height));
			frame.setMinimumSize(new Dimension(width, height));
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.add(game);
			frame.setVisible(true);
			game.start();
		}
		
		
		
	}
	

}
