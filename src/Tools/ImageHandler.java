package Tools;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageHandler {

	public static final int FLIP_VERTICAL = 1;
	public static final int FLIP_HORIZONTAL = -1;
	
	public static Image rotate(BufferedImage bimg, Double angle) {
	    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
	           cos = Math.abs(Math.cos(Math.toRadians(angle)));
	    int w = bimg.getWidth();
	    int h = bimg.getHeight();
	    int neww = (int) Math.floor(w*cos + h*sin),
	        newh = (int) Math.floor(h*cos + w*sin);
	    BufferedImage rotated = new BufferedImage(neww, newh, bimg.getType());
	    Graphics2D graphic = rotated.createGraphics();
	    graphic.translate((neww-w)/2, (newh-h)/2);
	    graphic.rotate(Math.toRadians(angle), w/2, h/2);
	    graphic.drawRenderedImage(bimg, null);
	    graphic.dispose();
	    return rotated;
	}
	
	public static Image flip(BufferedImage img, int direction) {
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		switch(direction) {
		case FLIP_HORIZONTAL:
			tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-img.getWidth(null), 0);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			img = op.filter(img, null);
		
		
		
		}
		
		return img;
//		try {
//			int width = img.getWidth();
//			int height = img.getHeight();
//			BufferedImage flipped = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//			
//			for(int y = 0; y < height; y++) {
//				for(int x = 0; x < width; x++) {
//					switch(direction) {
//					case FLIP_HORIZONTAL:
//						flipped.setRGB((width-1) - x, y, img.getRGB(x, y));
//						break;
//					case FLIP_VERTICAL:
//						flipped.setRGB((width-1) - x, y, img.getRGB(x, y));
//						break;
//					}
//				}
//			}
//			return flipped;
//		}
//		
//		catch(Exception e) {
//			e.printStackTrace();
//		}
//		return null;
	}
	
}
