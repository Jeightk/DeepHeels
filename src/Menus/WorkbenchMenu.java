package Menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Entities.Craftable;
import Entities.EntityID;
import Tiles.Tile;
import Tiles.TileMap;
import Tools.Recipes;
import main.Game;

public class WorkbenchMenu {

	private Inventory inventory;
	private Recipes recipes;
	int craftmenuX = Game.WIDTH/2-300;
	int craftmenuY = 20;
	
	//For running through the sets to find the craftables one time in the render
	
	//All craftable items
	public ArrayList<Craftable> craftables = new ArrayList<>();
	
	//Houses all the individual tiles
	public Tile[] craftMenuRegions = new Tile[9];
	
	//
	public HashMap<Tile, Craftable> craftableLocation = new HashMap<Tile, Craftable>();
	
	private List<EntityID> itemTest;
	private List<EntityID> inventoryToSet;
	
	public WorkbenchMenu(Inventory inventory, Recipes recipes) {
		this.inventory = inventory;
		this.recipes = recipes;
		
		
	}
	
	
	public void tick() {
		
		
	}
	
	
	
	public void render(Graphics g) {
		g.drawImage(Game.workbenchIMG, craftmenuX, craftmenuY, null);
		//refreshes the craftables items each time the player opens the workbench
		
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Courier", 1, 25));
		//Takes the craftable items and prints them into the workbench
		
		//MAYBE trying refreshing the render of the inventory to fix issue?
		if(craftables.size() != 0) {
			for(int i = 0; i < craftables.size(); i++) {
				try {
					g.drawImage(craftables.get(i).getImage(), (craftmenuX+300)-(150), (craftmenuY+100)+i*70, 32, 32, null);
					//TODO: FIX ERROR HERE NOT SURE WHAT IT IS YET
					g.drawString(craftables.get(i).getName(), (craftmenuX+300)-(50), (craftmenuY+125)+i*70);
				}catch(Exception e) {
					System.out.println("ITERATION " + i);
					System.out.println("WORKBENCHMENU: " + e + " : " +e.getStackTrace()[0].getLineNumber());
				}
				
			}
		}
		
		
		
		
		checkCraftmenuSize(g);
		
	}
	
	public void createSlots(TileMap tileMap) {
		if(craftables.size() < 9) {
			for(int i = 0; i < craftables.size(); i++) {
				Tile t = new Tile(60, 500, craftmenuX+50, (craftmenuY+90)+i*70);
				craftMenuRegions[i] = t;
				tileMap.workbenchTiles.add(t);
				if(!(craftableLocation.containsKey(t))) {
					craftableLocation.put(t, craftables.get(i));
				}
				
				
			}
		}else {
			for(int i = 0; i < 9; i++) {
				Tile t = new Tile(60, 500, craftmenuX+50, (craftmenuY+90)+i*70);
				craftMenuRegions[i] = t;
				tileMap.workbenchTiles.add(t);
				
			}
		}
		
		
	}
	
	public void checkCraftmenuSize(Graphics g) {
		if(craftables.size() < 9) {
			for(int i = 0; i < craftables.size(); i++) {
				Tile t = craftMenuRegions[i];
				t.render(g);
			}
		}else {
			for(int i = 0; i < 9; i++) {
				Tile t = craftMenuRegions[i];
				t.render(g);
			}
		}
	}
	
	public void findCraftables() {
		craftables.clear();
		
		//Cycles through all recipes to match which ones are craftable with the given inventory.
		for(int i = 0; i < recipes.Allrecipes.size(); i++) {
//			itemTest = inventory.TOTALInventory;
//			inventoryToSet = inventory.TOTALInventory;
//			itemTest = recipes.Allrecipes.get(i).getRecipe();
//			List<EntityID> itemTestToList = new ArrayList<>();
//			for(EntityID id : itemTest) {
//				itemTestToList.add(id);
//			}
//			itemTest.retainAll(recipes.Allrecipes.get(i).getRecipe());
			
//			if(inventoryToSet.retainAll(itemTest)) {
//				System.out.println(inventoryToSet);
//				System.out.println(recipes.Allrecipes.get(i).getRecipe());
//				System.out.println("--------------------------------------------");
//			}
			if(containsRecipe(recipes.Allrecipes.get(i).getRecipe()) && !(craftables.contains(recipes.Allrecipes.get(i)))) {
				craftables.add(recipes.Allrecipes.get(i));
			}
//			if(itemTest.equals(recipes.Allrecipes.get(i).getRecipe()) && !(craftables.contains(recipes.Allrecipes.get(i)))) {
//				System.out.println("ADDED");
//				craftables.add(recipes.Allrecipes.get(i));
//			}
			
		}
		
//		System.out.println("Craftables : " + craftables);
	}
	
	public void clearCraftablesList() {
		craftables.clear();
	}
	
	public boolean containsRecipe(List<EntityID> recipe) {
		List<EntityID> copy = new ArrayList<>(inventory.TOTALInventory);
		
		for(EntityID ingredient : recipe) {
			if(!copy.remove(ingredient)) {
				return false;
			}
		}
		return true;
		
	}
	
	
}
