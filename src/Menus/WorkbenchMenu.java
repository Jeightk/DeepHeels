package Menus;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;

import Entities.Craftable;
import Entities.EntityID;
import Levels.LevelHandler;
import Tiles.Tile;
import Tiles.TileMap;
import Tools.Recipes;
import main.Game;

public class WorkbenchMenu {

	private Inventory inventory;
	private Recipes recipes;
	private LevelHandler levelHandler;
	int craftmenuX = Game.WIDTH/2-300;
	int craftmenuY = 20;

	//For when hovering over the 'nextlevel' button
	public boolean nextlevelhover = false;
	public boolean startNextLevel = false;

	//For running through the sets to find the craftables one time in the render
	
	//All craftable items
	public ArrayList<Craftable> craftables = new ArrayList<>();
	
	//Houses all the individual tiles
	public Tile[] craftMenuRegions = new Tile[9];
	
	//
	public HashMap<Tile, Craftable> craftableLocation = new HashMap<Tile, Craftable>();
	
	private List<EntityID> itemTest;
	private List<EntityID> inventoryToSet;
	
	public WorkbenchMenu(Inventory inventory, Recipes recipes, LevelHandler levelHandler) {
		this.inventory = inventory;
		this.recipes = recipes;
		this.levelHandler = levelHandler;
		
	}
	
	
	public void tick() {
		
		
	}
	
	
	
	public void render(Graphics g) {
		g.drawImage(Game.workbenchIMG, craftmenuX, craftmenuY, null);
		renderNextLevel(g, nextlevelhover);
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

	//Render NextLevel
	public void renderNextLevel(Graphics g, boolean b){
		//This is for rendering the next level button & for hovering
		if(b){
			g.drawImage(Game.NextLevelIMGHover, craftmenuX+600, craftmenuY, null);
		}else{
			g.drawImage(Game.NextLevelIMG, craftmenuX+600, craftmenuY, null);
		}

	}
	//Gets the bounds of the check mark to proceed to the next level.
	public Rectangle nextLevelBounds(){
		return new Rectangle(craftmenuX+600+105, craftmenuY+64, 94,71);
	}
	//This renders the starting countdown for the next level to start
	public void renderStartNextLevel(Graphics g){
		levelHandler.startCountdown(g);
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
			if(containsRecipe(recipes.Allrecipes.get(i).getRecipe()) && !(craftables.contains(recipes.Allrecipes.get(i)))) {
				craftables.add(recipes.Allrecipes.get(i));
			}

		}
	}
	
	public void clearCraftablesList() {
		craftables.clear();
	}
	
	public boolean containsRecipe(Map<EntityID, Integer> recipe) {

		for(EntityID ingredient : recipe.keySet()) {

			//CHECKS TO MAKE SURE PLAYER INVENTORY HAS CORRECT AMOUNT OF INGREDIENTS TO CRAFT
			if(inventory.inventory.get(ingredient) == null || recipe.get(ingredient) > inventory.inventory.get(ingredient)) {
				return false;
			}
		}
		return true;
		
	}
	
	
}
