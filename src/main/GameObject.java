package main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {

	protected float x,y;
	protected ID id;
	protected float velX, velY;
	protected int HP;
	protected int size;
	//woah
	
	
	public GameObject(float x, float y, ID id) {
		this.x = Game.clamp(x, 0, Game.WIDTH-75);
		this.y = Game.clamp(y, 0, Game.HEIGHT-100);
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBound();
	
	
	public int getHP() {
		return HP;
	}
	
	public void setHP(int hp) {
		this.HP = hp;
	}
	
	public float getX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public float getY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public ID getID() {
		return id;
	}
	
	public void setID(ID id) {
		this.id = id;
	}
	
	public float getVelocityX() {
		return velX;
	}
	
	public void setVelocityX(float velX) {
		this.velX = velX;
	}
	
	public float getVelocityY() {
		return velY;
	}
	
	public void setVelocityY(float velY) {
		this.velY = velY;
	}
	
	public void whenClicked() {
		
	}
	
	public int getSize() {
		return size;
	}
	
}
