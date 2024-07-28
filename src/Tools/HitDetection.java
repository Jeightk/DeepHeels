package Tools;


import GameObjects.Enemy;
import GameObjects.Explosion;
import GameObjects.Plant;
import Projectiles.Barrel;
import Projectiles.Bullet;
import main.GameObject;
import main.Handler;
import main.ID;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class HitDetection {

	private ShootMecha shootMecha;
	private boolean firstEntry = false;

	private long currentPhysical;
	private long lastTimePhysical = 0;
	
	private long currentShoot;
	private long lastTimeShoot = 0;
	
	
	private boolean hitDetected = true;
	private boolean shootDetected = true;
	
	GameObject mainObj;
	GameObject disabledPlant = null;
	
	private Handler handler;

	public HashMap<GameObject, Boolean> currentEnemies = new HashMap<>();
	private boolean isFirstEntry = false;

	//TODO: CREATE A NEW INSTANCE OF THIS EACH TIME A MOB/PLANT
	public HitDetection(Handler handler, GameObject mainObj) {
		this.handler = handler;
		this.mainObj = mainObj;
		
	}
	
	public void tick() {
		if(this.mainObj.getID() == ID.Plant){
			this.plantCollision();
		}else if(this.mainObj.getID() == ID.Projectile){
			this.projectileCollision();
		}else if(this.mainObj.getID() == ID.Enemy){
			this.enemyCollision();
		}

	}


	private void enemyCollision(){
		if(this.mainObj.getBound().intersects(handler.getPlayer().getBound())){
			handler.getPlayer().setHP(handler.getPlayer().getHP() - ((Enemy)this.mainObj).getDMG());
		}
	}

	//
	private void plantCollision(){
		for (GameObject gameObject : handler.getEnemies()) {
			if (this.mainObj.getBound().intersects(gameObject.getBound()) && this.mainObj.getID() == ID.Plant) {

				if (((Plant) this.mainObj).getType() == "Defender") {
					if (currentEnemies.containsKey(gameObject)) {
						return;
					} else {
						currentEnemies.put(gameObject, true);
						shootMecha = new ShootMecha(this.mainObj, gameObject);
						shootMecha.shoot();
					}
				}
			}else{

				if(currentEnemies.containsKey(gameObject) || gameObject == null){
					currentEnemies.remove(gameObject);
					shootMecha.stopShoot();
				}
			}
		}
	}

	private void projectileCollision(){
		if(this.mainObj.getClass() == Explosion.class){
			if(handler.getPlayer().getBound().intersects(this.mainObj.getBound())){

				handler.getPlayer().setHP(handler.getPlayer().getHP() - ((Explosion)this.mainObj).getDAMAGE());
//				this.renderExplosion();
			}
			return;
		}

		for (GameObject gameObject : handler.getEnemies()) {
			if(this.mainObj.getID() == ID.Projectile && this.mainObj.getBound().intersects(gameObject.getBound())){
				gameObject.setHP(gameObject.getHP() - 5);
				handler.removeObject(this.mainObj);
			}else{

				if(currentEnemies.containsKey(gameObject) || gameObject == null){
					currentEnemies.remove(gameObject);
					shootMecha.stopShoot();
				}
			}
		}
	}

//	public void renderExplosion(){
//		float originX = handler.getPlayer().getX();
//		float originY = handler.getPlayer().getY();
//		handler.removeObject(this.mainObj);
//		GameObject t = new Explosion(originX, originY, ID.Enemy, 1);
//		handler.addObject(t);
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//			@Override
//			public void run() {
//				handler.removeObject(t);
//				GameObject t2 = new Explosion(originX, originY, ID.Enemy, 2);
//				handler.addObject(t2);
//
//
//				Timer timer2 = new Timer();
//				timer2.schedule(new TimerTask() {
//					@Override
//					public void run() {
//						handler.removeObject(t2);
//						GameObject t3 = new Explosion(originX, originY, ID.Enemy, 3);
//						handler.addObject(t3);
//
//						Timer timer3 = new Timer();
//						timer3.schedule(new TimerTask() {
//							@Override
//							public void run() {
//								handler.removeObject(t3);
//							}
//						}, 250);
//
//					}
//				}, 250);
//			}
//		}, 250);
//	}


	//OUT WITH THE OLD AND IN WITH THE NEW, REMOVED THIS AND DECIDED TO ADD INDIVIDUAL COLLISION METHODS FOR EACH ID FOR EFFICIENCY.
//	private void newCollision() {
//		for (GameObject gameObject : handler.getEnemies()) {
//			if (this.mainObj.getBound().intersects(gameObject.getBound()) && this.mainObj.getID() == ID.Plant) {
//
//				if (((Plant) this.mainObj).getType() == "Defender") {
//					if(currentEnemies.containsKey(gameObject)){
//						return;
//					}else{
//						currentEnemies.put(gameObject, true);
//						shootMecha = new ShootMecha(this.mainObj, gameObject);
//						shootMecha.shoot();
//					}
//				}
//			}else if(this.mainObj.getID() == ID.Projectile && this.mainObj.getBound().intersects(gameObject.getBound())){
//				gameObject.setHP(gameObject.getHP() - 5);
//			}else{
//
//				if(currentEnemies.containsKey(gameObject) || gameObject == null){
//					currentEnemies.remove(gameObject);
//					shootMecha.stopShoot();
//				}
//			}
//		}
//	}
	
	//TODO: NEEDS A BIT MORE TWEEKING, COULD CHECK IDS OF GAMEOBJECT BEFORE CYCLING THROUGH TO REDUCE THE AMOUNT OF OBJECTS THAT THE LOOP IS CYCLING THRU
//	private void collision() {
//		
//		//TODO: THERES SOME TYPE OF BUG HERE WHERE IT CANNOT FIGURE IF IT IS A SPECIFIC OBJECT, NEED TO CLEAN THIS UP
//		for(GameObject gameObject : handler.object) {
//			for(GameObject gOb2 : handler.object) {
//				if((gameObject != gOb2) && gameObject.getBound().intersects(gOb2.getBound())) {
//					System.out.println("First : " + gameObject.getID() + " : Second : " + gOb2.getID());
//					//Shooting projectiles collisions
//					if(((gameObject.getID() == ID.Projectile) && gOb2.getID() == ID.Enemy) || (gOb2.getID() == ID.Projectile) && gameObject.getID() == ID.Enemy) {
//						System.out.println("First : " + gameObject.getID() + " : Second : " + gOb2.getID());
//						if(gameObject.getID() == ID.Enemy) { //TODO: DIDNT EXECUTE WHEN CHECKING FOR BULLET TYPE!!!
//							if(gameObject.getHP() > 0) {
//								gameObject.setHP(gameObject.getHP()-10);
//								handler.removeObject(gOb2);
//								return;
//							}
//							
//						}else {
//							if(gOb2.getHP() > 0) {
//								gOb2.setHP(gOb2.getHP()-10);
//								handler.removeObject(gameObject);
//								return;
//							}
//						}
//						
//					}
//					//When in range of defensive plant shooters
//					if((gameObject.getID() == ID.Plant)) {
//						if((gameObject.getID() == ID.Plant) && (((Plant)gameObject).getType() == "Defender") && gOb2.getID()==ID.Enemy) {
//							plantobj = gameObject;
//							((Plant)gameObject).enableShoot(gOb2); // WHEN THE ENEMY ENTERS THE RANGE OF THE PLANT
//							disabledPlant = gOb2;
//							return;
//						}
//					}
//				}
//			}
//		}
//		//When enemy leaves the range of defensive plants
//		if(plantobj != null) {
//			((Plant)plantobj).disableShoot(disabledPlant); // WHEN THE ENEMY LEAVES THE RANGE OF THE PLANT
//			return;
//		}
//		
//	}
	
}
