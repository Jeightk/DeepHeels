package Entities;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Craftable {

	protected Map<EntityID, Integer> recipe;
	protected EntityID entityid;
	protected EntityType EntityType;
	protected String name;
	protected int damage;
	
	public abstract void render(Graphics g);
	public abstract Image getImage();
	
	public Craftable(Map<EntityID, Integer> recipe, EntityID entityid, String name, int damage, EntityType EntityType) {
		this.recipe = recipe;
		this.EntityType = EntityType;
		this.entityid = entityid;
		this.name = name;
		this.damage = damage;
	}
	
	public String getName() {
		return name;
	}
	
	public Map<EntityID, Integer> getRecipe(){
		return recipe;
	}
	
	public EntityID getEntityID() {
		return entityid;
	}
	
	public EntityType getEntityType() {
		return EntityType;
	}
	
	public int getDamage() {
		return damage;
	}
	
}
