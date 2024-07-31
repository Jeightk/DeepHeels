package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import Entities.CraftableHandler;
import Entities.EntityHandler;
import Saving.SaveFile;
import GameObjects.Workbench;
import HUD.Healthbar;
import Levels.LevelHandler;
import Menus.Inventory;
import Menus.Hotbar;
import Menus.MainMenu;
import Menus.CharacterSelect;
import Menus.Menus;
import Menus.LoadSave;
import Menus.Settings;
import Menus.WorkbenchMenu;
import Tiles.TileMap;
import Tools.GrassLandscape;
import Tools.KeyInput;
import Tools.MapRender;
import Tools.MouseInput;
import Tools.PlantGrowth;
import Tools.Recipes;

public class Game extends Canvas implements Runnable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5602704290958149858L;

	//TO FIX THE FULL SCREEN BUG CHANGE 2 TO 3
//	public static final int WIDTH = (640)*2, HEIGHT = (WIDTH/12*9);
	public static int WIDTH = 1280, HEIGHT = 736;
//	public static final int WIDTH = 1800, HEIGHT = 900;

	
	
	/* THE PRIORITY OF ELEMENTS OF THE GAME
	 * 0 : THE PLAYER, MIGHT SHIFT IF I EVER ADD THE FUNCTIONALITY TO WALK INTO BUILDINGS
	 * 1 : Enemies
	 * 2 : Plants and shrubs
	 * 3 : The farming tiles & buildings
	 * 4 : Background tiles
	 * */
	private Thread thread;
	private boolean running = false;

	
	//for displaying frames not so fast
	private int frames;
	private int AFRAME = 0;
	private long timeDisplacement = System.currentTimeMillis();
	//--------
	
	
	private Handler handler;
	private EntityHandler entityHandler;
	private CraftableHandler craftableHandler;
	
	private BufferedImage spritesheet;
	private BufferedImage enemySprites;
	private BufferedImage defendersSprites;
	private BufferedImage grassBackground = null;
	
	
	public static SpriteSheet ss;
	public static SpriteSheet enemySS;
	public static SpriteSheet defendersSS;
	public static BufferedImage inventoryIMG = null;
	public static BufferedImage inventorySelector = null;
	public static BufferedImage workbenchIMG = null;
	public static BufferedImage NextLevelIMG = null;
	public static BufferedImage NextLevelIMGHover = null;
	public static BufferedImage healthbarIMG = null;
	public static BufferedImage hotbarIMG = null;
	public static BufferedImage characterSelectIMG = null;
	public static BufferedImage startButtonHOVERIMG = null;
	
	public static BufferedImage mainMenubackground = null;
	public static BufferedImage mainMenubackgroundPlayClick = null;
	public static BufferedImage mainMenubackgroundLoadClick = null;
	public static BufferedImage mainMenubackgroundQuitClick = null;
	public static BufferedImage mainMenubackgroundAboutClick = null;

	private SaveFile saveFile;
	private Settings settings;
	private LoadSave loadSave;

	private MapRender mapRender;
	
	private TileMap tileMap;
	
	private MouseInput mouseInput;
	private KeyInput keyInput;
	
	private Inventory inventory;
	private Hotbar hotbar;
	private Recipes recipes;
	private WorkbenchMenu workbenchmenu;
	private Menus menus;
	private GrassLandscape grasslandscape;
	private MainMenu mainMenu;
	private CharacterSelect characterSelect;
	
	private Healthbar healthbar;
	private PlantGrowth plantGrowth;
	private LevelHandler levelHandler;
	
	private int TILLABLE_LAND = 10;
	
	public enum STATE{
		Game,
		Menu,
		Inventory,
		Workbench
	}
	
	public STATE gameState = STATE.Menu;
	
	public Game() {
		handler = new Handler();

		entityHandler = new EntityHandler();
		craftableHandler = new CraftableHandler();
		levelHandler = new LevelHandler(handler, this);

		grasslandscape = new GrassLandscape(this);
		mainMenu = new MainMenu(this, grasslandscape);
		recipes = new Recipes(craftableHandler);
		inventory=new Inventory();
		hotbar = new Hotbar(inventory);
		workbenchmenu = new WorkbenchMenu(inventory, recipes, levelHandler);
		tileMap = new TileMap(handler);
		menus = new Menus(inventory, tileMap, workbenchmenu, this);
		characterSelect = new CharacterSelect(this);
		healthbar = new Healthbar(600, 10, Game.WIDTH/2-(600/2), 25);
		mapRender = new MapRender(2, handler, entityHandler, inventory, healthbar, this);
		plantGrowth = new PlantGrowth(handler, tileMap, mapRender);

		loadSave = new LoadSave(this, handler, inventory, mapRender, tileMap);
		mouseInput = new MouseInput(handler, tileMap, inventory, menus, workbenchmenu, entityHandler, mainMenu, plantGrowth, this, levelHandler, mapRender, characterSelect, loadSave);


		keyInput = new KeyInput(handler, menus, this, levelHandler);
		


		saveFile = new SaveFile(this, inventory, handler);
		settings = new Settings(saveFile);

		this.addKeyListener(keyInput);
		this.addKeyListener(settings);
		this.addMouseListener(mouseInput);
		this.addMouseMotionListener(mouseInput);

		handler.addObject(new Workbench(Game.WIDTH/2-32,100, ID.Workbench));
		new Window(WIDTH, HEIGHT, "DEEP HEELS", false, this);

	}
	
	public void init() {
		BufferedImageLoader loader = new BufferedImageLoader();
		try {
			spritesheet = loader.loadImage("sprites/sprite_sheet.png");
			enemySprites = loader.loadImage("sprites/enemies.png");
			defendersSprites = loader.loadImage("sprites/defenders.png");
			grassBackground = loader.loadImage("sprites/grassbackground.png");
			mainMenubackground = loader.loadImage("sprites/menubackground.png");
			mainMenubackgroundPlayClick = loader.loadImage("sprites/menubackgroundwhenPlayClicked.png");
			mainMenubackgroundLoadClick = loader.loadImage("sprites/menubackgroundwhenLoadClicked.png");
			mainMenubackgroundQuitClick = loader.loadImage("sprites/menubackgroundwhenQuitClicked.png");
			mainMenubackgroundAboutClick = loader.loadImage("sprites/menubackgroundwhenAboutClicked.png");
			inventoryIMG = loader.loadImage("sprites/inventory.png");
			inventorySelector = loader.loadImage("sprites/inventorySelector.png");
			characterSelectIMG = loader.loadImage("sprites/characterselect.png");
			startButtonHOVERIMG = loader.loadImage("sprites/startbuttonHOVER.png");
			workbenchIMG = loader.loadImage("sprites/crafting.png");
			NextLevelIMG = loader.loadImage("sprites/nextlevel.png");
			NextLevelIMGHover = loader.loadImage("sprites/nextlevelhover.png");
			healthbarIMG = loader.loadImage("sprites/healthbar.png");
			hotbarIMG = loader.loadImage("sprites/hotbar.png");
		}catch(IOException e) {
			e.printStackTrace();
		}
		ss = new SpriteSheet(spritesheet);
		enemySS = new SpriteSheet(enemySprites);
		defendersSS = new SpriteSheet(defendersSprites);
		
	}
	//possibly fix the issue of inventory taking a while to load on the first load
	private boolean init_Inventory = true;
	public void initInventory() {
		if(init_Inventory) {
			menus.openInventory = true;
			menus.openInventory = false;
			init_Inventory = false;
		}
		
	}

	public void setWIDTH(int width){
		this.WIDTH = width;
	}

	public void setHEIGHT(int height){
		this.HEIGHT = height;
	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		if(gameState == STATE.Game) {
			handler.tick();
			entityHandler.tick();
		}else if(gameState == STATE.Menu) {
			mainMenu.tick();
			characterSelect.tick();
		}
		
		
		//bla bla.tick();
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(grassBackground, 0, 0, Game.WIDTH, Game.HEIGHT, null);
//		g.setColor(Color.black);
//		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		//DISPLAYING FRAMERATE PERIODICALLY
		if(System.currentTimeMillis() - timeDisplacement > 1000) {
			AFRAME = frames;
			timeDisplacement = System.currentTimeMillis();
		}
		Font fpsFont = new Font("Georgia", 1, 15);
		g.setFont(fpsFont);
		g.setColor(Color.yellow);
		
		//----------------------------------
		
		
		
		handler.render(g);
		entityHandler.render(g);
		menus.render(g);
		craftableHandler.render(g);
		mouseInput.render(g);
		
		if(gameState == STATE.Game) {
			tileMap.render(g);
			tileMap.renderFarmableTiles(g);
			levelHandler.render(g);
		}else if(gameState == STATE.Inventory) {
			tileMap.renderInventoryTiles(g);
			tileMap.renderInventoryGearTiles(g);
		}else if(gameState == STATE.Workbench) {
			tileMap.renderWorkbenchTiles(g);
		}else if(gameState == STATE.Menu){
			mainMenu.render(g);
			characterSelect.render(g);
		}
		initInventory();
		
		
		g.drawString("FPS: "+AFRAME, 10, 20);
		g.dispose();
		bs.show();
		
	}
	

	@Override
	public void run() {
		init();
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) /ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
//				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
		
	}
	public static float clamp(float var, float min, float max) {
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
	}
	
	public int getTillableLandAmount() {
		return this.TILLABLE_LAND;
	}
	
	public void setTillableLandAmount(int amount) {
		this.TILLABLE_LAND = amount;
	}
	
	public static void main(String[] args) {
		new Game();
	}

}
