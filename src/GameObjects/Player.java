package GameObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import Entities.Craftable;
import Entities.Entity;
import Entities.EntityHandler;
import HUD.Healthbar;
import Menus.Inventory;
import Tiles.Tile;
import Tools.HitDetection;
import Tools.ImageHandler;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class Player extends GameObject{

	private Game game;
	private EntityHandler entityHandler;
	private Inventory inventory;
	private Healthbar healthbar;
	private Handler handler;
	private boolean hitDetected = false;
	
	
	//Base amount of health
	private int HEALTH = 100;
	
	//Base amount of how much damage a player with his fists does
	private int DAMAGE = 10;
	
	private boolean GearStats = false;
	
	public Craftable Helmet;
	public Craftable Chestplate;
	public Craftable Weapon;
	public Entity Offhand;

	public String character;

	double rotationRequired = Math.toRadians (45);
//	AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, (int)x, (int)y);
//	AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
	
	public Player(float x, float y, ID id, Game game, EntityHandler entityHandler, Inventory inventory, Healthbar healthbar, Handler handler) {
		super(x, y, id);
		
		this.handler = handler;
		this.entityHandler = entityHandler;
		this.game = game;
		this.inventory = inventory;
		this.healthbar = healthbar;

		this.character = handler.getCharacterName();
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		x+=velX;
		y+=velY;
		if(velX != 0 || velY != 0) {
			x = Game.clamp(x, 0, Game.WIDTH-75);
			y = Game.clamp(y, 0, Game.HEIGHT-100);
			
		}
		
		//Updates once when gear is updated
		if(GearStats) {
			if(Weapon != null) {
//				System.out.println(DAMAGE);
				DAMAGE=Weapon.getDamage();
//				System.out.println(DAMAGE);
			}
			
			GearStats = false;
			
		}
		
		collision();
	}

	private boolean direction;
	
	//Hackyish way to animate the player, probably way better ways but to lazy to learn rn...
	@Override
	public void render(Graphics g) {



		if(velX < 0) {
			//Setting the direction of the idle image
			direction = false;

			if(character.equalsIgnoreCase("Myk")){ // HAD TO BE SPECIFC WITH THE SPRITES & DIRECTION OOPS
				directionalAnimation(g, 0, -64);
			}else{
				directionalAnimation(g, 64, -64);
			}


		}else if(velX > 0) {
			direction = true;

			if(character.equalsIgnoreCase("Myk")){ // HAD TO BE SPECIFC WITH THE SPRITES & DIRECTION OOPS
				directionalAnimation(g, 64, 64);
			}else{
				directionalAnimation(g, 0, 64);
			}


		}else {
			if(character.equalsIgnoreCase("Myk")){ // HAD TO BE SPECIFC WITH THE SPRITES & DIRECTION OOPS
				if(direction) {
					directionalAnimation(g, 64, 64);
				}else {
					directionalAnimation(g, 0, -64);
				}
			}else if(character.equalsIgnoreCase("Otis")){
				if(direction) {
					directionalAnimation(g, 0, 64);
				}else {
					directionalAnimation(g, 64, -64);
				}
			}

		}
		
		healthbar.render(g, HEALTH);
		
		if(Weapon != null) {
			if(direction) {
				g.drawImage(ImageHandler.rotate(((BufferedImage)Weapon.getImage()), 90.0), (int)x+30, (int)y+20, 32, 32, null);
			}else {
//				g.drawImage(ImageHandler.rotate(((BufferedImage)Weapon.getImage()), 270.0), (int)x, (int)y+20, 32, 32, null);
				BufferedImage rotated = (BufferedImage) ImageHandler.rotate(((BufferedImage)Weapon.getImage()), 90.0);
				g.drawImage(ImageHandler.flip(rotated, ImageHandler.FLIP_HORIZONTAL), (int)x, (int)y+20, 32, 32, null);
			}
		}
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x, (int)y, 64, 64);
	}
	
	public Rectangle punchBound() {
		//renders the rectangle based on the direction the player is standing
		if(direction) {
			return new Rectangle((int)x+64, (int)y, 64,64);
		}else {
			return new Rectangle((int)x-64, (int)y, 64,64);
		}
		
	}
	
	//Animating character steps in certain directions
	private void directionalAnimation(Graphics g, int xOffset, int xDirection) {

		if(character.equalsIgnoreCase("Myk")){
			g.drawImage(Game.ss.grabImage(4, 7, 64, 64), (int)x+xOffset, (int)y, (-1)*xDirection, 64, null);
		}else{
			if(velX != 0 || velY != 0) {
				if(System.currentTimeMillis()/100 % 2 == 0) {
					g.drawImage(Game.ss.grab64Image(3, 1, 64, 64), (int)x+xOffset, (int)y, xDirection, 64, null);
				}else {
					g.drawImage(Game.ss.grab64Image(2, 1, 64, 64), (int)x+xOffset, (int)y, xDirection, 64, null);
				}

			}else {
				g.drawImage(Game.ss.grab64Image(2, 1, 64, 64), (int)x+xOffset, (int)y, xDirection, 64, null);
			}
		}


	}
	
//	HitDetection t = new HitDetection(handler);
	public void collision() {
		
		for(int i = 0; i < entityHandler.entity.size(); i++) {
			if(getBound().contains(entityHandler.entity.get(i).getX(), entityHandler.entity.get(i).getY())) {
				inventory.addItem(entityHandler.entity.get(i));
				entityHandler.entity.remove(i);
			}
		}
		
		
	}
	
	
	
	public int getHP() {
		return HEALTH;
	}
	
	public void setHP(int HP) {
		this.HEALTH = HP;
	}
	
	//is called in the mouse input when equipping or unequipping items
	public void updateGear() {
		Weapon = (Craftable) inventory.Gear[5];
		GearStats = true;
	}
	
	public int getDamage() {
		return DAMAGE;
	}
	
	public Craftable getWeapon() {
		return Weapon;
	}
	
	public Entity getOffhand() {
		return Offhand;
	}
	
}
