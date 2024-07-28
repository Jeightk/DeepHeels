package GameObjects.Enemies;

import Entities.Entity;
import GameObjects.Enemy;
import HUD.Healthbar;
import Projectiles.Barrel;
import main.Game;
import main.GameObject;
import main.Handler;
import main.ID;

import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Burnard extends Enemy {

    private Healthbar healthbar;
    private Handler handler;

    private int size = 64;

    GameObject player;

    private int toss;
    private boolean tossed;
    private Timer timer;

    public Burnard(float x, float y, ID id, int HP, long AttackSpeed, GameObject player, Handler handler) {
        super(x, y, id, HP, AttackSpeed);
        this.handler = handler;
        this.player = player;
        toss = 0;

        healthbar = new Healthbar(10, 10, (int)getX(), (int)getY()-50);
    }

    @Override
    public void render(Graphics g) {

        switch(toss){
            case 0:
                g.drawImage(Game.enemySS.grab64Image(1, 3, 64, 64), (int)x, (int)y, 64, 64, null);
                break;
            case 1:
                g.drawImage(Game.enemySS.grab64Image(2, 3, 64, 64), (int)x, (int)y, 64, 64, null);
                break;
            case 2:
                g.drawImage(Game.enemySS.grab64Image(4, 3, 64, 64), (int)x, (int)y, 64, 64, null);
                break;
        }

        healthbar.render(g, getHP(), getBASEHP());
    }

    @Override
    public Rectangle getBound() {
        return new Rectangle((int)x, (int)y, size,size);
    }

    @Override
    public void tick() {
//        hpCheck();
        x += velX;
        y += velY;
//		counter++;
        float diffX = x - player.getX() - 8;
        float diffY = y - player.getY() - 8;
        float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));
        if(distance >= 250){
            velX = (float) ((-1.0/distance) * diffX);
            velY = (float) ((-1.0/distance) * diffY);
            toss = 0;
            if(tossed){
                tossed = false;
                timer.cancel();
            }
        }else if(distance < 250){
            if(toss == 0){
                toss = 1;
            }

            velX = 0;
            velY = 0;
            if(tossed == false){
                tossed = true;
                renderTossingMechanism(tossed);
                System.out.println("FINAL CALL");
            }
        }



        healthbar = new Healthbar(10, 10, (int)getX()+5, (int)getY()-15);

    }

    @Override
    public List<Entity> getItemDrops() {
        return List.of();
    }

    public void objectCleanup(){
        tossed = false;
        timer.cancel();
        this.handler.removeObject(this);

    }

    @Override
    public int getDMG() {
        return 0;
    }

    public int getSize(){
        return size;
    }

    public void renderTossingMechanism(boolean tossed){
        timer = new Timer();
        if(tossed){
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(getTossed() == false){
                        timer.cancel();
                        return;
                    }
                    System.out.println(getTossed());
//                    System.out.println(player.getX() + " : " + player.getY() + " : " + x + " : " + y);
                    handler.addObject(new Barrel(x, y, ID.Projectile, player, handler));
                    toss = 2;

                    Timer timer2 = new Timer();
                    timer2.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            toss = 1;
                            timer2.cancel();
                        }
                    }, 1000);
                }
            }, 500, 2000);
        }
    }

    public boolean getTossed(){
        return tossed;
    }
}
