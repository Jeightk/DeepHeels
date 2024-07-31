package Menus;

import Saving.SaveFile;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class Settings extends KeyAdapter {

    private SaveFile sv;

    public Settings(SaveFile sv){
        this.sv = sv;
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

//        if(key == KeyEvent.VK_Z){
//            System.out.println("TOCK");
//        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if(key == KeyEvent.VK_Z){
            try {
                sv.saveToFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
