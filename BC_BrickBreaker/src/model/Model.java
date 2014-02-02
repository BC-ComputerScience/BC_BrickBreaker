package model;

import java.util.List;

import model.gameObjects.Collidable;
import model.gameObjects.GameObject;

public interface Model {
	public Collidable selectAtPoint(int x, int y);
	public List<Collidable> selectInRect(int x, int y, int width, int height);
	public List<Collidable> selectInRadius(int x, int y, int radius);
	public List<Collidable> selectAll();
	
	public void addGameObject(GameObject m);
	public void removeGameObject(GameObject m);
	
	public void addTrigger(trigger.Trigger t);
	public void removeTrigger(trigger.Trigger t);
	
	public int getScore();
	public int getLives();
	public int getBallCount();
	public int getBrickCount();
	
	public void advance(double time);
	public void updateView();

}
