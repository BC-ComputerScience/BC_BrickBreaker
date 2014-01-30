package controller;

public abstract class Event {
	private long time;
	private boolean isConsumed=false;
	public Event(){
		this(System.currentTimeMillis());
	}
	public Event(long time){
		this.time=time;
	}
	public long getWhen(){
		return time;
	}
	public boolean isConsumed(){
		return isConsumed;
	}
	public void consume(){
		this.isConsumed=true;
	}
	
	
}
