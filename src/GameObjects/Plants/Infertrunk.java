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

	private int size = 64;
	private boolean isHarvested = true;
	private long shootSpeed = 2000;
	private Handler handler;
	private boolean shootEnemy = false; // boolean to set plant defense active
	private HitDetection hitDetection;

	private int animation = 0;

	public Infertrunk(float x, float y, ID id, String name, String type, Handler handler) {
		super(x, y, id, name, type);
		this.handler = handler;
		this.isDefendable = true;
		this.hitDetection = new HitDetection(handler, this);

		defaultAnimation();
	}

	@Override
	public void tick() {
		
		hitDetection.tick();
		
	}

	@Override
	public void render(Graphics g) {

		switch(animation){
			case 0:
				g.drawImage(Game.defendersSS.grab64Image(1, 1, 64, 64), (int)x, (int)y, null);
				break;
			case 1:
				g.drawImage(Game.defendersSS.grab64Image(2, 1, 64, 64), (int)x, (int)y, null);
				break;
			case 2:
				g.drawImage(Game.defendersSS.grab64Image(3, 1, 64, 64), (int)x, (int)y, null);
				break;
		}

	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x-96, (int)y-96, 256, 256);
	}
	
	public Rectangle getRange() {
		return new Rectangle((int)x-96, (int)y-96, 256, 256);
	}

	public int getSize() {
		return size;
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

	public void defaultAnimation(){
		Timer timer = new Timer();
		Timer timer2 = new Timer();
		Timer timer3 = new Timer();
		Timer timer4 = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				animation = 1;
				timer2.schedule(new TimerTask() {
					@Override
					public void run() {
						animation = 2;
						timer3.schedule(new TimerTask() {
							@Override
							public void run() {
								animation = 1;
								timer4.schedule(new TimerTask() {
									@Override
									public void run() {
										animation = 0;
									}
								}, 200);
							}
						}, 200);

					}
				}, 202);
			}
		}, 0, 800);
	}

}
