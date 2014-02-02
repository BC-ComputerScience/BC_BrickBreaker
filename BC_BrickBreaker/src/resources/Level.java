package resources;
import java.util.ArrayList;

import mathematics.Vector;
import model.Model;
import model.gameObjects.GameObject;
public class Level {

	ArrayList<GameObject> objects = new ArrayList<GameObject>();
	double COR=1;
	Vector gravity=new Vector(0,0);
	
	public Level() {
		// TODO Auto-generated constructor stub
	}
	public void AddObject(GameObject o){
		objects.add(o);
	}
	
	public void LoadIntoModel(Model m){
		if(m instanceof model.Collision_Simulator){
			((model.Collision_Simulator) m).setGravity(this.gravity);
		}
		for(GameObject o:objects){
			m.addGameObject(o);
		}
	}
	
	public void addGameObject(GameObject o){
		this.addGameObject(o,true);
	}
	public void addGameObject(GameObject o, boolean defaultCOR){
		if(defaultCOR&&(o instanceof model.gameObjects.Movable)){
			((model.gameObjects.Movable)o).setCOR(this.COR);
		}
		objects.add(o);
	}
	public void setDefaultCOR(double COR){
		this.COR=COR;
	}
	public void setGravity(Vector g){
		this.gravity=g;
	}

}
