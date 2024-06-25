package Tiles;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;

public class Tile {

	private int height;
	private int width;
	private float x;
	private float y;
	
	public boolean isSelected;
	
	public Tile(int height, int width, float x, float y) {
		this.height = height;
		this.width = width;
		this.y = y;
		this.x = x;
	}
	
	public void render(Graphics g) {

		if(isSelected) {
			if(width == 64 && height == 64) {
				g.drawImage(Game.ss.grabImage(2, 7, 64, 64), (int)x, (int)y, null);
			}else if(width == 32 && height == 32){
				g.drawImage(Game.ss.grabImage(1, 8, 32, 32), (int)x, (int)y, null);
			}else {
				g.drawImage(Game.inventorySelector, (int)x, (int)y, null);
			}
			
			
		}
		
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x,(int)y,width,height);
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getX() {
		return (int)x;
	}
	
	public int getY() {
		return (int)y;
	}
	
}
