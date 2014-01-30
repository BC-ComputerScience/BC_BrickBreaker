package model;

import java.util.ArrayList;

import mathematics.Vector;
import mathematics.Matrix;

public abstract class Movable extends Collidable{
	//C is the maximum velocity
	public static final double C=2500;
	
	
	private double mass, charge;
	private Vector pos,trajectory;
	private double COR=1;
	public boolean immovable=false;
	Vector force=new Vector(2);
	
	public Movable(double x, double y, Vector trajectory,double mass){
		this.pos=new Vector(x,y);
		this.trajectory=trajectory;
		this.mass=mass;
		this.charge=0;
	}
	
	public void addForce(Vector v){
		force=force.add(v);
	}
	public Vector getTotalForce(){
		return force;
	}
	
	public void advance(double seconds){
		trajectory=trajectory.add(force.scale(seconds/mass));
		force=new Vector(2);
		this.pos=this.pos.add(trajectory.scale(seconds));
	}
	
	
	public Vector getTrajectory() {return trajectory;}
	
	public void setTrajectory(Vector trajectory) {
		if(immovable)return;
		if(trajectory.getLength()>C){
			this.trajectory=trajectory.scale(C/trajectory.getLength());
		}else{
			this.trajectory = trajectory;
		}
		
	}
	/**
	 * This should be used when colliding with static objects
	 * @param vector the normal to the surface which is being reflected off of
	 */
	public void reflect(Vector vector) {
		Matrix toBase= Matrix.createOrthonormal(vector);
		Matrix fromBase=toBase.invert();
		
		Vector temp =toBase.apply(trajectory);
		temp= new Vector(Math.abs(temp.getElement(0))*COR,temp.getElement(1));
		this.setTrajectory(fromBase.apply(temp));
	}
	/**
	 * 
	 * @param m1
	 * @param m2
	 * @param normal MUST be the normal vector pointing off of 1 and onto 2
	 */
	public static void centerOfMassBounce(Movable m1,Movable m2, Vector normal){
		if(normal.getLength()==0){
			return;//if its length is 0, it has no direction and bouncing is undefined
		}
		Matrix toBase= Matrix.createOrthonormal(normal);
		Vector trajectory1=toBase.apply(m1.trajectory);
		Vector trajectory2=toBase.apply(m2.trajectory);
		double x1=trajectory1.getElement(0);
		double x2=trajectory2.getElement(0);
		
		if(m1.immovable||m2.immovable){
			if(!m1.immovable)trajectory1=new Vector(Math.abs(x1),trajectory1.getElement(1));
			if(!m2.immovable)trajectory2=new Vector(-1*Math.abs(x2),trajectory2.getElement(1));
		}else{
			double cm=(x1*m1.getMass() + x2*m2.getMass())/(m1.getMass()+m2.getMass());
			x1-=cm;
			x2-=cm;
			x1=Math.abs(x1)*(m1.COR+m2.COR)/2;
			x2=-Math.abs(x2)*(m1.COR+m2.COR)/2;
			x1+=cm;
			x2+=cm;
			trajectory1=new Vector(x1,trajectory1.getElement(1));
			trajectory2=new Vector(x2,trajectory2.getElement(1));
		}
		
		Matrix fromBase=toBase.invert();
		m1.trajectory=fromBase.apply(trajectory1);
		m2.trajectory=fromBase.apply(trajectory2);
		
		
	}
	public void bounceX(Movable m, Vector Normal){
		{
		
		double x1=this.trajectory.getElement(0);
		double x2=m.trajectory.getElement(0);
		
		}
		
	}
	
	public void reflect(Vector vector, double time) {
		advance(-time);
		reflect(vector);
		advance(time);
		
	}
	
	
	
	
	public Vector getMomentum(){return this.trajectory.scale(this.mass);}
	public void setCOR(double cor) {this.COR=cor;}
	public double getMass() {return mass;}
	public void setMass(double mass) {this.mass = mass;}
	public double getCharge() {return charge;}
	public void setCharge(double charge) {this.charge = charge;}
	public Vector getPos() {return pos;}
	public void setPos(Vector pos) {this.pos = pos;}
	public double getX() {return pos.getElement(0);}
	public void setX(double x) {pos=new Vector(x,pos.getElement(1));}
	public double getY() {return pos.getElement(1);}
	public void setY(double y) {pos=new Vector(pos.getElement(0),y);}
	public double getVelX(){return trajectory.getElement(0);}
	public double getVelY(){return trajectory.getElement(1);}
	public void setImmovable(boolean isImmovable){immovable=isImmovable;}
	public boolean isImovable(){return immovable;}
	

	@Override
	public void translate(int deltaX, int deltaY) {
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void transLate(Vector v) {
		translate((int)v.getElement(0),(int)v.getElement(1));
		// TODO Auto-generated method stub
		
	}
	
}
