package Tools;

import GameObjects.Plant;
import main.GameObject;

import java.util.Timer;
import java.util.TimerTask;

public class ShootMecha {

    private GameObject shooter;
    private GameObject target;
    private Timer timer;
    private boolean firstEntered = false;

    public ShootMecha(GameObject shooter, GameObject target){
        this.shooter = shooter;
        this.target = target;
    }

    public void shoot(){
        try{
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(target.getHP() <= 0){
                        stopShoot();
                        return;
                    }
                    ((Plant)shooter).shoot(target);
                }
                //DELAY IS BUILD UP, AND PERIOD IS HOW OFTEN.
            }, 0, ((Plant)shooter).getShootSpeed());
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void stopShoot(){
        timer.cancel();
    }
}
