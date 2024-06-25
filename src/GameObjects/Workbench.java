package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;
import main.GameObject;
import main.ID;

public class Workbench extends GameObject{

	public int size = 32;
	
	public Workbench(float x, float y, ID id) {
		super(x, y, id);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.ss.grabImage(8, 1, size, size), (int)x, (int)y, null);
		
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x,(int)y,size,size);
	}

}
