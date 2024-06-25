package HUD;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;

public class Healthbar {

	private int width;
	private int height;
	private int x;
	private int y;
	
	public Healthbar(int width, int height, int x, int y) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	
	//RENDERING PLAYER HEALTH BAR
	public void render(Graphics g, int health) {
		g.drawImage(Game.healthbarIMG, x-201, y-7, null);
		g.setColor(Color.black);
		g.drawRect(Game.WIDTH/2-(width/2), 25, width, height);
		g.setColor(Color.red);
		g.fillRect(Game.WIDTH/2-(width/2), 25, health*6, height);
	}
	
	//RENDERING ENEMY HEALTH BARS
	public void render(Graphics g, int health, int baseHP) {
		g.setColor(Color.black);
		g.drawRect(x, y, baseHP/2, height);
		g.setColor(Color.red);
		g.fillRect(x, y, health/2, height);
		
	}
}
