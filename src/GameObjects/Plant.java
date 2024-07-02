package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import main.GameObject;
import main.ID;

public abstract class Plant extends GameObject{

	private String name; // Name of plant
	private String type; // Type of plant i.e 'Defender', 'Producer', etc
	
	public Plant(float x, float y, ID id, String name, String type) {
		super(x, y, id);
		this.type = type;
	}
	public boolean isHarvested;
	public long shootSpeed;
	public boolean isDefendable = false;
	public int size;
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBound();
	public abstract void shoot(GameObject targetObject);
	public abstract void disableShoot(GameObject targetObject);
	public abstract void enableShoot(GameObject targetObject);
	public abstract Rectangle getRange();
	public abstract long getShootSpeed();
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	

}
