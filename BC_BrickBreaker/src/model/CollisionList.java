package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mathematics.Vector;
/**
 * 
 * @author Anthony Klobas
 * 
 * note: if objects move, make a new list, this does not support shrinking (yet)
 *
 */
public class CollisionList {
	private static final int EAST=1,SOUTH=2;
	private static final int SOUTH_EAST=3;
	private static final int CENTER=0;
	private CollisionList subLists[];
	private ArrayList<Collidable> stationary;
	private ArrayList<Movable> movable;
	
	
	private int minX,minY,maxX,maxY;
	private int midX,midY;
	
	
	
	private int maxContained;
	private boolean isTop, isBottom;
	//This Constructor comes with a massive performance cost
	public CollisionList(){
		this(Integer.MAX_VALUE,Integer.MAX_VALUE);
	}
	public CollisionList(int n){
		this(Integer.MAX_VALUE,Integer.MAX_VALUE,n);
	}
	public CollisionList(int width,int height){
		this(width,height,10);//10 is default max per list
	}
	public CollisionList(int width,int height,int maxContained){
		this.minX=this.minY=0;
		this.maxX=width;
		this.maxY=height;
		this.maxContained=maxContained;
		isTop=true;
		isBottom=true;

		
		stationary=new ArrayList<Collidable>();
		movable=new ArrayList<Movable>();
	}
	/**
	 * this method is only to be used internally for instantiation of subLists;
	 */
	private CollisionList(int minX,int maxX, int minY, int maxY,int maxContained, CollisionList levelUp){
		this.minX=minX;
		this.maxX=maxX;
		this.minY=minY;
		this.maxY=maxY;
		this.maxContained=maxContained;
		isTop=false;
		isBottom=true;
		stationary=new ArrayList<Collidable>();
		movable=new ArrayList<Movable>();
	}
	
	private void toSublists(){
		int newWidth=(maxX-minX)/2;
		int newHeight=(maxY-minY)/2;
		if(newWidth<=2||newHeight<=2){
			return;//if everything is in the same spot, you'd get infinite recursion
		}
		midX=minX+newWidth;
		midY=minY+newHeight;
		
		this.subLists=new CollisionList[4];
		this.subLists[CENTER]=new CollisionList(minX,minX+newWidth,minY,minY+newHeight,maxContained, this);
		this.subLists[SOUTH]=new CollisionList(minX,minX+newWidth,minY+newHeight,maxY,maxContained, this);
		this.subLists[EAST]=new CollisionList(minX+newWidth,maxX,minY,minY+newHeight,maxContained, this);
		this.subLists[SOUTH_EAST]=new CollisionList(minX+newWidth,maxX,minY+newHeight,maxY,maxContained, this);
		isBottom=false;
		for(Collidable c: this.stationary){
			add(c);
		}
		for(Movable m: this.movable){
			add(m);
		}
		this.movable=null;
	}
	
	
	public void add(Collidable c){
		if(isBottom){
			if(c instanceof Movable){
				this.movable.add((Movable)c);
			}else{
				this.stationary.add(c);
			}
			if(movable.size()+stationary.size()/10>this.maxContained) toSublists();
		}else{
			if(c.getX()>midX){//add to east/southeast
				
				if(c.getY()>midY){
					
					subLists[CollisionList.SOUTH_EAST].add(c);
				}else{
					subLists[CollisionList.EAST].add(c);
					
					if(c.getY()+c.getBoundingHeight()>midY){

						subLists[CollisionList.SOUTH_EAST].add(c);
					}
				}/**/
				
			}else{
				
				if(c.getX()+c.getBoundingWidth()>midX){//overlaps east&west
					
					if(c.getY()>midY){
						subLists[CollisionList.SOUTH_EAST].add(c);
						subLists[CollisionList.SOUTH].add(c);
					}else{
						subLists[CollisionList.CENTER].add(c);
						subLists[CollisionList.EAST].add(c);
						if(c.getY()+c.getBoundingHeight()>midY){
							subLists[CollisionList.SOUTH].add(c);
							subLists[CollisionList.SOUTH_EAST].add(c);
						}
					}
				}else{//only west
					if(c.getY()>midY){
						subLists[CollisionList.SOUTH].add(c);
					}else{
						subLists[CollisionList.CENTER].add(c);
						int test=(int)(c.getY()+c.getBoundingHeight());
						if(test>midY){
							subLists[CollisionList.SOUTH].add(c);
						}
					}
				}
				
			}
		}
	}
	private void collideIfPossible(Collidable a, Collidable b, HashMap<Collision, Double> previousCollisions){
		
		boolean canA,canB;
		canA=a.canCollideWith(b);
		canB=b.canCollideWith(a);
		if((canA||canB)&&a.checkBoundingCollision(b)){
			
			
			Collision collision=new Collision(a,b);
			
			if(!previousCollisions.containsKey(collision)){
				double time;
				
				
				if(canA&&canB){
					if(a.getCollisionPrecedence()>=b.getCollisionPrecedence()){
						time=a.collideTime(b);
					}else{
						time=b.collideTime(a);
					}
				}else if(canA){
					time=a.collideTime(b);
				}else{
					time=b.collideTime(a);
				}
					if(time<=0){
						previousCollisions.put(collision, time);
						collision.setTime(time);
					}
				
			}
		}
		
	}
	
