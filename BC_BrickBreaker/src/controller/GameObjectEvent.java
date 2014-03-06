package controller;

import model.gameObjects.GameObject;

public class GameObjectEvent extends Event {
	private GameObject go;
	public GameObjectEvent(GameObject go){
		this.go=go;
	}
	public GameObject getObject(){
		return go;
	}
}
