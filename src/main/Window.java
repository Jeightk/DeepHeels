package main;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import Tools.KeyInput;

public class Window extends Canvas{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3574977469333721379L;
	JFrame frame;


	public Window(int width, int height, String title, boolean FULLSCREEN, Game game) {
		frame = new JFrame(title);
		
		
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
//			frame.setMaximumSize(new Dimension(width, height));
			frame.setMinimumSize(new Dimension(width, height));
			
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(true);
			frame.setLocationRelativeTo(null);
			frame.add(game);
			frame.setVisible(true);
			game.start();
		}

		frame.addComponentListener(new ComponentAdapter()
		{
			public void componentResized(ComponentEvent evt) {
				Component c = (Component)evt.getSource();
				game.setWIDTH(frame.getWidth());
				game.setHEIGHT(frame.getHeight());
			}
		});
		
	}

	public void loadSaves(){

	}
	

}
