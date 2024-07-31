package Tools;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import Entities.*;
import Entities.Seeds.EntityFlamecrestSeed;
import Entities.Seeds.EntityLilypatSeed;
import Entities.Seeds.EntityViperusSeed;
import GameObjects.Enemy;
import GameObjects.Plant;
import GameObjects.Player;
import GameObjects.TilledSoil;
import Levels.LevelHandler;
import Menus.Inventory;
import Menus.MainMenu;
import Menus.CharacterSelect;
import Menus.Menus;
import Menus.LoadSave;
import Menus.WorkbenchMenu;
import Tiles.Tile;
import Tiles.TileMap;
import main.Game;
import main.Game.STATE;
import main.GameObject;
import main.Handler;
import main.ID;

public class MouseInput extends MouseAdapter implements MouseMotionListener{

	private Game game;
	private Handler handler;
	
	private TileMap tileMap;
	
	private Tile oldTile;
	
	private Menus menus;
	private Inventory inventory;
	private WorkbenchMenu workbenchmenu;
	private EntityHandler entityHandler;
	private MainMenu mainmenu;
	private PlantGrowth plantGrowth;
	private LevelHandler levelHandler;
	private MapRender mapRender;
	private CharacterSelect characterSelect;
	private LoadSave loadSave;

	private GameObject player;
	private boolean clickedItemCraftable = false;
	private boolean clickedItemEntity = false;
	
	private int mouseX = 0;
	private int mouseY = 0;
	
	private Craftable c;
	private Entity inventoryClickE;
	
	private boolean renderHighlight = false;
	
	public enum clickedSTATE{
		ITEM,
		NOITEM
	}
	
	private clickedSTATE clickState = clickedSTATE.NOITEM;
	private Object itemSelected = null;
	private EntityType itemType = null;
	private boolean renderAvailableTillSoil = false;

	public boolean FarmingMode;

	public MouseInput(Handler handler, TileMap tileMap, Inventory inventory, Menus menus, WorkbenchMenu workbenchmenu, EntityHandler entityHandler, MainMenu mainmenu, PlantGrowth plantGrowth, Game game, LevelHandler levelHandler, MapRender mapRender, CharacterSelect characterSelect, LoadSave loadSave) {
		this.game = game;
		this.handler = handler;
		this.tileMap = tileMap;
		this.inventory = inventory;
		this.menus = menus;
		this.workbenchmenu = workbenchmenu;
		this.entityHandler = entityHandler;
		this.mainmenu = mainmenu;
		this.plantGrowth = plantGrowth;
		this.levelHandler = levelHandler;
		this.mapRender = mapRender;
		this.characterSelect = characterSelect;
		this.loadSave = loadSave;
		FarmingMode = plantGrowth.getFarmingMode();

		player = handler.getPlayer();
	}
	
	
	
	public void render(Graphics g) {
		
		if(game.gameState == STATE.Inventory) {
			if(clickedItemCraftable) {
				g.drawImage(c.getImage(), this.getMouseX(), this.getMouseY(), 32, 32, null);
			}else if(clickedItemEntity) {
				g.drawImage(inventoryClickE.getImage(), this.getMouseX(), this.getMouseY(), 32, 32, null);
			}
		}
		
		if(renderAvailableTillSoil) {
			g.setFont(new Font("Dialog", Font.BOLD, 20));
			g.setColor(Color.white);
			g.drawString(game.getTillableLandAmount()+ "", mouseX, mouseY-10);
		}
		
	}
	
