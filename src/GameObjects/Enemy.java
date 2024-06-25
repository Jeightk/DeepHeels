package GameObjects;

import java.awt.Graphics;
import java.util.List;

import Entities.Entity;
import main.GameObject;
import main.ID;

public abstract class Enemy extends GameObject{

	
	protected int HP;
	protected int BASEHP;
	protected int DMG;
	protected long AttackSpeed;
	public Enemy(float x, float y, ID id, int HP, int DMG, long AttackSpeed) {
		super(x, y, id);
		this.HP = HP;
		this.BASEHP = HP;
		this.DMG = DMG;
		this.AttackSpeed = AttackSpeed;
	}
	
	
	public abstract void render(Graphics g);
	public abstract void tick();
	public abstract List<Entity> getItemDrops();
	
	public abstract void AttackingRender(GameObject player);
	
	public void setHP(int hp) {
		this.HP = hp;
	}
	
	public int getHP() {
		return this.HP;
	}
	
	public int getDMG() {
		return this.DMG;
	}
	
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
