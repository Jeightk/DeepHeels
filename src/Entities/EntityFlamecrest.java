package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import main.Game;

public class EntityFlamecrest extends Entity{

	private int baseY;
	private boolean dropped = true;
	
	
	public EntityFlamecrest(float x, float y, EntityID EntityID, EntityType EntityType) {
		super(x, y, EntityID, EntityType);
		baseY = (int)this.y;
	}

	@Override
	public void tick() {
		this.dropAnimation(baseY, y);
//		if(dropped) {
//			if(y==baseY+30) {
//				for(int i = 0; i < 5; i++) {
//					
//					this.y = this.y-1;
//				}
//				dropped = false;
//			}else {
//				for(int i = 0; i < 5; i++) {
//					
//					this.y = this.y+1;
//				}
//			}
//			
//		}
//		
//		y = Game.clamp(y, baseY, baseY+30);
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.ss.grabBitImage(16, 13, 16, 16), (int)x, (int)y, null);
		
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	@Override
	public Image getImage() {
		return Game.ss.grabBitImage(16, 13, 16, 16);
	}
	

}