	public void mousePressed(MouseEvent e) {
		int mouseX=e.getX();
		int mouseY=e.getY();
		if(player == null){
			this.player = handler.getPlayer();
		}

		if(game.gameState == Game.STATE.Game) {
			
			//Planting seeds
			if(inventory.getOffhand() != null && !handler.isSeeded(mouseX, mouseY, ID.TilledSoil)) {
				if(handler.getTile(mouseX, mouseY) == ID.TilledSoil && inventory.getOffhand().getEntityID() == EntityID.EntityFlamecrestSeed) {
					handler.removeObject(handler.getExactObject(mouseX, mouseY, ID.TilledSoil));
					handler.addObject(new TilledSoil(tileMap.getClosestFarmableTile(mouseX, mouseY).getX(), tileMap.getClosestFarmableTile(mouseX, mouseY).getY(), ID.TilledSoil, EntityID.EntityFlamecrestSeed, 0), 1);
					inventory.removeGear(null, EntityFlamecrestSeed.class, 4);
					plantGrowth.startGrowth(handler.getExactObject(mouseX, mouseY, ID.TilledSoil), EntityID.EntityFlamecrestSeed, tileMap.getClosestFarmableTile(mouseX, mouseY).getX(), tileMap.getClosestFarmableTile(mouseX, mouseY).getY());

				}else if(handler.getTile(mouseX, mouseY) == ID.TilledSoil && inventory.getOffhand().getEntityID() == EntityID.EntityViperusSeed){
					handler.removeObject(handler.getExactObject(mouseX, mouseY, ID.TilledSoil));
					handler.addObject(new TilledSoil(tileMap.getClosestFarmableTile(mouseX, mouseY).getX(), tileMap.getClosestFarmableTile(mouseX, mouseY).getY(), ID.TilledSoil, EntityID.EntityViperusSeed, 0), 1);
					inventory.removeGear(null, EntityViperusSeed.class, 4);
					plantGrowth.startGrowth(handler.getExactObject(mouseX, mouseY, ID.TilledSoil), EntityID.EntityViperusSeed, tileMap.getClosestFarmableTile(mouseX, mouseY).getX(), tileMap.getClosestFarmableTile(mouseX, mouseY).getY());

				}else if(handler.getTile(mouseX, mouseY) == ID.TilledSoil && inventory.getOffhand().getEntityID() == EntityID.EntityLilypatSeed){
					handler.removeObject(handler.getExactObject(mouseX, mouseY, ID.TilledSoil));
					handler.addObject(new TilledSoil(tileMap.getClosestFarmableTile(mouseX, mouseY).getX(), tileMap.getClosestFarmableTile(mouseX, mouseY).getY(), ID.TilledSoil, EntityID.EntityLilypatSeed, 0), 1);
					inventory.removeGear(null, EntityLilypatSeed.class, 4);
					plantGrowth.startGrowth(handler.getExactObject(mouseX, mouseY, ID.TilledSoil), EntityID.EntityLilypatSeed, tileMap.getClosestFarmableTile(mouseX, mouseY).getX(), tileMap.getClosestFarmableTile(mouseX, mouseY).getY());
				}
			}
			
			
			
			//Error here needs to be fixed.
			if(FarmingMode == true) {
				if (handler.getObject(ID.TilledSoil) == null || !(handler.getTile(mouseX, mouseY) == ID.TilledSoil)){
					if(game.getTillableLandAmount() > 0) {
						for(int i = 0; i < tileMap.farmingTileList.size(); i++) {
							if(tileMap.farmingTileList.get(i).isSelected) {
								handler.addObject(new TilledSoil(tileMap.farmingTileList.get(i).getX(), tileMap.farmingTileList.get(i).getY(), ID.TilledSoil, null, 0), 1);
								game.setTillableLandAmount(game.getTillableLandAmount()-1);
								
							}
						}
					}
					
				}
			}
			
			
			
			
			
			for(GameObject tObject : handler.object.toArray(new GameObject[0])) {
				
				if(tObject == null) {
					return;
				}
				
				//Harvesting plants
				if(tObject.getID() == ID.Plant && FarmingMode == false) {
					if(tObject.getBound().contains(mouseX, mouseY) && ((Plant)tObject).getType() == "Producer"){
						tObject.whenClicked();
						Tile t = new Tile(tObject.getSize(), tObject.getSize(), (tObject.getX()/tObject.getSize())*tObject.getSize(), (tObject.getY()/tObject.getSize())*tObject.getSize());
						for(int i = 0; i < tileMap.tileList.size(); i++) {
							if(tileMap.tileList.get(i).getBounds().equals(t.getBounds())) {
								tileMap.tileList.remove(tileMap.tileList.get(i));
							}
						}
						handler.queueRemove(tObject);
					}
					
//					
				}else {
//					//Attacking mobs
					if(((Player)player).punchBound().intersects(tObject.getBound())) {
						//Checking if attacking object is an enemy
						if(tObject.getID() == ID.Enemy) {
							//if Hp is lower than 0 after subtract of fist dmg then remove object, else remove 10 hp.
							if(tObject.getHP() - ((Player)player).getDamage() <= 0) {
								((Enemy)tObject).objectCleanup();
								entityHandler.addMultipleEntities(((Enemy)tObject).getItemDrops());
							}else {
								tObject.setHP(tObject.getHP()-((Player) player).getDamage());
//								System.out.println(tObject.getHP());
							}
							
							//pushes the mob back 10 pixels away from the player
							if(player.getX() < tObject.getX()) {
								tObject.setX(tObject.getX()+10);
							}else {
								tObject.setX(tObject.getX()-10);
							}
							
//						//Opening workbench
						}else if(tObject.getID() == ID.Workbench) {
							if(tObject.getBound().contains(mouseX, mouseY) && menus.openInventory == false && menus.openWorkbench == false){
								menus.openWorkbench = true;
								game.gameState = STATE.Workbench;
								workbenchmenu.clearCraftablesList();
								workbenchmenu.findCraftables();
								menus.workbenchRegionsAccess();
							}else {
								menus.openInventory = false;
								menus.openWorkbench = false;
								game.gameState = STATE.Game;
							}
						}
					//Clicking on plane tile
					}
				}
			}
		//Inventory clicking of items
		}else if(game.gameState == Game.STATE.Inventory) {
			
			
			
			for(int i = 0; i < inventory.listInventory.size(); i++) {
				
				
				
				if(tileMap.inventoryTiles.get(i).isSelected) {
					System.out.println(inventory.itemLocation.get(tileMap.inventoryTiles.get(i)));
					
					try {
						itemSelected = (Entity) inventory.itemLocation.get(tileMap.inventoryTiles.get(i));
						inventory.entityMessageID = ((Entity) itemSelected).getEntityID();
						
						if((((Entity) itemSelected).getEntityType() == EntityType.Seed)) {
							inventoryClickE = (Entity) itemSelected;
							clickedItemEntity = true;
							itemType = inventoryClickE.getEntityType();
						}
						
						c = null;
						clickedItemCraftable = false;
						clickState = clickedSTATE.ITEM;
						
						return;
						
					}catch(Exception e1) {
						itemSelected = (Craftable) inventory.itemLocation.get(tileMap.inventoryTiles.get(i));
						inventory.entityMessageID = ((Craftable) itemSelected).getEntityID();
						
						//Clicking items in inventory to equip
						if((((Craftable) itemSelected).getEntityType() == EntityType.Weapon) || (((Craftable) itemSelected).getEntityType() == EntityType.Armor)){
							c = (Craftable) itemSelected;
							clickedItemCraftable = true;
							itemType = c.getEntityType();
							
							inventoryClickE = null;
							clickedItemEntity = false;
							clickState = clickedSTATE.ITEM;
							
							return;
						}
						
					}
					
					
						
					
					inventory.EntityMessage = true;
					
					inventory.mouseX = mouseX;
					inventory.mouseY = mouseY;
				}
			}
			//Eqipping weapons or armor
			for(int i = 0; i < tileMap.inventoryGearTiles.size(); i++) {
				if(tileMap.inventoryGearTiles.get(i).isSelected) {
					
					if(clickState == clickedSTATE.ITEM) {
						
						//Equipping gear on click
						if((tileMap.inventoryGearTiles.get(i) == tileMap.inventoryGearTiles.get(5)) && itemType == EntityType.Weapon) {
							
							System.out.println("5: " + inventory.equippedGear.containsValue(5));
							if(inventory.equippedGear.containsValue(5)) {
								System.out.println("THIS CONTAINS ITEM ALREADY!");
								return;
							}
							
							gearEquip(i, 5);
							menus.inventoryRegionsAccess();
							((Player)player).updateGear();
							
							//equipping fArming tool
							if(c.getEntityID() == EntityID.EntityTill) {
								tileMap.farmingTileList.clear();
//								tileMap.tileList.clear();
								tileMap.setFarmableTiles();
								tileMap.tileList.clear();
								plantGrowth.setFarmingMode(true);
								this.FarmingMode = true;
								renderAvailableTillSoil = true;
							}
							
							itemType = null;
							
						//Equipping offhand items
						}else if((tileMap.inventoryGearTiles.get(i) == tileMap.inventoryGearTiles.get(4)) && itemType == EntityType.Seed) {
							
							System.out.println("EntityType + " + itemType);
							System.out.println("4: " + inventory.equippedGear.containsValue(4));
							if(inventory.equippedGear.containsValue(4) || itemType != EntityType.Seed) {
								System.out.println("THIS CONTAINS ITEM ALREADY!");
								return;
							}
							
							gearEquip(i, 4);
							menus.inventoryRegionsAccess();
							((Player)player).updateGear();
							
							itemType = null;
							
						}else {
							//returns if the user tries to equip in the wrong slot
							return;
						}
						
						clickState = clickedSTATE.NOITEM;
						return;
						
					}else {
						//Getting the item that is clicked
						
						if(inventory.equippedGear.containsKey(tileMap.inventoryGearTiles.get(i))) {
							
							System.out.println("THIS IS THE ITEM: " + inventory.equippedGear.get(tileMap.inventoryGearTiles.get(i)));
							
							if(inventory.ifCraftable(inventory.equippedGear.get(tileMap.inventoryGearTiles.get(i)))) {
								System.out.println("looking at til");
								clickedItemCraftable = true;
								clickState = clickedSTATE.ITEM;
								c = (Craftable) inventory.equippedGear.get(tileMap.inventoryGearTiles.get(i));
								inventoryClickE = null;
							}else {
								clickedItemEntity = true;
								clickState = clickedSTATE.ITEM;
								inventoryClickE = (Entity) inventory.equippedGear.get(tileMap.inventoryGearTiles.get(i));
								c = null;
							}
							
							
							return;
						}
					}
					
				}
			}
			
			if(clickState == clickedSTATE.ITEM) {
				//Unequipping gear and weapons' and placing into inventory
				
				//Unequipping craftables
				if(c != null) {
					System.out.println("testersmega" + c.getName());
					if(inventory.equippedGear.containsValue(c)) {
						for(Tile t : inventory.equippedGear.keySet()) {
							if(inventory.equippedGear.get(t) == c) {
								inventory.removeGear(t, c, tileMap.getTilePositionInArray(tileMap.inventoryGearTiles, t));
								inventory.addItem(c);
								
								//When unequipping the farming tool
								if(c.getEntityID() == EntityID.EntityTill) {
									tileMap.tileList.clear();
									tileMap.setTiles();
									tileMap.deselectAllTiles(tileMap.farmingTileList);
									plantGrowth.setFarmingMode(false);
									this.FarmingMode = false;
									renderAvailableTillSoil = false;
								}
								
								clickedItemCraftable = false;
								clickState = clickedSTATE.NOITEM;
								menus.inventoryRegionsAccess();
								((Player)player).updateGear();
								
								return;
							}
						}
					//Picking up items in inventory and placing back into inventory
					}else {
						clickedItemCraftable = false;
						clickedItemEntity = false;
						clickState = clickedSTATE.NOITEM;
					}
				//Unequipping entities
				}else if(inventoryClickE != null) {
					System.out.println("testersBIG");
					if(inventory.equippedGear.containsValue(inventoryClickE)) {
						for(Tile t : inventory.equippedGear.keySet()) {
							if(inventory.equippedGear.get(t) == inventoryClickE) {
								System.out.println("testers");
								inventory.removeGear(t, inventoryClickE, tileMap.getTilePositionInArray(tileMap.inventoryGearTiles, t));
								inventory.addItem(inventoryClickE);
								clickedItemEntity = false;
								clickState = clickedSTATE.NOITEM;
								menus.inventoryRegionsAccess();
								((Player)player).updateGear();
								
								//When unequipping the farming tool
								if(inventoryClickE.getEntityID() == EntityID.EntityTill) {
									tileMap.tileList.clear();
									tileMap.setTiles();
									tileMap.deselectAllTiles(tileMap.farmingTileList);
									plantGrowth.setFarmingMode(false);
									this.FarmingMode = false;
								}
								
								return;
							}
						}
					//Picking up items in inventory and placing back into inventory
					}else {
						clickedItemCraftable = false;
						clickedItemEntity = false;
						clickState = clickedSTATE.NOITEM;
					}
				}
				
				
				
			}
			
		//Workbench crafting items
		}else if(game.gameState == Game.STATE.Workbench) {
			//When clicked it loads the next level
			if(workbenchmenu.nextlevelhover){
				game.gameState = STATE.Game;
				levelHandler.countdownStart = true;
				levelHandler.startTimer();
				menus.openWorkbench = false;
				menus.setEntityMessageVisible(false);
			}

			for(int i = 0; i < workbenchmenu.craftables.size(); i++) {
				if(tileMap.workbenchTiles.get(i).isSelected) {
					Craftable c = workbenchmenu.craftableLocation.get(tileMap.workbenchTiles.get(i));
					if(c == null) {
						return;
					}
					inventory.addItem(c);
					inventory.removeCraftMaterials(c.getRecipe());
					workbenchmenu.findCraftables();
					menus.workbenchRegionsAccess();
				}
			}
		}else if(game.gameState == Game.STATE.Menu) {


			if(characterSelect.isActive){ // Selecting a character
				if(mouseOver(mouseX, mouseY, Game.WIDTH / 2 - 80, Game.HEIGHT / 2 - 31, 64, 64)) { // Otis
					characterSelect.setRenderName("Otis", (Game.WIDTH / 2 - 80), (Game.HEIGHT / 2 - 31) - 5);
					characterSelect.hightlightCharacter(Game.WIDTH / 2 - 80, Game.HEIGHT / 2 - 31);
					characterSelect.permaHighlight = true;
				}else if(mouseOver(mouseX, mouseY, Game.WIDTH / 2 + 16, Game.HEIGHT / 2 - 31, 64, 64)){ // Myk
					characterSelect.setRenderName("Myk", (Game.WIDTH / 2 + 16), (Game.HEIGHT / 2 - 31) - 5);
					characterSelect.hightlightCharacter(Game.WIDTH / 2 + 16, Game.HEIGHT / 2 - 31);
					characterSelect.permaHighlight = true;
				}else if(mouseOver(mouseX, mouseY, (Game.WIDTH/2 - 400)+301, (Game.HEIGHT / 2 - 300)+485, 200, 64) && characterSelect.renderName) {
					handler.setCharacterName(characterSelect.name);
					game.gameState = Game.STATE.Game;
					mapRender.renderMap();
					tileMap.setTiles();
//					System.out.println("testo");
				}else{
					characterSelect.permaHighlight = false;
					characterSelect.highlight = false;
					characterSelect.renderName = false;
				}
				return;
			}else{
				//Mouse click -- play button
				if(mouseOver(mouseX, mouseY, 122, 330, 203, 70)) {
//				game.gameState = Game.STATE.Game;
					characterSelect.isActive = true;
					mainmenu.isCharacterSelect = true;
//				mapRender.renderMap();
//				tileMap.setTiles();
				}else if(mouseOver(mouseX, mouseY, 122, 451, 203, 70)){
					loadSave.loadSaves();
				}
			}



		}
		
		
		
		
		
	}
	
	
	
