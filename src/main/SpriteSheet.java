package main;

import java.awt.image.BufferedImage;

public class SpriteSheet {

	private BufferedImage image;
	
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}
	
	//64 / 64
	public BufferedImage grab64Image(int col, int row, int width, int height) {
		
		BufferedImage img = image.getSubimage((col * 64)-64, (row * 64)-64, width, height);
		return img;
		
	}
	
	//NORMAL 32 / 32
	public BufferedImage grabImage(int col, int row, int width, int height) {
		
		BufferedImage img = image.getSubimage((col * 32)-32, (row * 32)-32, width, height);
		return img;
		
	}
	//BIT 16 / 16
	public BufferedImage grabBitImage(int col, int row, int width, int height) {
		
		BufferedImage img = image.getSubimage((col * 16)-16, (row * 16)-16, width, height);
		return img;
		
	}
}
