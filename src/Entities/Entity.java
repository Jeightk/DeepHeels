package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import main.Game;

public abstract class Entity {

	protected float x;
	protected float y;
	protected EntityID EntityID;
	protected EntityType EntityType;
	private boolean dropped = true;
	
	public Entity(float x, float y, EntityID EntityID, EntityType EntityType) {
		this.EntityID = EntityID;
		this.EntityType = EntityType;
		this.x = x;
		this.y = y;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Image getImage();
	public abstract Rectangle getBound();
	
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
	
	public EntityID getEntityID() {
		return EntityID;
	}
	
	public EntityType getEntityType() {
		return EntityType;
	}
	
	public void dropAnimation(float baseY, float y) {
		if(dropped) {
			if(y==baseY+30) {
				for(int i = 0; i < 5; i++) {
					
					this.y = this.y-1;
				}
				dropped = false;
			}else {
				for(int i = 0; i < 5; i++) {
					
					this.y = this.y+1;
				}
			}
			
		}
		
		y = Game.clamp(y, baseY, baseY+30);
	}
	
	
}