	@Override
	public void mouseMoved(MouseEvent e) {
		this.mouseX = e.getX();
		this.mouseY = e.getY();
		if(game.gameState == Game.STATE.Game) {
			//When farming mode is activated render the farmable tiles
			if(FarmingMode) {
				for(int i = 0; i < tileMap.farmingTileList.size(); i++) {
					if(tileMap.farmingTileList.get(i).getBounds().contains(mouseX, mouseY)) {
						tileMap.farmingTileList.get(i).isSelected = true;
					}else {
						tileMap.farmingTileList.get(i).isSelected = false;
					}
				}
				
			//render the normal tiles when farming mode is not activated
			}else {
				for(int i = 0; i < tileMap.tileList.size(); i++) {
					if(tileMap.tileList.get(i).getBounds().contains(mouseX, mouseY)) {
						tileMap.tileList.get(i).isSelected = true;
					}else {
						tileMap.tileList.get(i).isSelected = false;
					}
				}
			}
			
			
		}else if(game.gameState == Game.STATE.Inventory) {
			//The inventory slots
			for(int i = 0; i < tileMap.inventoryTiles.size(); i++) {
				if(tileMap.inventoryTiles.get(i).getBounds().contains(mouseX, mouseY)) {
					tileMap.inventoryTiles.get(i).isSelected = true;
				}else {
					tileMap.inventoryTiles.get(i).isSelected = false;
				}
			}
			//The equipment slots
			for(int i = 0; i < tileMap.inventoryGearTiles.size(); i++) {
				if(tileMap.inventoryGearTiles.get(i).getBounds().contains(mouseX, mouseY)) {
					tileMap.inventoryGearTiles.get(i).isSelected = true;
				}else {
					tileMap.inventoryGearTiles.get(i).isSelected = false;
				}
			}
		}else if(game.gameState == Game.STATE.Workbench) {
			//This is for rendering the next level button & for hovering
			if(workbenchmenu.nextLevelBounds().contains(mouseX, mouseY)){
				workbenchmenu.nextlevelhover = true;
			}else{
				workbenchmenu.nextlevelhover = false;
			}
			//rendering selected tiles in craftbench
			for(int i = 0; i < workbenchmenu.craftables.size(); i++) {
				if(tileMap.workbenchTiles.get(i).getBounds().contains(mouseX, mouseY)) {
					tileMap.workbenchTiles.get(i).isSelected = true;
				}else {
					tileMap.workbenchTiles.get(i).isSelected = false;
				}
			}
		}else if(game.gameState == Game.STATE.Menu) {
			if(characterSelect.isActive){ // CHARACTER SELECT HOVERING
				if(characterSelect.permaHighlight){
					return;
				}
				if(mouseOver(mouseX, mouseY, Game.WIDTH / 2 - 80, Game.HEIGHT / 2 - 31, 64, 64)){ // Jake
					characterSelect.hightlightCharacter(Game.WIDTH / 2 - 80, Game.HEIGHT / 2 - 31);
				}else if(mouseOver(mouseX, mouseY, Game.WIDTH / 2 + 16, Game.HEIGHT / 2 - 31, 64, 64)){ // Myk
					characterSelect.hightlightCharacter(Game.WIDTH / 2 + 16, Game.HEIGHT / 2 - 31);
				}else{
					characterSelect.highlight = false;
				}
			}else{
				if(mouseOver(mouseX, mouseY, 122, 330, 203, 70)) {
					mainmenu.renderPlayButton = true;
					mainmenu.renderLoadButton =false;
					mainmenu.renderQuitButton=false;
					mainmenu.renderAboutButton=false;
				}else if(mouseOver(mouseX, mouseY, 122, 451, 203, 70)){
					mainmenu.renderLoadButton=true;
					mainmenu.renderPlayButton=false;
					mainmenu.renderQuitButton=false;
					mainmenu.renderAboutButton=false;
				}else if(mouseOver(mouseX, mouseY, 1002, 458, 140, 53)) {
					mainmenu.renderQuitButton = true;
					mainmenu.renderLoadButton=false;
					mainmenu.renderPlayButton=false;
					mainmenu.renderAboutButton=false;
				}else if(mouseOver(mouseX, mouseY, 1024, 531, 95, 25)) {
					mainmenu.renderAboutButton=true;
					mainmenu.renderQuitButton = false;
					mainmenu.renderLoadButton=false;
					mainmenu.renderPlayButton=false;
				}else if(mainmenu.d.isMouseInside(mouseX, mouseY)) {
					mainmenu.renderLetter=true;
					mainmenu.renderQuitButton = false;
					mainmenu.renderLoadButton=false;
					mainmenu.renderPlayButton=false;
					mainmenu.renderAboutButton=false;
				}else {
					mainmenu.findLetterToRender(mouseX, mouseY);
				}
			}


		}
		
		if(clickedItemCraftable || clickedItemEntity) {
			this.setMouseX(mouseX);
			this.setMouseY(mouseY);
		}
		
		e.consume();
		
		
	}
//	private void gearUnEquip(Craftable tempItem, Entity etempItem) {
//		
//		if()
//		
//		
//	}
	
