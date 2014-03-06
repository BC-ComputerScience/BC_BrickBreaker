package model.gameObjects;

import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import controller.Event;
import controller.GameEventListener;
import controller.GameObjectEvent;
import controller.LogicalSystem;
import mathematics.Vector;

public abstract class GameObject {
	private static Thread eventDispatch;
	private static BlockingQueue<GameObjectEvent> eventQueue;
	static{
		eventQueue=new LinkedBlockingQueue<GameObjectEvent>();
		eventDispatch=new Thread(new Dispatcher());
		eventDispatch.start();
	}
	
	
	
	private LinkedList<GameEventListener> listeners;
	private Vector pos;
	
	public GameObject(Vector pos){
		this.pos=pos;
		this.listeners=new LinkedList<GameEventListener>();
	}

	
	public Vector getPos(){
		return this.pos;
	}
	public void translate(Vector v){
		pos=pos.add(v);
	}
	public void setPos(Vector v){
		this.pos=v;
	}
	
	public abstract void advance(double seconds);
	public abstract GameObject cloneAt(Vector v);
	
	
	
	
	
	public void fireEvent(GameObjectEvent e){
		try {
			eventQueue.put(e);
		} catch (InterruptedException e1) {
			
		}
	}
	public void addGameEventListener(GameEventListener gl){
		this.listeners.add(gl);
	}
	
	public void dispatchEvent(GameObjectEvent event) {
		for(GameEventListener gl:this.listeners){
			if(!event.isConsumed()){
				gl.gameEventHappened(event);
			}
		}
	}
	
	private static class Dispatcher implements Runnable{
		public void run() {
			while(true){
				try {
					GameObjectEvent event=eventQueue.take();
					event.getObject().dispatchEvent(event);
				} catch (InterruptedException e) {
					System.err.println("something interupted the event thread");
				}
			}
			
		}
		
	}

	
	
}
