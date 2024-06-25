package GameObjects.Plants;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import Entities.EntityElderberry;
import Entities.EntityHandler;
import Entities.EntityID;
import Entities.EntityType;
import GameObjects.Plant;
import Tools.MouseInput;
import main.Game;
import main.GameObject;
import main.ID;

public class Elderberry extends Plant{

	private boolean isHarvested = false;
	private EntityHandler entityHandler;
	
	private int size = 64;
	
	public Elderberry(float x, float y, ID id, String name, String type, EntityHandler entityHandler) {
		super(x, y, id, name, type);
		
		this.entityHandler = entityHandler;
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		
		//System for animating the flower, the modulus operator acts as the frequency for the animation...
		if(isHarvested) {
			g.drawImage(Game.ss.grab64Image(3, 2, 64, 64), (int)x, (int)y, null);
		}else {
			
			g.drawImage(Game.ss.grab64Image(1, 2, 64, 64), (int)x, (int)y, null);
//			if(System.currentTimeMillis()/1000 % 2 == 0) {
//				
//				g.drawImage(Game.ss.grab64Image(1, 2, 64, 64), (int)x, (int)y, null);
//			}else{
//				g.drawImage(Game.ss.grab64Image(2, 2, 64, 64), (int)x, (int)y, null);
//			}
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
			entityHandler.addEntity(new EntityElderberry(x+(size/2)-3,y+size-3,EntityID.EntityElderberry, EntityType.Material));
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
