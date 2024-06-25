package Tools;


import GameObjects.Enemy;
import GameObjects.Plant;
import Projectiles.Bullet;
import main.GameObject;
import main.Handler;
import main.ID;

public class HitDetection {

	private long currentPhysical;
	private long lastTimePhysical = 0;
	
	private long currentShoot;
	private long lastTimeShoot = 0;
	
	
	private boolean hitDetected = true;
	private boolean shootDetected = true;
	
	GameObject mainObj;
	GameObject disabledPlant = null;
	
	private Handler handler;
	
	//TODO: CREATE A NEW INSTANCE OF THIS EACH TIME A MOB/PLANT
	public HitDetection(Handler handler, GameObject mainObj, ID objID) {
		this.handler = handler;
		this.mainObj = mainObj;
		
	}
	
	public void tick() {
		newCollision();
	}
	
	
	
	private void newCollision() {
		for(GameObject gameObject : handler.object) {
			if(this.mainObj.getBound().intersects(gameObject.getBound())) {
				if(this.mainObj.getID() == ID.Plant && gameObject.getID() == ID.Enemy) {
					if(((Plant)this.mainObj).getType() == "Defender") {
						((Plant)this.mainObj).enableShoot(gameObject);
					}
				}
			}
		}
	}
	
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


	private void registerObj(ID id) {
		if(this.mainObj.getID() == ID.Enemy) {
		}
		
		
	}
	
	
	public void DetectHit(GameObject tObject, GameObject player) {
		currentPhysical = System.currentTimeMillis();
		if(hitDetected) {
			//Getting the newest time and subtracting with the last iterated time, then if thats greater than the attack delay value aka attackspeed then iterate again.
			
			if(player.getHP() - ((Enemy)tObject).getDMG() < 0) {
				System.out.println("GG");
				return;
			}
			
			if(currentPhysical - lastTimePhysical > ((Enemy)tObject).getAttackSpeed()) {
				player.setHP(player.getHP()-((Enemy)tObject).getDMG());
				((Enemy)tObject).AttackingRender(player);
				lastTimePhysical = System.currentTimeMillis();
			}
		}
	}
	
	public void DetectShoot(GameObject plant, GameObject Enemy) {
		this.shootDetected = true;
		currentShoot = System.currentTimeMillis();
		if(shootDetected) {
			if(currentShoot - lastTimeShoot > ((Plant)plant).getShootSpeed()) {
				System.out.println("test");
				lastTimePhysical = System.currentTimeMillis();
			}
		}
	}
	
	
	public void cancelHit() {
		hitDetected = false;
	}
	
	public void cancelShoot() {
		this.shootDetected = false;
	}
	
}
