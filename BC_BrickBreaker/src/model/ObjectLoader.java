package model;

import mathematics.Vector;
import model.gameObjects.GameObject;

import java.util.HashMap;

public class ObjectLoader {
	public static final ObjectLoader LOADER=new ObjectLoader(true);
		
	
	private HashMap<String,GameObject> map;
	private boolean isDefault=false;
	
	public ObjectLoader(){
		map = new HashMap<String,GameObject>();
	}
	private ObjectLoader(boolean isDefault){
		this();
		this.isDefault=isDefault;
	}
	public GameObject createAt(String name, Vector v){
		System.out.println(map.keySet());
		System.out.println(name);
		GameObject template=map.get(name);
		System.out.println(template);
		if(template==null&&!isDefault){
			template=LOADER.map.get(name);
		}
		if(template!=null){
			System.out.println("derp");
			System.out.println(template.cloneAt(v));
			return template.cloneAt(v);
		}
		System.out.println("nrul");
		return null;
	}
	public void addTemplate(String s, GameObject o){
		map.put(s, o);
	}
}
