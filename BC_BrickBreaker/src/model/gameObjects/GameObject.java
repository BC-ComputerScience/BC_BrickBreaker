package model.gameObjects;

import mathematics.Vector;

public abstract class GameObject {
	private Vector pos;
	
	public GameObject(Vector pos){
		this.pos=pos;
	}
	
	public abstract GameObject cloneAt(Vector v);
	
	/**
	 * @return gets a vector pointing from origin to top left corner
	 */
	public Vector getPos(){
		return this.pos;
	}
	public void translate(Vector v){
		pos=pos.add(v);
	}
	public void setPos(Vector v){
		this.pos=v;
	}
}
