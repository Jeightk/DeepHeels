package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import main.Game;

public class EntityHotrod extends Craftable{

	public EntityHotrod(List<EntityID> recipe, EntityID entityid, String name, int damage, EntityType EntityType) {
		super(recipe, entityid, name, damage, EntityType);
		
	}

	@Override
	public void render(Graphics g) {
//		g.drawImage(Game.ss.grabBitImage(15, 14, 16, 16), (int)x, (int)y, null);
		
	}
	
	public Image getImage() {
		return Game.ss.grabBitImage(16, 14, 16, 16);
	}

}
