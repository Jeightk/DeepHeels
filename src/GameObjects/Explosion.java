package GameObjects;

import Tools.HitDetection;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

import java.awt.*;

public class Explosion extends GameObject {

    private int stage;

    private int size = 64;

    private Handler handler;
    private HitDetection hitDetection;

    private int DAMAGE = 1;

    public Explosion(float x, float y, ID id, int stage, Handler handler) {
        super(x, y, id);
        this.stage = stage;
        this.handler = handler;

        this.hitDetection = new HitDetection(handler, this);
    }

    @Override
    public void tick() {
        this.hitDetection.tick();
    }

    public int getDAMAGE(){
        return DAMAGE;
    }

    @Override
    public void render(Graphics g) {
        if(stage == 1){
            g.drawImage(Game.enemySS.grab64Image(1, 4, 64, 64), (int)x, (int)y, 64, 64, null);
        }else if(stage == 2){
            g.drawImage(Game.enemySS.grab64Image(2, 4, 64, 64), (int)x, (int)y, 64, 64, null);
        }else if(stage == 3){
            g.drawImage(Game.enemySS.grab64Image(3, 4, 64, 64), (int)x, (int)y, 64, 64, null);
        }

    }

    @Override
    public Rectangle getBound() {
        return new Rectangle((int)x, (int)y, size,size);
    }
}
