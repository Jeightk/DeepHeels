package Entities;

import java.awt.Graphics;
import java.util.LinkedList;

public class CraftableHandler {

	public LinkedList<Craftable> craftable = new LinkedList<Craftable>();

	public void render(Graphics g) {
		
		for(Craftable c : craftable) {
			c.render(g);
		}
		
	}
	
	public void addCraftable(Craftable c) {
		this.craftable.add(c);
	}
	
	public void removeCraftable(Craftable c) {
		this.craftable.remove(c);
	}
}
