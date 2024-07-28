package GameObjects;

import main.GameObject;
import main.ID;

import java.awt.*;

public abstract class Flora extends GameObject {
    public Flora(float x, float y, ID id) {
        super(x, y, id);
    }

    public abstract void tick();
    public abstract void render();
    public abstract Rectangle getBound();
    public abstract Rectangle getRange();

}
