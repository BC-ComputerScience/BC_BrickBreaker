package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
	private ArrayList<Collidable> list;
	
	
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
		list=new ArrayList<Collidable>();
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
		list=new ArrayList<Collidable>();
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
		for(Collidable c: list){
			add(c);
		}
		list=null;
	}
	
	
	public void add(Collidable c){
		if(isBottom){
			list.add(c);
			if(list.size()>this.maxContained) toSublists();
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
				/**/
				
			}
		}
	}
	public void checkCollisions(){
		if(isBottom){
			int length=list.size();
			Collidable a,b;
			boolean canA,canB;
			for(int i=0;i<length;i++){
				for(int j=i+1;j<length;j++){
					
					a=list.get(i);
					b=list.get(j);
					canA=a.canCollideWith(b);
					canB=b.canCollideWith(a);
					if((canA||canB)&&a.checkBoundingCollision(b)){
						if(canA&&canB){
							if(a.getCollisionPrecedence()>=b.getCollisionPrecedence()){
								a.collide(b);
							}else{
								b.collide(a);
							}
						}else if(canA){
							a.collide(b);
						}else if(canB){
							b.collide(a);
						}
					}
					
				}
			}
		}else{
			for (CollisionList c: this.subLists){
				c.checkCollisions();
			}
		}
	}

	public String toString(){
		if(isBottom){
			String ret=list.toString();
			ret=ret.replace('<', '[');
			ret=ret.replace('>', ']');
			return list.toString();
		}else{
			String ret=
				"<list"+this.hashCode()+">";
			for(CollisionList c:subLists){
				ret+=c;
			}
			return ret+"</list"+this.hashCode()+">";
		}
	}
	public Collidable selectAtPoint(int x, int y) {
		if(isBottom){
			Collidable ret=null;
			for(Collidable c: list){
				if(c.pointInBoundingBox(x, y))ret=c;
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
	

	
	

}
