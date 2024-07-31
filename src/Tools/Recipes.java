package Tools;

import java.util.*;

import Entities.Craftable;
import Entities.CraftableHandler;
import Entities.EntityHotrod;
import Entities.EntityID;
import Entities.EntityTill;
import Entities.EntityType;


public class Recipes {

	Map<EntityID, Integer> hotrodRecipe = Map.of(EntityID.EntityFlamecrest, 1, EntityID.EntityIvoriane, 2);
	Map<EntityID, Integer> tillRecipe = Map.of(EntityID.EntityIvoriane, 3);


	private CraftableHandler craftableHandler;
	
	public ArrayList<Craftable> Allrecipes = new ArrayList<>();
	
	public Recipes(CraftableHandler craftableHandler) {
		this.craftableHandler = craftableHandler;
		
		addIt();
		
		for(int i = 0; i < craftableHandler.craftable.size(); i++) {
			Allrecipes.add(craftableHandler.craftable.get(i));
		}
		
	}
	
	
	

	//Temp place to add craftables to the list
	public void addIt() {
		//int slot is the damage amplifier
		craftableHandler.addCraftable(new EntityHotrod(hotrodRecipe, EntityID.EntityHotrod, "Hotrod", 35, EntityType.Weapon));
		craftableHandler.addCraftable(new EntityTill(tillRecipe, EntityID.EntityTill, "Till", 11, EntityType.Weapon));
	}
	
	
}
