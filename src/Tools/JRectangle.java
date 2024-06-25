package Tools;

import java.awt.Point;
import java.awt.Rectangle;

public class JRectangle extends Rectangle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	private int width;
	private int height;
	private int id;
	
	public JRectangle(int x, int y, int width, int height, int id) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
	}

	
	public boolean isMouseInside(int objX, int objY) {
		
		Point p = new Point(objX, objY);
		
		if(this.contains(p)) {
			return true;
		}else {
			return false;
		}
	}
	
}
