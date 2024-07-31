package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import main.Game;

public class EntityTill extends Craftable{

	public EntityTill(Map<EntityID, Integer> recipe, EntityID entityid, String name, int damage, EntityType EntityType) {
		super(recipe, entityid, name, damage, EntityType);
	}

	@Override
	public void render(Graphics g) {
//		g.drawImage(Game.ss.grabImage(8, 6, 32, 32), 16, 16, null);
		
	}

	@Override
	public Image getImage() {
		return Game.ss.grabImage(8, 6, 32, 32);
	}

}
