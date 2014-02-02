package model.gameObjects;

import java.util.ArrayList;

import mathematics.Vector;
import mathematics.Matrix;

public abstract class Movable extends Collidable{
	//C is the maximum velocity
	public static final double C=2500;
	
	
	private double mass, inverseMass;
	private double charge;
	private Vector trajectory;
	private double COR=1;
	Vector force=new Vector(2);
	
	public Movable(Vector pos,int width, int height,double mass){
		this(pos,width,height,new Vector(pos.getDimension()),mass);
	}
	public Movable(Vector pos,int width, int height, Vector trajectory,double mass){
		super(pos,width,height);
		this.trajectory=trajectory;
		this.mass=mass;
		this.inverseMass=1/mass;
		this.charge=0;
	}
	
	public void addForce(Vector v){
		force=force.add(v);
	}
	public Vector getTotalForce(){
		return force;
	}
	
	public void advance(double seconds){
		trajectory=trajectory.add(force.scale(seconds*inverseMass));// (kg*m/s^2)*(s/kg)=(m/s)
		force=new Vector(2);
		this.translate(trajectory.scale(seconds));
	}
	
	
	public Vector getTrajectory() {return trajectory;}
	
	public void setTrajectory(Vector trajectory) {
		
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
	 * @param normal the normal vector pointing off of 1 and onto 2
	 */
	public static void centerOfMassBounce(Movable m1,Movable m2, Vector normal){
		if(normal.getLength()==0){
			return;//if its length is 0, it has no direction and bouncing is undefined
		}
		
		
		double averageCOR=(m1.COR+m2.COR)/2;
		double totalMass=m1.mass+m2.mass;
		Vector rel1=m1.trajectory.getProjection(normal);
		Vector rel2=m2.trajectory.getProjection(normal);
		Vector Vcm=rel1.scale(m1.mass).add(rel2.scale(m2.mass)).scale(1/totalMass);
		rel1=rel1.subtract(Vcm);
		rel2=rel2.subtract(Vcm);
		m1.trajectory=m1.trajectory.subtract(rel1.scale(1+averageCOR));//.subtract(Vcm.subtract(rel1));
		m2.trajectory=m2.trajectory.subtract(rel2.scale(1+averageCOR));;//.subtract(Vcm.subtract(rel2));
		
		
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

	
	public double getVelX(){return trajectory.getElement(0);}
	public double getVelY(){return trajectory.getElement(1);}
	
	//public void setImmovable(boolean isImmovable){immovable=isImmovable;}
	//public boolean isImovable(){return immovable;}
	

	
}
