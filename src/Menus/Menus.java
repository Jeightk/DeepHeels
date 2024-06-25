package Menus;

import java.awt.Graphics;

import Tiles.TileMap;
import main.Game;
import main.Game.STATE;

public class Menus {

	private Inventory inventory;
	private TileMap tileMap;
	private WorkbenchMenu workbenchmenu;
	Game game;
	
	public Menus(Inventory inventory, TileMap tileMap, WorkbenchMenu workbenchmenu, Game game) {
		this.inventory = inventory;
		this.tileMap = tileMap;
		this.workbenchmenu = workbenchmenu;
		this.game = game;
	}
	
	public boolean openInventory = false;
	public boolean openWorkbench = false;
	
	private boolean initialized = false;
	
	public void render(Graphics g) {
		
		if(openInventory) {
			inventory.render(g);
			
		}
		
		if(openWorkbench) {
			workbenchmenu.render(g);
		}
		
	}
	
	
	public void inventoryRegionsAccess() {
		//refresh on each inventory open
		tileMap.inventoryTiles.clear();
		tileMap.inventoryGearTiles.clear();
		inventory.equippedGear.clear();
		//---
		
		inventory.createSlots(tileMap);
	}
	
	public void workbenchRegionsAccess() {
		tileMap.workbenchTiles.clear();
		workbenchmenu.createSlots(tileMap);
	}
	
	public void setEntityMessageVisible(boolean bool) {
		inventory.EntityMessage = bool;
	}
	
	public void refreshInventory() {
		this.openInventory = true;
		game.gameState = STATE.Inventory;
		this.inventoryRegionsAccess();
		this.openInventory = false;
		this.openWorkbench = false;
		game.gameState = STATE.Game;
		this.setEntityMessageVisible(false);
	}
	
}
