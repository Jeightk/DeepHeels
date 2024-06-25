package Projectiles;

import java.awt.Graphics;

import main.GameObject;
import main.ID;

public abstract class Bullet extends GameObject{

	public String type;
	public int speed;
	
	public Bullet(float x, float y, ID id, String type, int speed) {
		super(x, y, id);
		// TODO Auto-generated constructor stub
		this.type = type;
		this.speed = speed;
	}
	
	
	
	public abstract void render(Graphics g);
	public abstract String getType();
	
}
