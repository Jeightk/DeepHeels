package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import Entities.Entity;
import GameObjects.TilledSoil;
import Tiles.Tile;
import Tiles.TileMap;

public class Handler {
	
	public LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	
	public void tick() {
		
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			tempObject.tick();
		}
		
	}
	
	public void render(Graphics g) {		
		for(int i = 0; i < object.size(); i++) {
			GameObject tempObject = object.get(i);
			
			tempObject.render(g);
		}
		
		
		
	}
	//OVERLOAD FOR WHEN A POSITION IS NEEDED FOR THE OBJECT IN THE LIST
	public void addObject(GameObject object, int position) {
		this.object.add(position, object);
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
	
	public GameObject getObject(ID IdOfObject) {
		for(GameObject obj : object) {
			if(obj.getID() == IdOfObject) {
				return obj;
			}
		}
		return null;
	}
	
	public int GetObjectPos(GameObject obj) {
		if(object.contains(obj)) {
			for(int i = 0; i < object.size(); i++) {
				if(object.get(i) == obj) {
					return i;
				}
			}
		}
		return 100;
	}
	
	public GameObject getExactObject(int xpos, int ypos, ID idpos) {
		for(GameObject obj : object) {
			if(obj.getID() == idpos) {
				if(obj.getBound().contains(xpos, ypos)) {
					return obj;
				}
			}
		}
		return null;
	}
	
	public boolean isSeeded(int xpos, int ypos, ID idpos) {
		for(GameObject obj : object) {
			if(obj.getID() == idpos) {
				if(obj.getBound().contains(xpos, ypos)) {
					TilledSoil ts = (TilledSoil) obj;
					return ts.isSeeded();
				}
			}
		}
		return false;
	}
	
	public ID getTile(int mouseX, int mouseY) {
		for(GameObject obj : object) {
			if(obj.getBound().contains(mouseX, mouseY)) {
				return obj.getID();
			}
		}
		return null;
	}
	
	public void queueRemove(GameObject object) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				removeObject(object);
			}
		}, 2*10*1000);
	}
	
	public static HashMap<GameObject, Integer> sortByValue(HashMap<GameObject, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<GameObject, Integer> > list =
               new LinkedList<Map.Entry<GameObject, Integer> >(hm.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<GameObject, Integer> >() {
            public int compare(Map.Entry<GameObject, Integer> o1,
                               Map.Entry<GameObject, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
         
        // put data from sorted list to hashmap
        HashMap<GameObject, Integer> temp = new LinkedHashMap<GameObject, Integer>();
        for (Map.Entry<GameObject, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
	

}
