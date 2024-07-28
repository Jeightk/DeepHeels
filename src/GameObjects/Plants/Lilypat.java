package GameObjects.Plants;

import GameObjects.Plant;
import main.Game;
import main.GameObject;
import main.ID;

import java.awt.*;

public class Lilypat extends Plant {

    private int size = 64;
    public Lilypat(float x, float y, ID id, String name, String type) {
        super(x, y, id, name, type);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.ss.grabImage(4, 5, 64, 64), (int)x, (int)y, null);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle((int)x-96, (int)y-96, 256, 256);
    }

    @Override
    public void shoot(GameObject targetObject) {

    }

    @Override
    public void disableShoot(GameObject targetObject) {

    }

    @Override
    public void enableShoot(GameObject targetObject) {

    }

    public int getSize() {
        return size;
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle((int)x-96, (int)y-96, 256, 256);
    }

    @Override
    public long getShootSpeed() {
        return 0;
    }
}
