package Menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import Entities.Craftable;
import Entities.Entity;
import Entities.EntityID;
import Tiles.Tile;
import Tiles.TileMap;
import main.Game;

public class Inventory {

	//The inventory as a whole, the entities and their respective amounts
	public HashMap<EntityID, Integer> inventory = new HashMap<EntityID, Integer>();
	
	//A list of all entities in the inventory : To keep count of how many individual items are in the inventory
	public ArrayList<EntityID> TOTALInventory = new ArrayList<>();
	
	//list of the entities or craftables in the inventory
	public ArrayList<Object> listInventory = new ArrayList<>();
	
	//List of the gear currently being worn by the player along with the designated tile
	public HashMap<Tile, Object> equippedGear = new HashMap<Tile, Object>();
	
	//Hashmap of the inventory slots and their position
	public HashMap<Integer, EntityID> inventorySlots = new HashMap<Integer, EntityID>();
	
	//Houses all the individual tiles
	public Tile[] inventoryRegions = new Tile[9];
	
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
	
	
	public void render(Graphics g) {
//		g.drawImage(Game.inventoryIMG, 20, 20, null);
		g.drawImage(Game.inventoryIMG, inventoryX-100, inventoryY, null);
		
		g.setColor(Color.DARK_GRAY);
		g.setFont(new Font("Courier", 1, 25));
		
		
		int counter = 0;
//		for(EntityID key : inventory.keySet()) {
		for(int i = 0; i < inventory.size(); i++) {
			//Counts til the end of the page of inventory space
			if(counter > 9) {
				break;
			}
			//Checking whether the item is a craftable or entity so we can properly get the name and entityid
			try {
				if(listInventory.get(i).getClass().getSuperclass() == Entity.class) {
					
					String ent = ((Entity) listInventory.get(i)).getEntityID().toString().substring(6, ((Entity) listInventory.get(i)).getEntityID().toString().length());
					g.drawImage(((Entity) listInventory.get(i)).getImage(), (inventoryX+300)-170, (inventoryY+105)+counter*70, 32, 32, null);
					//this line
					g.drawString(ent + " : "+inventory.get(((Entity) listInventory.get(i)).getEntityID()), (inventoryX+300)-100, (inventoryY+125)+counter*70);
				}else if(listInventory.get(i).getClass().getSuperclass() == Craftable.class) {
					String ent = ((Craftable) listInventory.get(i)).getEntityID().toString().substring(6, ((Craftable) listInventory.get(i)).getEntityID().toString().length());
					g.drawImage(((Craftable) listInventory.get(i)).getImage(), (inventoryX+300)-170, (inventoryY+105)+counter*70, 32, 32, null);
					g.drawString(ent + " : "+inventory.get(((Craftable) listInventory.get(i)).getEntityID()), (inventoryX+300)-100, (inventoryY+125)+counter*70);
					
				}
			}catch(Exception e) {
				System.out.println("INVENTORY: " + e + " : ");
				e.printStackTrace();
				//This is most likely the issue here inventory.get(((Entity) listInventory.get(i)).getEntityID())
			}
			
			
			
			counter+=1;
			
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
					System.out.println("INVENTORY: " + e + " : ");
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
	
	public void addItem(Entity e) {
		if(inventory.containsKey(e.getEntityID())) {
			inventory.put(e.getEntityID(), inventory.get(e.getEntityID())+1);
			TOTALInventory.add(e.getEntityID());
		}else {
			TOTALInventory.add(e.getEntityID());
			listInventory.add(e);
			inventory.put(e.getEntityID(), 1);
		}
		
	}
	
	public void addItem(Craftable c) {
		if(inventory.containsKey(c.getEntityID())) {
			inventory.put(c.getEntityID(), inventory.get(c.getEntityID())+1);
			TOTALInventory.add(c.getEntityID());
		}else {
			TOTALInventory.add(c.getEntityID());
			listInventory.add(c);
			inventory.put(c.getEntityID(), 1);
		}
		
		
	}
	
	public Entity getOffhand() {
		if(Gear[4] != null) {
			return (Entity) Gear[4];
		}
		return null;
	}
	
	
	public void deleteItem(EntityID e) {
		
		if(inventory.get(e)-1 == 0) {
			inventory.remove(e);
			
			
			for(int i = 0; i < listInventory.size(); i++) {
				//Checking to see if the item in the iteration is a entity or craftable
				if(listInventory.get(i).getClass().getSuperclass() == Entity.class) {
					//Casting entity to the object to then check if their entityids match, if so remove the item from the inventory
					if(((Entity) listInventory.get(i)).getEntityID() == e) {
						listInventory.remove(listInventory.get(i));
					}
				}else if(listInventory.get(i).getClass().getSuperclass() == Craftable.class) {
					//maybe crafting with craftables down the road
					if(((Craftable) listInventory.get(i)).getEntityID() == e) {
						listInventory.remove(listInventory.get(i));
					}
				}
				
			}
		}else {
			inventory.put(e, inventory.get(e)-1);
		}
		
		TOTALInventory.remove(e);
	}
	
	public void createSlots(TileMap tileMap) {
		//Inventory slots
		if(listInventory.size() < 9) {
			for(int i = 0; i < listInventory.size(); i++) {
				Tile t = new Tile(60, 500, inventoryX+50, (inventoryY+90)+i*70);
				inventoryRegions[i] = t;
				tileMap.inventoryTiles.add(t);
				if(!(itemLocation.containsKey(t))) {
					//Check the class identity to set the slot
					if(listInventory.get(i).getClass().getSuperclass() == Entity.class) {
						itemLocation.put(t, ((Entity) listInventory.get(i)));
					}else {
						itemLocation.put(t, ((Craftable) listInventory.get(i)));
					}
					
				}
				
				
			}
		}else {
			for(int i = 0; i < 9; i++) {
				Tile t = new Tile(60, 500, inventoryX+50, (inventoryY+90)+i*70);
				inventoryRegions[i] = t;
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
	
	//Returns 
	public void checkInventorySize(Graphics g) {
		if(listInventory.size() < 9) {
			for(int i = 0; i < listInventory.size(); i++) {
				Tile t = inventoryRegions[i];
				t.render(g);
			}
		}else {
			for(int i = 0; i < 9; i++) {
				Tile t = inventoryRegions[i];
				t.render(g);
			}
		}
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
	
	//Removes items of the recipe list when crafting items
	public void removeCraftMaterials(List<EntityID> recipe) {
		for(int i = 0; i < recipe.size(); i++) {
			deleteItem(recipe.get(i));
		}
	}
	
	//Equipping gear (VERY SIMPLE) WITH MORE STEPS IN THE MOUSEINPUT FILE
	public void EquipGear(Tile t, Object o, int placement) {
		equippedGear.put(t, o);
		Gear[placement] = o;
	}
	
	public void removeGear(Tile t, Object o, int placement) {
		if(t == null) {
			equippedGear.remove(o);
		}else {
			equippedGear.remove(t);
		}
		
		Gear[placement] = null;
	}
	
}
