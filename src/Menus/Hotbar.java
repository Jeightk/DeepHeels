package Menus;

import main.Game;

import java.awt.*;

//Not implemented, currently dont see a use for it.. maybe in the future

public class Hotbar {

    private Inventory inventory;

    public Hotbar(Inventory inventory){
        this.inventory = inventory;
    }

    public void render(Graphics g){
//        g.drawImage(Game.hotbarIMG, Game.WIDTH - (360+100), Game.HEIGHT -(108+75), null);
//        renderItems(g);
    }

    public void renderItems(Graphics g){
        if(inventory.getOffhand() != null){
//            System.out.println(inventory.getOffhand());
        }
    }

}
