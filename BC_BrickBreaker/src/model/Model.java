package model;

import java.util.List;

public interface Model {
	public Collidable selectAtPoint(int x, int y);
	public List<Collidable> selectInRect(int x, int y, int width, int height);
	public List<Collidable> selectInRadius(int x, int y, int radius);
	public List<Collidable> selectAll();
	
	public void addGameObject(Collidable m);
	public void removeGameObject(Collidable m);
	
	public void addTrigger(trigger.Trigger t);
	public void removeTrigger(trigger.Trigger t);
	
	public int getScore();
	public int getLives();
	public int getBallCount();
	public int getBlockCount();

}