	public void checkCollisions(){
		HashMap<Collision, Double> collisions= new HashMap<Collision, Double>();
		checkCollisions(collisions);
		
		HashSet <Collidable> finished=new HashSet <Collidable>();
		
		Collision cols[]=new Collision[collisions.size()];
		if(!collisions.isEmpty())System.out.println(collisions);
		int i=0;
		for(Collision c: collisions.keySet()){
			cols[i++]=c;
		}
		Arrays.sort(cols);
		for(Collision c: cols){
			
			
			if((!finished.contains(c.a)||!finished.contains(c.b))){
				
				if(c.a instanceof Movable)finished.add(c.a);
				if(c.b instanceof Movable)finished.add(c.b);
				c.doCollision();
			}
		}
		
	}
	
	
	public void checkCollisions(HashMap<Collision, Double> collisions){
		
		if(isBottom){
			int length=movable.size();
			for(int i=0;i<length;i++){
				for(int j=i+1;j<length;j++){
					this.collideIfPossible(movable.get(i),movable.get(j),collisions);
				}
				for(Collidable c: stationary){
					this.collideIfPossible(c, movable.get(i),collisions);
				}
			}
		}else{
			for (CollisionList c: this.subLists){
				c.checkCollisions();
			}
		}
	}


	public Collidable selectAtPoint(int x, int y) {
		if(isBottom){
			Collidable ret=null;
			for(Collidable c: stationary){
				if(c.pointInBoundingBox(x, y))ret=c;
			}
			for(Movable m: movable){
				if(m.pointInBoundingBox(x, y))ret=m;
			}
			
			return ret;
		}else{
			if(x>this.midX){
				if(y>this.midY){
					return subLists[CollisionList.SOUTH_EAST].selectAtPoint(x, y);
				}else{
					return subLists[CollisionList.EAST].selectAtPoint(x, y);
				}
				
			}else{
				if(y>this.midY){
					return subLists[CollisionList.SOUTH].selectAtPoint(x, y);
				}else{
					return subLists[CollisionList.CENTER].selectAtPoint(x, y);
				}
			}
		}
	}
	public ArrayList<Collidable> selectInRect(int x, int y, int width,int height) {
		return new ArrayList<Collidable>();
	}
	
	private class Collision implements Comparable<Collision>{
		
		private double time=1;
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime * result + ((a == null) ? 0 : a.hashCode());
			result = prime * result + ((b == null) ? 0 : b.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if(obj instanceof Collision){
				return ((Collision)obj).a==a&&((Collision)obj).b==b;
			}
			return false;
		}
		private Collidable a,b;
		public Collision(Collidable a, Collidable b){
			this.a=a;
			this.b=b;
		}
		public void setTime(double time){this.time=time;}
		public double getTime(){return time;}
		public void doCollision(){
			a.collide(b);
		}
		private CollisionList getOuterType() {
			return CollisionList.this;
		}
		public String toString(){
			return a+","+b;
		}
		@Override
		public int compareTo(Collision o) {

				double ret=time-o.time;
				if(ret>0)return 1;
				return -1;

		}
		
	}/**/

	
	

}
