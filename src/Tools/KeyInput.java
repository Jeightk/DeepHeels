package Tools;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Entities.Seeds.EntityLilypatSeed;
import GameObjects.Enemies.Burnard;
import GameObjects.Enemies.Thief;
import GameObjects.Plants.Infertrunk;
import GameObjects.Plants.Viperus;
import Levels.LevelHandler;
import Menus.Inventory;
import Menus.Menus;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;
import main.Window;
import main.Game.STATE;

public class KeyInput extends KeyAdapter{

	private Handler handler;
	private Menus menus;
	private Game game;
	private LevelHandler levelHandler;
	private Inventory inventory;

	private boolean wkey = false;
	private boolean skey = false;
	private boolean akey = false;
	private boolean dkey = false;
	
	
	public boolean FULLSCREEN = false;
	
	public KeyInput(Handler handler, Menus menus, Game game, LevelHandler levelHandler, Inventory inventory) {
		this.handler = handler;
		this.menus = menus;
		this.game = game;
		this.levelHandler = levelHandler;
		this.inventory = inventory;
	}
	
	public void keyPressed(KeyEvent e) {
		if(game.gameState == STATE.Menu){
			return;
		}

		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) {
			GameObject tempObject = handler.object.get(i);
			if (tempObject.getID() == ID.Player) {
				if (key == KeyEvent.VK_W) {
					tempObject.setVelocityY(-5);
					this.wkey = true;
				}
				if (key == KeyEvent.VK_S) {
					tempObject.setVelocityY(5);
					this.skey = true;
				}
				if (key == KeyEvent.VK_D) {
					tempObject.setVelocityX(5);
					this.dkey = true;
				}
				if (key == KeyEvent.VK_A) {
					tempObject.setVelocityX(-5);
					this.akey = true;
				}
			}


		}


		
		if(key == KeyEvent.VK_ESCAPE) System.exit(1);
		
		//TODO: Lags when first loaded up, maybe enable all these values on start up then disable so the first time the player opens inventory it doesnt lag...
		if(key == KeyEvent.VK_E && menus.openInventory == false && menus.openWorkbench == false) {
			menus.openInventory = true;
			game.gameState = STATE.Inventory;
			menus.inventoryRegionsAccess();
		}else {
			menus.openInventory = false;
			menus.openWorkbench = false;
			game.gameState = STATE.Game;
			menus.setEntityMessageVisible(false);

		}
		
		//FULLSCREEN MODE!
		if(key == KeyEvent.VK_F11){
			if(FULLSCREEN) {
				new Window(Game.WIDTH, Game.HEIGHT, "ToStake", true, game);
			}else {
				new Window(Game.WIDTH, Game.HEIGHT, "ToStake", false, game);
			}
		}
		
		//TODO: REMOVE THESE WHEN GAME DONE
		//SPAWNING MOBS WITH A KEY only for testing purposes ofc
		if(key == KeyEvent.VK_F) {
			handler.addObject(new Burnard(100, 100, ID.Enemy,100, 3000, handler.getPlayer(), handler));
		}

		if(key == KeyEvent.VK_N){
			levelHandler.AscendLevel();
		}

		if(key == KeyEvent.VK_L) {
//			handler.addObject(new Infertrunk(400, 400, ID.Plant, "Infertrunk", "Defender", handler));
			handler.addObject(new Infertrunk(400, 400, ID.Plant, "Infertrunk", "Defender", handler));
		}
		
	}
	
	public void keyReleased(KeyEvent e) {

		//TODO: Fix bug where clicking workbench and then hitting E, player will keep walking

		//Returns when the state hasnt switched to the 'game' state.
		if(game.gameState == STATE.Menu || game.gameState == STATE.Workbench){
			if(game.gameState == STATE.Workbench){
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_E){
					game.gameState = STATE.Game;
					menus.openWorkbench = false;
				}
			}
			return;
		}

		int key = e.getKeyCode();

		for (int i = 0; i < handler.object.size(); i++) { // fix the problem, wayy better movement than ^
			GameObject tobject = handler.object.get(i);
			if (tobject.getID() == ID.Player) {
				if (key == KeyEvent.VK_W && skey == false) {
					this.wkey = false;
					tobject.setVelocityY(0);
				} else if (key == KeyEvent.VK_W && skey == true) {
					tobject.setVelocityY(5);
					this.wkey = false;
				}
				if (key == KeyEvent.VK_S && wkey == false) {
					this.skey = false;
					tobject.setVelocityY(0);
				} else if (key == KeyEvent.VK_S && wkey == true) {
					tobject.setVelocityY(-5);
					this.skey = false;
				}

				if (key == KeyEvent.VK_D && akey == false) {
					this.dkey = false;
					tobject.setVelocityX(0);
				} else if (key == KeyEvent.VK_D && akey == true) {
					tobject.setVelocityX(-5);
					this.dkey = false;
				}
				if (key == KeyEvent.VK_A && dkey == false) {
					this.akey = false;
					tobject.setVelocityX(0);
				} else if (key == KeyEvent.VK_A && dkey == true) {
					tobject.setVelocityX(5);
					this.akey = false;
				}
			}
		}


	}
	
}
