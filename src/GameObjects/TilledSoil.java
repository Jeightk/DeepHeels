package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import Entities.EntityID;
import main.Game;
import main.GameObject;
import main.ID;

public class TilledSoil extends GameObject{

	public int size = 32;
	private EntityID seedID;
	private int stage;
	private boolean renderSoil = true;
	
	public TilledSoil(float x, float y, ID id, EntityID seedID, int stage) {
		super(x, y, id);
		this.seedID = seedID;
		this.stage = stage;
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		if(renderSoil) {
			g.drawImage(Game.ss.grabImage(8, 2, size, size), (int)x, (int)y, null);
		}
		
		if(seedID == EntityID.EntityFlamecrestSeed) {
			if(stage == 0) {
				g.drawImage(Game.ss.grabBitImage(16, 15, 16, 16), (int)x+4, (int)y+4, null);
			}else if(stage == 1) {
				g.drawImage(Game.ss.grabImage(8, 3, size, size), (int)x, (int)y-5, null);
			}
			
		}
		
		
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x,(int)y,size,size);
	}
	
	public boolean isSeeded() {
		if(seedID == null) {
			return false;
		}
		return true;
	}

}
