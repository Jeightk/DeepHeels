package GameObjects.Plants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import GameObjects.Plant;
import Projectiles.Fireball;
import Tools.HitDetection;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class Infertrunk extends Plant{

	private long shootSpeed = 2000;
	private Handler handler;
	private boolean shootEnemy = false; // boolean to set plant defense active
	private Timer timer;
	private HitDetection hitDetection;
	
	public Infertrunk(float x, float y, ID id, String name, String type, Handler handler) {
		super(x, y, id, name, type);
		this.handler = handler;
		this.isDefendable = true;
		this.hitDetection = new HitDetection(handler, this);
	}

	@Override
	public void tick() {
		
		hitDetection.tick();
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.ss.grabImage(4, 7, 64, 64), (int)x, (int)y, null);

	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x-96, (int)y-96, 256, 256);
	}
	
	public Rectangle getRange() {
		return new Rectangle((int)x-96, (int)y-96, 256, 256);
	}
	
	@Override
	public void whenClicked() {
	}

	@Override
	public long getShootSpeed() {
		return shootSpeed;
	}

	@Override
	public void enableShoot(GameObject targetObject) {
		if(this.shootEnemy) {
			return;
		}else {
			System.out.println("has entered");
			this.shootEnemy = true;
			this.shoot(targetObject);
		}
		
		
		
		
	}

	@Override
	public void shoot(GameObject targetObject) {
		handler.addObject(new Fireball(x, y, ID.Projectile, "Fireball", 2, targetObject, handler));
	}

	@Override
	public void disableShoot(GameObject targetObject) {
		
		if(this.shootEnemy != false) {
			this.shootEnemy = false;
//			this.shoot(targetObject);
			System.out.println("has left");
		}
		
	}

}
