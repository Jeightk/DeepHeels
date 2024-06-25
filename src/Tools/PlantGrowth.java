package Tools;

import java.util.Timer;
import java.util.TimerTask;

import Entities.Entity;
import Entities.EntityID;
import GameObjects.TilledSoil;
import GameObjects.Plants.Infertrunk;
import main.GameObject;
import main.Handler;
import main.ID;

public class PlantGrowth {

	Handler handler;
	public PlantGrowth(Handler handler) {
		this.handler = handler;
	}
	
	//TODO: Set player render above plant render
	public void startGrowth(GameObject SoilTile, EntityID seed, int x, int y) {
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				handler.removeObject(SoilTile);
				handler.addObject(new TilledSoil(x,y,ID.TilledSoil,seed,1));
				Timer timer2 = new Timer();
				timer2.schedule(new TimerTask() {
					@Override
					public void run() {
						handler.removeObject(handler.getExactObject(x, y, ID.TilledSoil));
						handler.addObject(new Infertrunk(SoilTile.getX(), SoilTile.getY(), ID.Plant, "Infertrunk", "Defender", handler));
					}
				}, 2*10*1000);
			}
		}, 2*10*1000);
		
	}
	
}
