package Menus;

import main.Game;

import java.awt.*;

public class CharacterSelect {

    private Game game;

    public boolean isActive = false;
    public boolean highlight = false;

    public boolean permaHighlight = false;
    public boolean renderName = false;

    private int highlightX = 0;
    private int highlightY = 0;

    private int nameX = 0;
    private int nameY = 0;
    public String name = "";

    public boolean characterSelected = false;

    public CharacterSelect(Game game){
        this.game = game;
    }


    public void render(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        if(isActive){
            g2.setColor(Color.darkGray);
            g2.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
            g.drawImage(Game.characterSelectIMG, Game.WIDTH/2 - 400, Game.HEIGHT / 2 - 300, null);
            if(highlight || permaHighlight){
                g.drawImage(Game.ss.grabImage(2, 7, 64, 64), (int)highlightX, (int)highlightY, null);
            }

            if(renderName){
                g2.setColor(Color.BLACK);
                g2.drawString(name, nameX, nameY);
                g.drawImage(Game.startButtonHOVERIMG, (Game.WIDTH/2 - 400)+301, (Game.HEIGHT / 2 - 300)+485, null);
            }

        }
    }

    public void setRenderName(String name, int x, int y){
        renderName = true;
        this.name = name;
        this.nameX = x;
        this.nameY = y;

    }

    public void hightlightCharacter(int x, int y){
        highlight = true;
        this.highlightX = x;
        this.highlightY = y;
    }

    public void tick(){
        if(isActive){

        }
    }

}
