package Menus;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Tools.GrassLandscape;
import Tools.JRectangle;
import main.BufferedImageLoader;
import main.Game;
import main.Game.STATE;


public class MainMenu{

	private Game game;
	private GrassLandscape grasslandscape;
	
	private int rectangleX = 0;
	private int rectangleY = 20;
	
	private int rectangleA = 0;
	private int rectangleB = 620;
	
	public boolean renderPlayButton = false;
	public boolean renderQuitButton = false;
	public boolean renderLoadButton = false;
	public boolean renderAboutButton = false;
	public boolean renderLetter = false;
	public boolean isCharacterSelect = false;
	
	public static BufferedImage mb1 = null;
	
	public JRectangle d = new JRectangle(19,117,63,90,1);
	public JRectangle e1 = new JRectangle(19,117,63,90,1);
	public JRectangle e2 = new JRectangle(19,117,63,90,1);
	public JRectangle p = new JRectangle(19,117,63,90,1);
	List<JRectangle> Letters = new ArrayList<>(Arrays.asList(d, e1));
	
	public MainMenu(Game game, GrassLandscape grasslandscape) {
		this.game = game;
		this.grasslandscape = grasslandscape;
	}
	
	public void render(Graphics g) {
		if(isCharacterSelect == false){
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(Color.BLACK);
			g2.fillRect(rectangleX, rectangleY, 20, 20);
			g2.fillRect(rectangleA, rectangleB, 20, 20);

//		g2.drawImage(grasslandscape.getLandscape(), null, 300, 300);
			g.drawImage(grasslandscape.getLandscape(), 0, 0, game.getWidth(), game.getHeight(), null);
			if(renderPlayButton) {
				g.drawImage(Game.mainMenubackgroundPlayClick, 0, 0, null);
			}else if(renderLoadButton){
				g.drawImage(Game.mainMenubackgroundLoadClick, 0, 0, null);
			}else if(renderQuitButton) {
				g.drawImage(Game.mainMenubackgroundQuitClick, 0, 0, null);
			}else if(renderAboutButton) {
				g.drawImage(Game.mainMenubackgroundAboutClick, 0, 0, null);
			}else if(renderLetter) {

			}else {
				g.drawImage(Game.mainMenubackground, 0, 0, null);
			}
		}

		
	}
	
	private int direction = 1;
	private int speed = 10;
	public void tick() {
		if(isCharacterSelect == false){
			rectangleX+=speed * direction;
			rectangleA+=speed * direction;
			if(rectangleX >= Game.WIDTH) {
				direction = -1;
			}else if(rectangleX <= 0) {
				direction = 1;
			}
		}

	}
	
	
	public void findLetterToRender(int mouseX, int mouseY) {
		Point point = new Point(mouseX, mouseY);
		for(int i = 0; i < Letters.size(); i++) {
			if(Letters.get(i).contains(point)) {
//				g.drawImage(Game.main, 0, 0, null);
				
			}
		}
		this.renderQuitButton = false;
		this.renderLoadButton=false;
		this.renderPlayButton=false;
		this.renderAboutButton=false;
	}
	
	
	public void renderImage() {
		BufferedImageLoader loader = new BufferedImageLoader();
		
	}
	
	
	
}
