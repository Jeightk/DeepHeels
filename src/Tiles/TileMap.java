package Tiles;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import Entities.EntityID;
import GameObjects.Plant;
import GameObjects.TilledSoil;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class TileMap {

	public int tilesWidth = Game.WIDTH/32;
	public int tilesHeight = Game.HEIGHT/32;
	
	private Handler handler;
	
	public List<Tile> tileList = new ArrayList<Tile>();
	
	public List<Tile> farmingTileList = new ArrayList<Tile>();
	
	public List<Tile> inventoryTiles = new ArrayList<Tile>();
	//List for the gear and weapons/offhands
	public List<Tile> inventoryGearTiles = new ArrayList<Tile>();
	
	public List<Tile> workbenchTiles = new ArrayList<Tile>();
	
	public List<Tile> TilledSoilTiles = new ArrayList<Tile>();
	
	public TileMap(Handler handler) {
		this.handler = handler;
	}
	
	public void setTiles() {
		
//		Tile t = null;
		
		
		for(GameObject tObject : handler.object.toArray(new GameObject[0])) {
			if(tObject.getID() == ID.Plant) {
				Tile t = new Tile(tObject.getSize(), tObject.getSize(), (tObject.getX()/tObject.getSize())*tObject.getSize(), (tObject.getY()/tObject.getSize())*tObject.getSize());
				tileList.add(t);

			}
		}

		
		
		
		
	}
	private boolean setFarmTile = false;
	public void setFarmableTiles() {
		for(int i = 0; i < (Game.HEIGHT/32)-2; i++) {
			for(int j = 0; j < (Game.WIDTH/32)-2; j++) {
				
				
				Tile newTile = new Tile(32, 32, j*32, i*32);
				farmingTileList.add(newTile);
				
				for(Tile t : tileList) {
					if(t.getBounds().contains(newTile.getBounds())) {
						farmingTileList.remove(newTile);
					}
				}
//				for(GameObject tObject : handler.object.toArray(new GameObject[0])) {
//					if(tObject.getID() == ID.Plant) {
//						if(tObject.getBound().contains(j,i)) {
//							Tile t = new Tile(tObject.getSize(), tObject.getSize(), j*tObject.getSize(), i*tObject.getSize());
//						}
//					}else {
//						Tile t = new Tile(32, 32, j*32, i*32);
//						farmingTileList.add(t);
//					}
//				}
				
				
			}
			
		}
	}
	
	//This is for when planting seeds, we delete the previous tilled soil tile and we need to find the closest farmable tile to reset the tilled soil tile in along with seed
	public Tile getClosestFarmableTile(int xpos, int ypos) {
		for(int i = 0; i < farmingTileList.size(); i++) {
			if(farmingTileList.get(i).getBounds().contains(xpos, ypos)) {
				return farmingTileList.get(i);
			}
		}
		return null;
	}
	
	public void deselectAllTiles(List<Tile> tiles) {
		for(int i = 0; i < tiles.size(); i++) {
			tiles.get(i).isSelected = false;
		}
	}
	
	public void render(Graphics g) {
		
		for(int i = 0; i < tileList.size(); i++) {
			Tile t = tileList.get(i);
			t.render(g);
		}
		
	}
	
	public void renderFarmableTiles(Graphics g) {
		for(int i = 0; i < farmingTileList.size(); i++) {
			Tile t = farmingTileList.get(i);
			t.render(g);
		}
	}
	
	public void renderInventoryTiles(Graphics g) {
		for(int i = 0; i < inventoryTiles.size(); i++) {
			
			Tile t = inventoryTiles.get(i);
			t.render(g);
		}
	}
	
	public void renderInventoryGearTiles(Graphics g) {
		for(int i = 0; i < inventoryGearTiles.size(); i++) {
			Tile t = inventoryGearTiles.get(i);
			t.render(g);
		}
	}
	
	public void renderWorkbenchTiles(Graphics g) {
		for(int i = 0; i < workbenchTiles.size(); i++) {
			Tile t = workbenchTiles.get(i);
			t.render(g);
		}
	}
	
	public int getTilePositionInArray(List<Tile> t, Tile tile) {
		
		for(int i = 0; i < t.size(); i++) {
			if(t.get(i) == tile) {
				return i;
			}
		}
		System.out.println("That tile does not exist??");
		return 0;
	}


	//Later down the road this could be re-worked, I think the display of the tiles is actually in the rendering and not based off of the farmingmode value.
	//Soo... maybe could remove the farmingmode value all together from this method and just have clearing and then readding to the lists. but
	// to lazy to test that right now so we can jus keep it this way.
	public void updateTiles(boolean farmingmode){
		System.out.println("Updating tiles ! Farming-Mode: " + farmingmode);
		this.tileList.clear();
		this.farmingTileList.clear();
		if(farmingmode){
			this.setTiles();
			this.setFarmableTiles();
		}else{
			this.setTiles();
		}
	}
	
}
