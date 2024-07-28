package GameObjects.Enemies;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Entities.*;
import Entities.Seeds.EntityLilypatSeed;
import Entities.Seeds.EntityViperusSeed;
import GameObjects.Enemy;
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

	private boolean walking = true;
	private int walkingAnimation = 1;

	private Healthbar healthbar;

	private int DMG = 20;


	public Thief(float x, float y, ID id, int HP, long AttackSpeed, GameObject player, Handler handler) {
		super(x, y, id, HP, AttackSpeed);
		this.handler = handler;

		this.player = player;
		
		
		healthbar = new Healthbar(10, 10, (int)getX(), (int)getY()-50);


		walkingAnimation();
		this.hitdetection = new HitDetection(handler, this);
	}

	@Override
	public void render(Graphics g) {
//		if(walking){
//			if(System.currentTimeMillis()/100 % 3 == 0) {
//				g.drawImage(Game.enemySS.grab64Image(1, 1, 64, 64), (int)x, (int)y, 64, 64, null);
//			}else{
//				if(walkingCounter % 2 == 0){
//					g.drawImage(Game.enemySS.grab64Image(2, 1, 64, 64), (int)x, (int)y, 64, 64, null);
//				}else{
//					g.drawImage(Game.enemySS.grab64Image(3, 1, 64, 64), (int)x, (int)y, 64, 64, null);
//				}
//				walkingCounter++;
//			}
//
//
//		}

		switch (walkingAnimation) {
			case 0:
				g.drawImage(Game.enemySS.grab64Image(1, 1, 64, 64), (int) x, (int) y, 64, 64, null);
				break;
			case 1:
				g.drawImage(Game.enemySS.grab64Image(2, 1, 64, 64), (int) x, (int) y, 64, 64, null);
				break;
			case 2:
				g.drawImage(Game.enemySS.grab64Image(3, 1, 64, 64), (int) x, (int) y, 64, 64, null);
				break;
			case 3:
				g.drawImage(Game.enemySS.grab64Image(4, 1, 64, 64), (int) x, (int) y, 64, 64, null);
				break;
		}

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

		if(distance >= 40){
			velX = (float) ((-1.0/distance) * diffX);
			velY = (float) ((-1.0/distance) * diffY);
			if(walking == false){
				walking = true;
				walkingAnimation();
			}
		}else{
			velX = 0;
			velY = 0;
			if(walking){
				walking = false;
				attackingAnimation();
			}
		}

		healthbar = new Healthbar(10, 10, (int)getX()+5, (int)getY()-15);


	}

	@Override
	public Rectangle getBound() {
		return new Rectangle((int)x, (int)y, size,size);
	}

	@Override
	public List<Entity> getItemDrops() {
		List<Entity> drops = Arrays.asList(new EntityIvoriane((int)x, (int)y, EntityID.EntityIvoriane, EntityType.Material), new EntityViperusSeed((int)x, (int)y, EntityID.EntityViperusSeed, EntityType.Seed),
				new EntityLilypatSeed((int)x, (int)y, EntityID.EntityLilypatSeed, EntityType.Seed));
		return drops;
	}

	public void walkingAnimation() { // Animates the thief walking
		Timer timer = new Timer();
		Timer timer2 = new Timer();
		Timer timer3 = new Timer();
		Timer timer4 = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(walking != true){
					walkingAnimation = 0;
					timer.cancel();
					return;
				}
				walkingAnimation = 0;
				timer2.schedule(new TimerTask() {
					@Override
					public void run() {
						walkingAnimation = 1;
						timer3.schedule(new TimerTask() {
							@Override
							public void run() {
								walkingAnimation = 0;
								timer4.schedule(new TimerTask() {
									@Override
									public void run() {
										walkingAnimation = 2;
									}
								}, 200);
							}
						}, 200);
					}
				}, 202);
			}
		}, 0, 800);
	}

	public void attackingAnimation(){
		Timer timer = new Timer();
		Timer timer2 = new Timer();


		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				if(walking){
					timer.cancel();
					return;
				}
				walkingAnimation = 3;
				hitdetection.tick();
				timer2.schedule(new TimerTask() {
					@Override
					public void run() {
						walkingAnimation = 0;
					}
				}, 500);

			}
		}, 0, 1000);
	}

	public void objectCleanup(){
		this.handler.removeObject(this);
	}

	@Override
	public int getDMG() {
		return DMG;
	}


	public int getSize() {
		return size;
	}

}
