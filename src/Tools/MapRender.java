package Tools;

import java.util.Random;

import Entities.EntityHandler;
import GameObjects.Player;
import GameObjects.Plants.Elderberry;
import GameObjects.Plants.Flamecrest;
import GameObjects.Plants.Ivoriane;
import HUD.Healthbar;
import Menus.Inventory;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class MapRender {

	private Game game;
	private int frequency;
	private Handler handler;
	private EntityHandler entityHandler;
	
	private Inventory inventory;
	private Healthbar healthbar;
	
	private Random random;
	
	public MapRender(int frequency, Handler handler, EntityHandler entityHandler, Inventory inventory, Healthbar healthbar, Game game) {
		this.frequency = frequency;
		this.handler = handler;
		this.entityHandler = entityHandler;
		this.inventory = inventory;
		this.game = game;
		this.healthbar = healthbar;
	}
	
	
	public void renderMap() {
		random = new Random();
		for(int i = 0; i < frequency; i++) {
			
			//TODO: Figure out a layering method to render the player first then the plants then the tiles etc.
//			handler.addObject(new Elderberry(figureX(), figureY(), ID.Plant, "Elderberry", entityHandler));
			handler.addObject(new Flamecrest(figureX(), figureY(), ID.Plant, "Flamecrest", "Producer", entityHandler));
			handler.addObject(new Ivoriane(figureX(), figureY(), ID.Plant, "Ivoriane", "Producer", entityHandler));
			handler.addObject(new Ivoriane(figureX(), figureY(), ID.Plant, "Ivoriane", "Producer", entityHandler));
			handler.addObject(new Ivoriane(figureX(), figureY(), ID.Plant, "Ivoriane", "Producer", entityHandler));
			
		}
		
		handler.addObject(new Player(Game.WIDTH/2-32,150, ID.Player, game, entityHandler, inventory, healthbar, handler));
		
	}

	public void updatePlayerRender(){
		for(int i = 0; i < handler.object.size(); i++){
			if(handler.object.get(i).getID() == ID.Player){
				float tempX = handler.object.get(i).getX();
				float tempY = handler.object.get(i).getY();
				handler.removeObject(handler.object.get(i));
				handler.addObject(new Player(tempX, tempY, ID.Player, game, entityHandler, inventory, healthbar, handler));
			}
		}
	}
	
	public int figureX() {
		int findInteger = Game.WIDTH/32;
		int x = random.nextInt(findInteger);
		return x*32;
	}
	
	public int figureY() {
		int findInteger = Game.HEIGHT/32;
		int y = random.nextInt(findInteger);
		return y*32;
	}
	
}
