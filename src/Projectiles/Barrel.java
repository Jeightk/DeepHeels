package Projectiles;

import GameObjects.Explosion;
import Tools.HitDetection;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Barrel extends GameObject {

    private int size = 64;

    GameObject player;
    private Handler handler;



    private float originX;
    private float originY;
    private boolean Exploded = false;

    public Barrel(float x, float y, ID id, GameObject player, Handler handler) {
        super(x, y, id);

        this.player = player;
        this.handler = handler;
        this.originX = player.getX();
        this.originY = player.getY();
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        float diffX = x - originX - 8;
        float diffY = y - originY - 8;
        float distance = (float) Math.sqrt((x-originX)*(x-originX) + (y-originY)*(y-originY));

        velX = (float) ((-1.0/distance) * diffX) * 2;
        velY = (float) ((-1.0/distance) * diffY) * 2;


        if(this.velX <= .1 && this.velY <= .1 && Exploded == false){
            this.Exploded = true;
            this.renderExplosion();
        }
    }

    @Override
    public void render(Graphics g) {
        if(Exploded == false){
            g.drawImage(Game.enemySS.grab64Image(3, 3, 64, 64), (int)x, (int)y, 64, 64, null);
        }

    }

    @Override
    public Rectangle getBound() {
        return new Rectangle((int)x, (int)y, size,size);
    }

    public int getSize(){
        return size;
    }


    private void renderExplosion(){

        GameObject t = new Explosion(originX, originY, ID.Projectile, 1, handler);
        handler.addObject(t);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.removeObject(t);
                GameObject t2 = new Explosion(originX, originY, ID.Projectile, 2, handler);
                handler.addObject(t2);


                Timer timer2 = new Timer();
                timer2.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.removeObject(t2);
                        GameObject t3 = new Explosion(originX, originY, ID.Projectile, 3, handler);
                        handler.addObject(t3);

                        Timer timer3 = new Timer();
                        timer3.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                handler.removeObject(t3);
                                removeThis();
                            }
                        }, 250);

                    }
                }, 250);
            }
        }, 250);
    }

    public void removeThis(){
        System.out.println("REMOVED OBJECT : " + this);
//        handler.removeObject(this);
    }
}
