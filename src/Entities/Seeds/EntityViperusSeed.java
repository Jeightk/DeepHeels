package Entities.Seeds;

import Entities.Entity;
import main.Game;

import java.awt.*;

public class EntityViperusSeed extends Entity {

    private int baseY;
    public EntityViperusSeed(float x, float y, Entities.EntityID EntityID, Entities.EntityType EntityType) {
        super(x, y, EntityID, EntityType);
        baseY = (int)this.y;
    }

    @Override
    public void tick() {
        this.dropAnimation(baseY, y);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Game.ss.grabBitImage(16, 16, 16, 16), (int)x, (int)y, null);
    }

    @Override
    public Image getImage() {
        return Game.ss.grabBitImage(16, 16, 16, 16);
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle((int)x, (int)y, 16, 16);
    }
}
