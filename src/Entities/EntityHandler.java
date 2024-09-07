package Entities;

import Tools.Recipes;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class EntityHandler {


	//LIST OF ENTITIES THAT ARE CURRENTLY DROPPED IN THE WORLD
	public LinkedList<Entity> entity = new LinkedList<Entity>();


	//LIST OF ALL ENTITIES THAT HAVE EVER BEEN REGISTERED IN THE GAME, REGISTERED == DROPPED IN GAME
	public LinkedList<Entity> ENTITIES = new LinkedList<>();

	private Recipes recipes;

	public EntityHandler(Recipes recipes){
		this.recipes = recipes;
	}

	
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
		if(!ENTITIES.contains(e)){
			ENTITIES.add(e);
		}
	}
	
	public void removeEntity(Entity e) {
		this.entity.remove(e);
	}
	
	public void addMultipleEntities(List<Entity> drops) {
		
		for(int i = 0; i < drops.size(); i++) {
			addEntity(drops.get(i));
		}
		
	}

	public Object createObjectfromSaveFile(String objectName){
		if(objectName == "EntityTill"){
			return new EntityTill(recipes.tillRecipe, EntityID.EntityTill, "Till", 11, EntityType.Weapon);
		}else if(objectName == "EntityHotrod"){
			return new EntityHotrod(recipes.hotrodRecipe, EntityID.EntityHotrod, "Hotrod", 35, EntityType.Weapon);
		}else if(objectName == "EntityFlamecrestSeed"){
			return new EntityHotrod(recipes.hotrodRecipe, EntityID.EntityHotrod, "Hotrod", 35, EntityType.Weapon);

		}else if(objectName == "EntityFlamecrestSeed"){

		}else if(objectName == "EntityFlamecrestSeed"){

		}

		return null;
	}
	
}
