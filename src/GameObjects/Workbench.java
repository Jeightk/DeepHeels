package GameObjects;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Game;
import main.GameObject;
import main.ID;

public class Workbench extends GameObject{

	public int size = 64;
	
	public Workbench(float x, float y, ID id)
	{
		super(x, y, id);
	}

	@Override
	public void tick() {
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.ss.grab64Image(1, 3, size, size), (int)Game.WIDTH/2-32, (int)y, null);
		
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x,(int)y,size,size);
	}

}
