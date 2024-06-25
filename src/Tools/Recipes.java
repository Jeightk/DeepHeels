package Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import Entities.Craftable;
import Entities.CraftableHandler;
import Entities.EntityHotrod;
import Entities.EntityID;
import Entities.EntityTill;
import Entities.EntityType;


public class Recipes {

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
		craftableHandler.addCraftable(new EntityHotrod(Arrays.asList(EntityID.EntityFlamecrest, EntityID.EntityIvoriane), EntityID.EntityHotrod, "Hotrod", 35, EntityType.Weapon));
		craftableHandler.addCraftable(new EntityTill(Arrays.asList(EntityID.EntityIvoriane, EntityID.EntityIvoriane, EntityID.EntityIvoriane), EntityID.EntityTill, "Till", 11, EntityType.Weapon));
	}
	
	
}
