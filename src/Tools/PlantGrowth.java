package Tools;

import java.util.Timer;
import java.util.TimerTask;

import Entities.Entity;
import Entities.EntityID;
import GameObjects.Plants.Lilypat;
import GameObjects.Plants.Viperus;
import GameObjects.TilledSoil;
import GameObjects.Plants.Infertrunk;
import Tiles.TileMap;
import main.GameObject;
import main.Handler;
import main.ID;

public class PlantGrowth {

	Handler handler;

	public boolean FarmingMode = false;

	private TileMap tileMap;
	private MapRender mapRender;

	public PlantGrowth(Handler handler, TileMap tileMap, MapRender mapRender) {
		this.handler = handler;
		this.tileMap = tileMap;
		this.mapRender = mapRender;
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
						if(seed == EntityID.EntityFlamecrestSeed){
							handler.addObject(new Infertrunk(SoilTile.getX(), SoilTile.getY(), ID.Plant, "Infertrunk", "Defender", handler));
						}else if(seed == EntityID.EntityViperusSeed){
							handler.addObject(new Viperus(SoilTile.getX(), SoilTile.getY(), ID.Plant, "Viperus", "Defender"));
						}else if(seed == EntityID.EntityLilypatSeed){
							handler.addObject(new Lilypat(SoilTile.getX(), SoilTile.getY(), ID.Plant, "Lilypat", "Defender"));
						}
						tileMap.updateTiles(FarmingMode);
						mapRender.updatePlayerRender();
					}
				}, 2*10*1000);
			}
		}, 2*10*1000);



	}

	public boolean getFarmingMode(){
		return FarmingMode;
	}
	public void setFarmingMode(boolean t){
		this.FarmingMode = t;
	}
	
}
