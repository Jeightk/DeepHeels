package GameObjects.Plants;

import java.awt.Graphics;
import java.awt.Rectangle;

import Entities.EntityFlamecrest;
import Entities.Seeds.EntityFlamecrestSeed;
import Entities.EntityHandler;
import Entities.EntityID;
import Entities.EntityType;
import GameObjects.Plant;
import main.Game;
import main.GameObject;
import main.ID;

public class Flamecrest extends Plant{

	private int size = 32;
	private EntityHandler entityHandler;
	
	public Flamecrest(float x, float y, ID id, String name, String type, EntityHandler entityHandler) {
		super(x, y, id, name, type);
		this.entityHandler = entityHandler;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		if(isHarvested) {
			g.drawImage(Game.ss.grabImage(1, 2, size, size), (int)x, (int)y, null);
		}else {
			if(System.currentTimeMillis()/1000 % 2 == 0) {
				
				g.drawImage(Game.ss.grabImage(1, 1, size, size), (int)x, (int)y, null);
			}else{
				g.drawImage(Game.ss.grabImage(2, 1, size, size), (int)x, (int)y, null);
			}
		}
		
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
			entityHandler.addEntity(new EntityFlamecrest(x+10,y,EntityID.EntityFlamecrest, EntityType.Material));
			entityHandler.addEntity(new EntityFlamecrestSeed(x-10,y,EntityID.EntityFlamecrestSeed, EntityType.Seed));
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
