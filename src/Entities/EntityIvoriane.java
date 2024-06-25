package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import main.Game;

public class EntityIvoriane extends Entity{

	private boolean dropped = true;
	private int baseY;
	
	public EntityIvoriane(float x, float y, EntityID EntityID, EntityType EntityType) {
		super(x, y, EntityID, EntityType);
		baseY = (int)this.y;
	}

	@Override
	public void tick() {
		this.dropAnimation(baseY, y);
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.ss.grabBitImage(15, 14, 16, 16), (int)x, (int)y, null);
		
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}

	@Override
	public Image getImage() {
		return Game.ss.grabBitImage(15, 14, 16, 16);
	}

}
