package Saving;

import Menus.Inventory;
import main.Game;
import main.Handler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveFile {

//    private Map<String, List<?>> saveMaterial = new HashMap<>();
    private String saveMaterial = "";

    private Inventory inventory;
    private Game game;
    private Handler handler;

    //list to show the position of the equippedgear
    private HashMap<Integer, Object> updatedGearEquipped = new HashMap<>();

    public SaveFile(Game game, Inventory inventory, Handler handler){
        this.game = game;
        this.inventory = inventory;
        this.handler = handler;
    }


    public void saveToFile() throws IOException {
        detailSave();
        getAllThingsLists();


        if(new File("saves/").list().length <= 5){

            createUpdatedSave(saveMaterial);

        }else{
            try {
                Files.delete(Path.of("saves/"+new File("saves/").list()[0]));
            } catch (NoSuchFileException x) {
                System.err.format("%s: no such" + " file or directory%n", Path.of("saves/"+new File("saves/").list()[0]));
            } catch (DirectoryNotEmptyException x) {
                System.err.format("%s not empty%n", Path.of("saves/"+new File("saves/").list()[0]));
            } catch (IOException x) {
                // File permission problems are caught here.
                System.err.println(x);
            }

            createUpdatedSave(saveMaterial);


            System.out.println(new File("saves/").list()[0]);
        }

    }

    private void createUpdatedSave(String str) throws IOException{
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String fixednow = now.toString().replace(":", "-").replace(".","");

        BufferedWriter writer = new BufferedWriter(new FileWriter("saves/savefile"+ fixednow +".txt"));
        writer.write(str);

        writer.close();
    }

    private void getAllThingsLists(){
        saveMaterial = "";
        saveMaterial = saveMaterial+"\n"+"CHARACTER:" + handler.getCharacterName();
        saveMaterial = saveMaterial+"\n"+"TILLABLE-LAND:" + game.getTillableLandAmount();
        saveMaterial = saveMaterial+"\n"+"INVENTORY.INVENTORY: "+inventory.inventory.toString();
        saveMaterial = saveMaterial+"\n"+"INVENTORY.equippedGear: "+inventory.equippedGear.toString();
        saveMaterial = saveMaterial+"\n"+"INVENTORY.Gear: "+inventory.Gear.toString();
        saveMaterial = saveMaterial+"\n"+"INVENTORY.itemLocation: "+inventory.itemLocation.toString();


    }

    //this method adds the position of the items in the gear slots for easier loading of the save.
    private void detailSave(){
        for(int i = 0; i < inventory.Gear.length; i++){
            System.out.println(inventory.Gear[i]);
        }
    }

}
