package Tools;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import main.BufferedImageLoader;
import main.Game;
import main.SpriteSheet;
import javax.swing.*;

public class GrassLandscape {

    private BufferedImage landscape;
    private Game game;

    private int[] flowerX, flowerY, swayDirection;
    
    private boolean once = true;
    private boolean animate = false;
    public GrassLandscape(Game game) {
        landscape = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        this.game = game;
    }

    private void drawGrass() {
    	Graphics2D g2d = landscape.createGraphics();
    	if(animate) {
    		drawGrassWithFlowers(g2d);
    		return;
    	}
    	flowerX = new int[50];
        flowerY = new int[50];
        swayDirection = new int[50];

        // Initialize flower positions and sway directions
        for (int i = 0; i < 50; i++) {
            flowerX[i] = (int) (Math.random() * game.getWidth());
            flowerY[i] = game.getHeight() / 2 + (int) (Math.random() * (game.getHeight() / 2 - 40));
            swayDirection[i] = Math.random() > 0.5 ? 1 : -1;
        }

        // Start animation timer
        Timer timer = new Timer(50, e -> animateFlowers());
        timer.start();
    	
    	
        
        // Draw sky
        g2d.setColor(new Color(135, 206, 250));  // Light blue color for the sky
        g2d.fillRect(0, 0, game.getWidth(), game.getHeight());

        // Draw sun
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(50, 50, 80, 80);

        // Draw hills
        g2d.setColor(new Color(46, 139, 87));  // Dark green color for hills
        g2d.fillArc(150, game.getHeight() / 2 - 100, 200, 200, 0, 180);
        g2d.fillArc(400, game.getHeight() / 2 - 80, 250, 250, 0, 180);

        // Draw grass
        g2d.setColor(new Color(34, 139, 34));  // Green color for the grass
        g2d.fillRect(0, game.getHeight() / 2, game.getWidth(), game.getHeight() / 2);

        // Draw clouds
        g2d.setColor(Color.WHITE);
        g2d.fillOval(200, 100, 80, 40);
        g2d.fillOval(240, 120, 80, 40);
        g2d.fillOval(280, 100, 80, 40);

        drawTexturedSky(g2d);
        drawGrassWithFlowers(g2d);
        
        g2d.dispose();
    }

    private void drawTexturedSky(Graphics2D g2d) {
    	Color startColor = new Color(173, 216, 230);  // Light blue color for the sky
        Color endColor = new Color(135, 206, 250);    // Darker blue color for the horizon

        // Create a gradient from the top to the bottom of the panel
        GradientPaint gradient = new GradientPaint(0, 0, startColor, 0, game.getHeight(), endColor);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, game.getWidth(), game.getHeight());
    }

    private void drawGrassWithFlowers(Graphics2D g2d) {
    	g2d.setColor(new Color(34, 139, 34));  // Green color for the grass

        // Draw grass
        g2d.fillRect(0, game.getHeight() / 2, game.getWidth(), game.getHeight() / 2);

        // Draw detailed animated flowers in the grass
        g2d.setColor(Color.RED);
        for (int i = 0; i < 50; i++) {
            int x = flowerX[i];
            int y = flowerY[i];

            // Draw flower stem
            g2d.setColor(new Color(139, 69, 19));  // Brown color for the stem
            g2d.fillRect(x + 4, y, 2, 20);

            // Draw flower petals
            g2d.setColor(Color.YELLOW);
            g2d.fillOval(x, y - 10, 10, 10);
            g2d.fillOval(x + 5, y - 15, 10, 10);
            g2d.fillOval(x + 10, y - 10, 10, 10);
            g2d.fillOval(x + 5, y - 5, 10, 10);
        }
    }
    private void animateFlowers() {
        // Update flower positions for animation
        for (int i = 0; i < 50; i++) {
            flowerX[i] += swayDirection[i];
            flowerY[i] += swayDirection[i];

            // Change sway direction when reaching the edge
            if (flowerX[i] <= 0 || flowerX[i] >= game.getWidth() - 10) {
                swayDirection[i] *= -1;
            }
        }
        animate = true;
        drawGrass();
        // Repaint the panel to reflect the changes
    }

    
//    public void init() {
//		BufferedImageLoader loader = new BufferedImageLoader();
//		try {
//			landscape = loader.loadImage(getLandscape());
//		}catch(IOException e) {
//			e.printStackTrace();
//		}
//		
//	}
    public BufferedImage getLandscape() {
    	if(once) {
    		drawGrass();
    		once = false;
    	}
        return landscape;
    }
}
