package Levels;

import GameObjects.Enemies.Thief;
import GameObjects.Plant;
import main.Game;
import main.Handler;
import main.ID;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class LevelHandler {

    private Handler handler;
    private Game game;

    public boolean countdownStart = false;
    private int count = 3;
    private Timer timer;
    private JLabel countLabel;

    public int CurrentLevel = 0;
    Random rand;


    public LevelHandler(Handler handler, Game game){
        this.handler = handler;
        this.game = game;
        rand = new Random();
    }

    public void AscendLevel(){
        System.out.println("Level Starting...");
        this.CurrentLevel +=1;
        populateMobs();
    }

    public void populateMobs(){
        switch(this.CurrentLevel){
            case 1:
                handler.addObject(new Thief(rand.nextFloat(Game.WIDTH - 20), rand.nextFloat(Game.HEIGHT - 20), ID.Enemy, 100, 10, 3000, handler));
                handler.addObject(new Thief(rand.nextFloat(Game.WIDTH - 20), rand.nextFloat(Game.HEIGHT - 20), ID.Enemy, 100, 10, 3000, handler));
        }
    }

    public void render(Graphics g){
        if(countdownStart){
            startCountdown(g);
        }

    }
    public void startCountdown(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Courier", 1, 200));
        g.drawString(count+"..", (Game.WIDTH / 2)-100, Game.HEIGHT / 2);


        g.dispose();
    }

    public void startTimer(){
        try{
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if(count <= 0){
                        startLevel();
                        return;
                    }

                    count-=1;
                }
                //DELAY IS BUILD UP, AND PERIOD IS HOW OFTEN.
            }, 1000, 1000);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void startLevel(){
        timer.cancel();
        AscendLevel();
        countdownStart = false;
        count = 3;
    }

}
