package Menus;

import java.awt.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import Entities.*;
import Tiles.Tile;
import Tiles.TileMap;
import main.Game;

public class Inventory {

	//The inventory as a whole, the entities and their respective amounts
	public ConcurrentHashMap<EntityID, Integer> inventory = new ConcurrentHashMap<EntityID, Integer>();

	//List of the gear currently being worn by the player along with the designated tile
	public ConcurrentHashMap<Tile, Object> equippedGear = new ConcurrentHashMap<Tile, Object>();
	
	//Houses each gear piece  for saving the position of gear after closing of the inventory
	public Object[] Gear = new Object[6];
	
	
	
	//Assigning inventory tiles to entities
	public HashMap<Tile, Object> itemLocation = new HashMap<Tile, Object>();
	
	//Entity messages when clicked
	public boolean EntityMessage = false;
	public EntityID entityMessageID = null;
	public int mouseX = 0;
	public int mouseY = 0;
	
	int inventoryX = Game.WIDTH/2-300;
	int inventoryY = 20;

	private EntityHandler entityHandler;
	private CraftableHandler craftableHandler;

	public Inventory(EntityHandler entityHandler, CraftableHandler craftableHandler){
		this.entityHandler = entityHandler;
		this.craftableHandler = craftableHandler;
	}


