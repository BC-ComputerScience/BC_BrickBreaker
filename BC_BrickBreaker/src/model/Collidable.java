package model;

import mathematics.Vector;


public abstract class Collidable implements GameObject{
	
	public abstract int getBoundingWidth(); 
	public abstract int getBoundingHeight();
	public abstract double getX();
	public abstract double getY();
	
	//getpos
	//setpos(vector)
	//setX
	//setY
	//translate(vector)
	
	public abstract boolean canCollideWith(Collidable C);
	public abstract boolean collide(Collidable C);
	public abstract boolean collide(Collidable C, boolean ignorePosition);
	public abstract double collideTime(Collidable C);
	
	public abstract int getCollisionPrecedence();
	public abstract boolean stillExists();
	public abstract void advance(double seconds);
	
	
	public boolean checkBoundingCollision(Collidable c){
		if(this.getX()<c.getX()){//this is on left
			if(this.getX()+this.getBoundingWidth()<c.getX())return false;
			if(this.getY()<c.getY()){//this is top left
				if(this.getY()+this.getBoundingHeight()<c.getY())return false;
			}else{//this is bottom left
				if(c.getY()+c.getBoundingHeight()<this.getY())return false;
			}
		}else{//this is on right
			if(c.getX()+c.getBoundingWidth()<this.getX())return false;
			if(this.getY()<c.getY()){//this is top right
				if(this.getY()+this.getBoundingHeight()<c.getY())return false;
			}else{//this is bottom right
				if(c.getY()+c.getBoundingHeight()<this.getY())return false;
			}
			
		}
		return true;
	}
	public boolean pointInBoundingBox(int x, int y){
		return (x>=this.getX()&&y>=this.getY()&&x<=this.getX()+this.getBoundingWidth()&&y<=this.getY()+this.getBoundingHeight());
	}
	
	
	
	
	public String toString(){
		return this.getClass().getName()+" at: ("+((int)getX())+","+((int)getY())+")";
	}

}
