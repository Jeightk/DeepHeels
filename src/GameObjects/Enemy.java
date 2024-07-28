package GameObjects;

import java.awt.Graphics;
import java.util.List;

import Entities.Entity;
import main.GameObject;
import main.ID;

public abstract class Enemy extends GameObject{

	
	protected int HP;
	protected int BASEHP;
	protected long AttackSpeed;
	public Enemy(float x, float y, ID id, int HP, long AttackSpeed) {
		super(x, y, id);
		this.HP = HP;
		this.BASEHP = HP;
		this.AttackSpeed = AttackSpeed;
	}
	
	
	public abstract void render(Graphics g);
	public abstract void tick();
	public abstract List<Entity> getItemDrops(); // item drops of the enemy
	public abstract void objectCleanup(); // removes any timers ; or things attached to object before object itself is removed

	public void setHP(int hp) {
		this.HP = hp;
	}
	
	public int getHP() {
		return this.HP;
	}
	
	public abstract int getDMG();
	
	public void setAttackSpeed(long atkspd) {
		this.AttackSpeed = atkspd;
	}
	
	public long getAttackSpeed() {
		return this.AttackSpeed;
	}
	public int getBASEHP() {
		return BASEHP;
	}
	
}