	//TODO: Need to fix the rendering of inventory on window maximize
	public void render(Graphics g) {
//		g.drawImage(Game.inventoryIMG, 20, 20, null);
		g.drawImage(Game.inventoryIMG, inventoryX-100, inventoryY, null);
		
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Monospaced", 1, 25));
		
		
		int counter = 0;

		try{
			for(EntityID items : inventory.keySet()){
				if(counter > 9){
					break;
				}

				try{
					//Draws the image of the item and then draws the name of the item along with the amount
					g.drawImage(findImageRelatedToEntityID(items), (inventoryX+300)-170, (inventoryY+105)+counter*70, 32, 32, null);
					g.drawString(items.toString().substring(6,items.toString().length()) + " : "+inventory.get(items), (inventoryX+300)-100, (inventoryY+125)+counter*70);


				}catch(Exception e){
					System.out.println("Inventory bug");
					e.printStackTrace();
				}

				counter+=1;
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			for(Tile t : equippedGear.keySet()) {
				try {
					if(equippedGear.get(t) != null) {
						if(ifCraftable(equippedGear.get(t))) {
							g.drawImage(((Craftable) equippedGear.get(t)).getImage(), t.getX(), t.getY(), 58, 58, null);
						}else {
							g.drawImage(((Entity) equippedGear.get(t)).getImage(), t.getX(), t.getY(), 58, 58, null);
						}
						
					}
				}catch(Exception e) {
					System.out.println("Gear bug");
					e.printStackTrace();
				}
				
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
//		checkInventorySize(g);
		
		
		if(EntityMessage) {
			renderEntityMessage(g);
		}
		
	}


	public Image findImageRelatedToEntityID(EntityID entityID){
		for(Entity entity: entityHandler.ENTITIES){
			if(entity.getEntityID() == entityID){
				return entity.getImage();
			}
		}
		for(Craftable craftable: craftableHandler.craftable){
			if(craftable.getEntityID() == entityID){
				return craftable.getImage();
			}
		}
		return null;
	}

	public Object findObjectRelatedToEntityID(EntityID entityID){
		for(Entity entity: entityHandler.ENTITIES){
			if(entity.getEntityID() == entityID){
				return entity;
			}
		}
		for(Craftable craftable: craftableHandler.craftable){
			if(craftable.getEntityID() == entityID){
				return craftable;
			}
		}
		return null;
	}

	public EntityType findEntityTypeRelatedToEntityID(EntityID entityID){
		for(Entity entity: entityHandler.ENTITIES){
			if(entity.getEntityID() == entityID){
				return entity.getEntityType();
			}
		}
		for(Craftable craftable: craftableHandler.craftable){
			if(craftable.getEntityID() == entityID){
				return craftable.getEntityType();
			}
		}
		return null;
	}

	public void addItem(EntityID e) {
		synchronized (inventory){
			if(inventory.containsKey(e)) {
				inventory.put(e, inventory.get(e)+1);
			}else {
				inventory.put(e, 1);
			}
		}

	}

	
	public Entity getOffhand() {
		if(Gear[4] != null) {
			return (Entity) Gear[4];
		}
		return null;
	}
	
	
	public void deleteItem(EntityID e) {
		synchronized (inventory){
			if(inventory.get(e)-1 == 0) {
				inventory.remove(e);

			}else {
				inventory.put(e, inventory.get(e)-1);
			}
		}
	}
	
	public void createSlots(TileMap tileMap) {
		//Inventory slots [limited to 9 for now]
		if(inventory.size() < 9) {
			int count = 0;

			//Cycles through all the items in the inventory and then assigns them to inventory tiles
			for(EntityID item : inventory.keySet()){
				Tile t = new Tile(60, 500, inventoryX+50, (inventoryY+90)+count*70);
				tileMap.inventoryTiles.add(t);

				//CHecks to make sure the tile doesnt already exist
				if(!(itemLocation.containsKey(t))) {

					//Sets the location of the object in the inventory.
					itemLocation.put(t, findObjectRelatedToEntityID(item));


				}
				count+=1;
			}
		}else {
			for(int i = 0; i < 9; i++) {
				Tile t = new Tile(60, 500, inventoryX+50, (inventoryY+90)+i*70);
				tileMap.inventoryTiles.add(t);
				
			}
		}
		
		for(int i = 0; i < 6; i++) {
			Tile t = new Tile(64, 64, inventoryX-78, (inventoryY+30)+i*80);
			tileMap.inventoryGearTiles.add(t);
			
			if(!(equippedGear.containsKey(t))) {
				
				if(Gear[i] != null) {
					if(Gear[i].getClass().getSuperclass() == Entity.class) {
						equippedGear.put(t, ((Entity) Gear[i]));
					}else {
						equippedGear.put(t, ((Craftable) Gear[i]));
					}
				}
				
				
			}
			
			
		}
		
		
		
//		for(int i = 0; i < 6; i++) {
//			Tile t = new Tile(100, 100, inventoryX-75, inventoryY+25);
//		}
		
		
	}
	
	public boolean ifCraftable(Object ItemSelected) {
		if(ItemSelected.getClass().getSuperclass() == Entity.class) {
			return false;
		}else if(ItemSelected.getClass().getSuperclass() == Craftable.class) {
			return true;
		}
		return false;
	}
	
	public void renderEntityMessage(Graphics g) {
		
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Courier", 1, 15));
		if(entityMessageID == EntityID.EntityElderberry) {
			g.drawString("The Elderberry, can be used as a latherable toxin on the end of a weapon.", inventoryX+600, mouseY);
		}else if(entityMessageID == EntityID.EntityFlamecrest) {
			g.drawString("The Flamecrest, said to be used by the one true king", inventoryX+600, mouseY);
		}else if(entityMessageID == EntityID.EntityIvoriane) {
			g.drawString("The Ivoriane, a very sturdy material usually used as shafts and handles for tools.", inventoryX+600, mouseY);
		}
	}
	
	//Removes items of the recipe list when crafting items TODO: Make this so it removes bulk at a time
	public void removeCraftMaterials(Map<EntityID, Integer> recipe) {
		for(EntityID ingredient : recipe.keySet()){
			for(int i = 0; i<recipe.get(ingredient); i++){
				deleteItem(ingredient);
			}
		}
	}
	
	//Equipping gear (VERY SIMPLE) WITH MORE STEPS IN THE MOUSEINPUT FILE
	public void EquipGear(Tile t, Object o, int placement) {
		synchronized (equippedGear){
			equippedGear.put(t, o);
			Gear[placement] = o;
		}
	}
	
	public void removeGear(Tile t, Object o, int placement) {
		synchronized (equippedGear){
			if(t == null) {
				equippedGear.remove(o);
			}else {
				equippedGear.remove(t);
			}

			Gear[placement] = null;
		}

	}

	
}
