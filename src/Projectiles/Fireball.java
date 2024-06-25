package Projectiles;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;
import main.GameObject;
import main.ID;

public class Fireball extends Bullet{

	float targetX;
	float targetY;
	
	double dx;
	double dy;
	double rad;
	
	double velX;
	double velY;
	String type;
	
	public Fireball(float x, float y, ID id, String type, int speed, GameObject targetObject) {
		super(x, y, id, type, speed);
		this.type = type;
		System.out.println(targetObject.getSize());
		this.targetX = targetObject.getX()+(targetObject.getSize() / 2);
		this.targetY = targetObject.getY()+(targetObject.getSize() / 2);
		
		velX = (targetX - this.x) / 5; // HAD TO DIVIDE BY 5 TO REDUCE SPEED
		velY = (targetY - this.y) / 5;
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.ss.grabBitImage(15, 15, 16, 16), (int)x, (int)y, null);
		
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;
		
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	@Override
	public String getType() {
		return type;
	}

	
}
