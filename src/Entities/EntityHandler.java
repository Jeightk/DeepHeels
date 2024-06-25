package Entities;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class EntityHandler {

	public LinkedList<Entity> entity = new LinkedList<Entity>();
	
	public void tick() {
		
		for(int i = 0; i < entity.size(); i++) {
			Entity tempEntity = entity.get(i);
			tempEntity.tick();
		}
		
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < entity.size(); i++) {
			Entity tempEntity = entity.get(i);
			tempEntity.render(g);
		}
	}
	
	public void addEntity(Entity e) {
		this.entity.add(e);
	}
	
	public void removeEntity(Entity e) {
		this.entity.remove(e);
	}
	
	public void addMultipleEntities(List<Entity> drops) {
		
		for(int i = 0; i < drops.size(); i++) {
			addEntity(drops.get(i));
		}
		
	}
	
}
