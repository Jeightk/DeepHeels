package GameObjects.Plants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;

import GameObjects.Plant;
import Projectiles.Fireball;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

public class Infertrunk extends Plant{

	private long shootSpeed = 3000;
	private Handler handler;
	private boolean shootEnemy = false; // boolean to set plant defense active
	private Timer timer;
	
	public Infertrunk(float x, float y, ID id, String name, String type, Handler handler) {
		super(x, y, id, name, type);
		this.handler = handler;
		this.isDefendable = true;
	}

	@Override
	public void tick() {
		
		collision();
		
	}

	private void collision() {
		
		
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Game.ss.grabImage(4, 7, 64, 64), (int)x, (int)y, null);
		
		g.setColor(Color.red);
		g.drawRect((int)x-96, (int)y-96, 256, 256);

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
		if(this.shootEnemy == true) {
			timer = new Timer();
			handler.addObject(new Fireball(x, y, ID.Projectile, "Fireball", 2, targetObject));
			timer.scheduleAtFixedRate(new TimerTask() {
				  @Override
				  public void run() {
				    handler.addObject(new Fireball(x, y, ID.Projectile, "Fireball", 2, targetObject));
				  }
				}, 5*1000, 5*1000);
		}else if(this.shootEnemy == false) {
			timer.cancel();
		}
		
		
	}

	@Override
	public void disableShoot(GameObject targetObject) {
		
		if(this.shootEnemy != false) {
			this.shootEnemy = false;
			this.shoot(targetObject);
			System.out.println("has left");
		}
		
	}

}
