package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import mathematics.Vector;
import view.Collision_View;
import view.Renderable;
import trigger.Trigger;

public class Collision_Simulator implements Model{
	int pixelsPerMeter=10;
	private Vector gravity=new Vector(0,0);
	Collision_View view;
	int width,height;
	ArrayList<view.Renderable> rendered;
	ArrayList<Collidable> collidable;
	LinkedList<Trigger> triggers;
	Random rand=new Random();
	CollisionList collisionList;
	double COR=1;
	
	public Collision_Simulator(int width, int height, Collision_View view) {
		this.width=width;
		this.height=height;
		this.view=view;
		this.reset();
	}
	public void setGravity(double n){
		gravity=new Vector(0,pixelsPerMeter*n);
	}
	
	public void addGameObject(Collidable m){
		if(m instanceof Movable)((Movable)m).setCOR(COR);
		this.collidable.add(m);
		if(m instanceof view.Renderable){
			this.rendered.add((view.Renderable)m);
		}
		
	}
	public void removeGameObject(Collidable m){
		this.collidable.remove(m);
		if(m instanceof view.Renderable){
			this.rendered.remove((view.Renderable)m);
		}
		
	}
	public Collidable selectAtPoint(int x, int y){
		return this.collisionList.selectAtPoint(x,y);
	}
	public ArrayList<Collidable> selectInRect(int x, int y, int width, int height){
		return this.collisionList.selectInRect(x,y,width,height);
		
	}
	
	
	public void reset(){
		rendered = new ArrayList<view.Renderable>();
		collidable = new ArrayList<Collidable>();
		triggers= new LinkedList<Trigger>();
		//setCOR(1);
		//setGravity(0);
		rendered.add(new view.Renderable(){
			Image im;
			public Image getImage() {
				if(im==null){
					im=new BufferedImage(1200,900,BufferedImage.TYPE_INT_ARGB);
					Graphics g=im.getGraphics();
					g.setColor(Color.BLACK);
					g.fillRect(0, 0, 1200, 900);
				}
				return im;
			}
			public int getImageX() {return 0;}
			public int getImageY() {return 0;}
		});
	}

	
	private void wallCollision(Movable m){
		double x=m.getX(),y=m.getY(),width=m.getBoundingWidth(),height=m.getBoundingHeight();;
		double time;
		if(x<0){
			time=x/m.getVelX(); 
			m.reflect(new Vector(1,0),time);
		}else if(x+width>this.width){
			time=(x+width-this.width)/m.getVelX();
			m.reflect(new Vector(-1,0),time);
		}
		if(y<0){
			time= y/m.getVelY();
			m.reflect(new Vector(0,1),time);
			
		}else if(y+height>this.height){
			time=(y+height-this.height)/m.getVelY();
			
			m.reflect(new Vector(0,-1),time);
			//m.addForce(new Vector(0,Math.abs(m.getTotalForce().getElement(1))));
		}
	}
	
	public void wallCollision(){
		for(Collidable m : collidable){
			if(m instanceof Movable)wallCollision((Movable)m);
		}

	}

	
	
	public void advance(){
		advance(20.0/1000);
		
	}
	public void advance(double seconds){
		/**/
		
		collisionList=new CollisionList(width,height,10);
		
		
		for(Collidable m:collidable){
			if(m instanceof Movable)((Movable)m).addForce(gravity.scale(((Movable)m).getMass()));
		}
		
		
		Iterator<Collidable> itr=collidable.iterator();
		
		while(itr.hasNext()){
			
			Collidable current= itr.next();
			if(!current.stillExists()){
				itr.remove();
				if(current instanceof Renderable){
					rendered.remove((Renderable)current);
				}
				
			}
			current.advance(seconds);
			collisionList.add(current);
		}
		
		
		collisionList.checkCollisions();
		wallCollision();
		wallCollision();
		
		Iterator<Trigger> triggeritr=this.triggers.iterator();
		while(triggeritr.hasNext()){
			Trigger trigger=triggeritr.next();
			if(trigger.checkConditions()){
				trigger.proc();
			}
			if(!trigger.stillExists()){
				triggeritr.remove();
			}
		}
		
		
		
		/*///depricated
		int length=movable.size();
		for(int i=0;i<length;i++){
			movable.get(i).advance(seconds);
			for(int j=i+1;j<length;j++){
				if(checkCollision(movable.get(i),movable.get(j))){
					movable.get(i).bounce(movable.get(j));
				}
			}
		}/**/
		
		
	}
	public void updateView() {
		if(view!=null)
			view.updateScreen(rendered);
		
	}
	public void setCOR(double parseDouble) {
		this.COR=parseDouble;
		for(Collidable m: collidable){
			if(m instanceof Movable)((Movable)m).setCOR(parseDouble);
		}
		
	}
	public int getWidth() {

		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	@Override
	public List<Collidable> selectInRadius(int x, int y, int radius) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Collidable> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void addTrigger(Trigger t) {
		this.triggers.add(t);
		
	}
	@Override
	public void removeTrigger(Trigger t) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getLives() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getBallCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getBlockCount() {
		return collidable.size();
	}
	
	
	
}
