package Menus;

import Entities.Entity;
import Entities.EntityID;
import Tiles.Tile;
import Tiles.TileMap;
import Tools.MapRender;
import main.Game;
import main.Handler;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class LoadSave {

    private Game game;
    private Handler handler;
    private Inventory inventory;
    private MapRender mapRender;
    private TileMap tileMap;

    public LoadSave(Game game, Handler handler, Inventory inventory, MapRender mapRender, TileMap tileMap){
        this.game = game;
        this.handler = handler;
        this.inventory = inventory;
        this.mapRender = mapRender;
        this.tileMap = tileMap;
    }

    public void render(Graphics g){

    }

    public void loadSaves(){
        System.out.println("Loaded Saves");
        // Create the frame for the application
        JFrame frame = new JFrame("Saves");
        frame.setSize(700, 400);

        // Center the frame on the screen
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int width = frame.getWidth();
        int height = frame.getHeight();
        int x = (screenSize.width - width) / 2;
        int y = (screenSize.height - height) / 2;
        frame.setLocation(x, y);

        // Load the icon image and set it for the JFrame
        try {
            Image iconImage = ImageIO.read(new File("sprites/icon.png"));
            frame.setIconImage(iconImage);
        } catch (IOException e) {
            System.err.println("Icon image file not found or cannot be loaded.");
            e.printStackTrace();
        }

        frame.setLayout(new BorderLayout());

        // Create the list model and add some items to it
        DefaultListModel<String> listModel = loadList();

        // Create the JList and attach the model to it
        JList<String> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Allow single selection

        // Styling the JList
        list.setFont(new Font("Monospaced", Font.BOLD, 14)); // Set monospaced font
        list.setBackground(Color.LIGHT_GRAY); // Set background color
        list.setForeground(Color.DARK_GRAY); // Set text color
        list.setBorder(new EmptyBorder(5, 5, 5, 5)); // Add padding inside the border

        // Convert list items to uppercase
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(
                    JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(((String) value).toUpperCase()); // Transform text to uppercase
                return c;
            }
        });

        // Create a JScrollPane to make the list scrollable
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to JScrollPane

        // Custom Scrollbar UI
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());

        // Create the Gothic Start button
        JButton startButton = new JButton("Start");
        startButton.setEnabled(false); // Initially disabled

        // Apply Gothic style to the button
        startButton.setFont(new Font("Monospaced", Font.BOLD, 18)); // Gothic font
        startButton.setForeground(new Color(255, 0, 0)); // Deep red text color
        startButton.setBackground(new Color(0, 0, 0)); // Black background
        startButton.setOpaque(true); // Ensure background color is visible

        // Add ornate border with dark color
        startButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(128, 0, 0), 3), // Dark red border
                BorderFactory.createEmptyBorder(10, 20, 10, 20))); // Padding inside the button

        // Rounded corners with Gothic border style
        startButton.setFocusPainted(false); // Remove focus border
        startButton.setContentAreaFilled(false); // Remove default button background
        startButton.setBorder(new GothicBorder(15)); // Apply Gothic border with rounded corners

        // Add an ActionListener to the Start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = list.getSelectedValue();
                if (selectedItem != null) {
                    readFile(new File("saves/"+selectedItem+".txt"));
                }
            }
        });

        // Add a ListSelectionListener to enable the Start button when an item is selected
        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                startButton.setEnabled(list.getSelectedIndex() != -1); // Enable button if an item is selected
            }
        });

        // Panel to hold the button
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);

        // Add components to the frame
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set the frame visible
        frame.setVisible(true);
    }

    public DefaultListModel<String> loadList(){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        List<String> items = new ArrayList<>();

        if(new File("saves/").list().length == 0){
            return listModel;
        }
        for(int i = 0; i<new File("saves/").list().length; i++){
            String s = new File("saves/").list()[i].substring(0, new File("saves/").list()[i].indexOf(".")); // trim the .txt off
            items.add(s);
        }
        // Reverse the list to make it descending
        Collections.reverse(items);
        for (String item : items) {
            listModel.addElement(item);
        }


        return listModel;
    }

    // Custom ScrollBar UI
    static class CustomScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            // Set the color for the scroll bar
            this.thumbColor = new Color(100, 100, 100); // Gray color for the thumb
            this.trackColor = new Color(220, 220, 220); // Light gray color for the track
            this.thumbDarkShadowColor = new Color(50, 50, 50); // Dark shadow for the thumb
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createArrowButton(orientation);
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createArrowButton(orientation);
        }

        private JButton createArrowButton(int orientation) {
            JButton button = new JButton();
            button.setBackground(Color.GRAY);
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            return button;
        }
    }

    // Gothic border class for the button
    static class GothicBorder extends AbstractBorder {
        private final int radius;

        GothicBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(new Color(128, 0, 0)); // Dark red color for Gothic border
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(10, 20, 10, 20); // Padding inside the button
        }
    }


    public void readFile(File f){
//        StringBuilder data = new StringBuilder();
        try {
            File myObj = f;
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                setValues(myReader.nextLine());
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public void setValues(String data){
        if(data.isEmpty()){
            return;
        }
        if(data.contains("CHARACTER")){
            int pos = data.indexOf(":");
            String s = data.substring(pos+1, data.length());
            System.out.println("CHARACTER: " + s);

            handler.setCharacterName(s);

        }else if(data.contains("TILLABLE-LAND")){
            int pos = data.indexOf(":");
            String s = data.substring(pos+1, data.length());
            System.out.println("Tillable: " + s);

            game.setTillableLandAmount(Integer.parseInt(s));

        }else if(data.contains("INVENTORY.INVENTORY")){
            int pos = data.indexOf(":");
            String s = data.substring(pos+1, data.length());
            if(s.equals("{}")){
                return;
            }
            String tempString = s;
//            System.out.println("Inventory.Inventory: " + s + " TOTAL: " + findAmountOfObjects(s));
            for(int i = 0; i <= findAmountOfObjects(s); i++){
                System.out.println(i);
                String name;
                String amount;

                if(i == 0 && findAmountOfObjects(s) > 1 || i == 0 && findAmountOfObjects(s) == 1){
                    name = tempString.substring(2, tempString.indexOf('='));
                }else{
                    name = tempString.substring(1, tempString.indexOf('='));
                }

                if(i == 0 && findAmountOfObjects(s) == 1 || i == findAmountOfObjects(s)){
                    amount = tempString.substring(tempString.indexOf('=')+1, tempString.indexOf('}'));
                }else{
                    amount = tempString.substring(tempString.indexOf('=')+1, tempString.indexOf(','));
                }
                inventory.inventory.put(EntityID.valueOf(name), Integer.parseInt(amount));

//                System.out.println("NAME: " + name + " Amount: " + amount);
                tempString=tempString.substring(tempString.indexOf(',')+1, tempString.length());
//                System.out.println("NEW: " + tempString);
//                s = s.substring(s.indexOf(',')+1, s.length());
            }

        }else if(data.contains("INVENTORY.equippedGear")){
            int pos = data.indexOf(":");
            String s = data.substring(pos+1, data.length());
            if(s.equals("{}")){
                return;
            }
            String tempString = s;
            System.out.println("Inventory.equippedGear: " + s + " TOTAL: " + findAmountOfObjects(s));
            for(int i = 0; i < findAmountOfObjects(s); i++){
                System.out.println(i);
                String tile;
                String entity;

                if(i == 0 && findAmountOfObjects(s) > 1 || i == 0 && findAmountOfObjects(s) == 1){
                    tile = tempString.substring(2, tempString.indexOf('='));
                }else{
                    tile = tempString.substring(1, tempString.indexOf('='));
                }

                if(i == 0 && findAmountOfObjects(s) == 1 || i == findAmountOfObjects(s)){
                    entity = tempString.substring(tempString.indexOf('=')+1, tempString.indexOf('}'));
                }else{
                    entity = tempString.substring(tempString.indexOf('=')+1, tempString.indexOf(','));
                }

                System.out.println("tile: " + tile + " entity: " + entity);

                if(entity.contains("Entities.EntityTill")){
                    System.out.println("test");
//                    inventory.equippedGear.put(new, (tobj));
                }

                tempString=tempString.substring(tempString.indexOf(',')+1, tempString.length());
                System.out.println("NEW: " + tempString);




//                inventory.Gear[5] = tobj;


            }

        }else if(data.contains("INVENTORY.inventoryRegions")){
            int pos = data.indexOf(":");
            String s = data.substring(pos+1, data.length());
            if(s.equals("{}")){
                return;
            }
        }


        game.gameState = Game.STATE.Game;
        mapRender.renderMap();
        tileMap.setTiles();


    }

    public int findAmountOfObjects(String s){
        int count = 0;

        if(!s.contains(",")){
            return 1;
        }

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == ',') {
                count++;
            }
        }
        return count;
    }
}