	//For equipping gear and weapons/offhands
	public void gearEquip(int i, int placement) {
		if(!(inventory.equippedGear.containsKey(tileMap.inventoryGearTiles.get(i)))) {
//			inventory.equippedGear.put(tileMap.inventoryGearTiles.get(i), itemSelected);
			inventory.EquipGear(tileMap.inventoryGearTiles.get(i), itemSelected, placement);
			
			//CHECKING IF OBJECT IS CRAFTABLE OR ENTITY BEFORE SO I CAN REMOVE IT RESPECTIVELY
			if(itemSelected.getClass().getSuperclass() == Entity.class) {
				inventory.deleteItem(((Entity)itemSelected).getEntityID());
			}else if(itemSelected.getClass().getSuperclass() == Craftable.class) {
				inventory.deleteItem(((Craftable)itemSelected).getEntityID());
			}
			clickedItemCraftable = false;
			clickedItemEntity = false;
			clickState = clickedSTATE.NOITEM;
		}
	}
	
	
	
	private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
		if(mx > x && mx < x+width) {
			if(my > y && my < y+height) {
				return true;
			}else return false;
		}else return false;
	}
	
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}
	
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public void updateEntity() {
		inventoryClickE = (Entity) itemSelected;
		clickedItemEntity = true;
		itemType = inventoryClickE.getEntityType();
	}
	
}
