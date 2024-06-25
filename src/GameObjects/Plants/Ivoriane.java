package GameObjects.Plants;

import java.awt.Graphics;
import java.awt.Rectangle;

import Entities.EntityElderberry;
import Entities.EntityHandler;
import Entities.EntityID;
import Entities.EntityIvoriane;
import Entities.EntityType;
import GameObjects.Plant;
import main.Game;
import main.GameObject;
import main.ID;

public class Ivoriane extends Plant{

	private int size = 32;
	private EntityHandler entityHandler;
	
	public Ivoriane(float x, float y, ID id, String name, String type, EntityHandler entityHandler) {
		super(x, y, id, name, type);
		this.entityHandler = entityHandler;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.ss.grabImage(7, 1, size, size), (int)x, (int)y, null);
		
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x, (int)y, size,size);
	}
	
	public int getSize() {
		return size;
	}
	
	@Override
	public void whenClicked() {
		if(isHarvested == false) {
			isHarvested = true;
			entityHandler.addEntity(new EntityIvoriane(x,y,EntityID.EntityIvoriane, EntityType.Material));
		}
	}

	@Override
	public Rectangle getRange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getShootSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void disableShoot(GameObject targetObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enableShoot(GameObject targetObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shoot(GameObject targetObject) {
		// TODO Auto-generated method stub
		
	}


}
