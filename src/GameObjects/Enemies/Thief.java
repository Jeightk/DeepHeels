package GameObjects.Enemies;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Entities.Entity;
import Entities.EntityID;
import Entities.EntityIvoriane;
import Entities.EntityType;
import GameObjects.Enemy;
import GameObjects.Plant;
import HUD.Healthbar;
import Tools.HitDetection;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class Thief extends Enemy{

	private Handler handler;
	private GameObject player;
	private int size = 64;
	
	private boolean hitDetected = false;
	private HitDetection hitdetection;
	
	private Healthbar healthbar;
	
	public Thief(float x, float y, ID id, int HP, int DMG, long AttackSpeed, Handler handler) {
		super(x, y, id, HP, DMG, AttackSpeed);
		this.handler = handler;
		
		for(int i = 0; i < handler.object.size(); i++) {
			if(handler.object.get(i).getID() == ID.Player) player = handler.object.get(i);
		}
		
		
		healthbar = new Healthbar(10, 10, (int)getX(), (int)getY()-50);
		
//		this.hitdetection = new HitDetection(handler);
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.enemySS.grab64Image(2, 1, 64, 64), (int)x, (int)y, 64, 64, null);
		healthbar.render(g, getHP(), getBASEHP());
		
	}
	
	@Override
	public void tick() {
		x += velX;
		y += velY;
//		counter++;
		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY() - 8;
		float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));
		
		velX = (float) ((-1.0/distance) * diffX);
		velY = (float) ((-1.0/distance) * diffY);
		healthbar = new Healthbar(10, 10, (int)getX()+5, (int)getY()-15);
		
		if(this.getHP() <= 0){
			this.handler.removeObject(this);
		}
		
	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x, (int)y, size,size);
	}

	@Override
	public List<Entity> getItemDrops() {
		List<Entity> drops = Arrays.asList(new EntityIvoriane((int)x, (int)y, EntityID.EntityIvoriane, EntityType.Material));
		return drops;
	}

	public void AttackingRender(GameObject player) {
		if(player.getX()<this.getX()) {
			this.setX(this.getX()-10);
		}else {
			this.setX(this.getX()+10);
		}
	}
	
	
	public int getSize() {
		return size;
	}

}
