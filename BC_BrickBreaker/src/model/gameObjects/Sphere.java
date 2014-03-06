package model.gameObjects;

import java.util.Random;

import resources.SpriteSet;
import resources.SpriteSheet;
import view.Renderable;
import view.Sprite;
import mathematics.Matrix;
import mathematics.Vector;

public class Sphere extends Movable implements Renderable{
	
	private double radius;
	private SpriteSet spriteSet=new SpriteSet();
	
	public Sphere(Vector pos, Vector traj,double mass, double radius){
		super(pos,(int)(radius*2),(int)(radius*2),traj,mass);
		this.radius=radius;
		this.setPos(pos.subtract(new Vector(radius,radius)));
	}
	
	public Sphere(Vector pos, Vector traj,double mass){
		this(pos,traj,mass,Math.sqrt(mass));
	}
	public double getRadius(){
		return this.radius;
	}
	
	
	/**************************Game Object *******************************/
	@Override
	public GameObject cloneAt(Vector v) {
		Sphere toret=new Sphere(v,this.getTrajectory(),this.getMass(),radius);
		if(spriteSet!=null){
			toret.addSpriteSet(this.spriteSet.clone());
		}
		return toret;
	}
	@Override
	public void advance(double time){
		super.advance(time);
		this.spriteSet.advance(time*100);
	}
	@Override
	public boolean stillExists() {
		return true;
	}
	
	
	/**************************Collidable section *******************************/
	@Override
	public boolean canCollideWith(Collidable C) {
		//spheres can only know how to collide with other spheres
		return C instanceof Sphere;
	}
	@Override
	public int getCollisionPrecedence() {
		return 1;
	}
	public boolean collide(Collidable C) {
		if(C instanceof Sphere){//never trust, always check, conseal, dont feel
			Sphere other=(Sphere)C;
			
			
			
			if (getCenterOfMass().distance(other.getCenterOfMass())<this.radius+other.radius){
				bounce (other);
				return true;
				
			}
			
			
		}
		return false;
	}
	@Override
	public double collideTime(Collidable C) {
		if(C instanceof Sphere){
			
			Sphere other=(Sphere)C;
			Vector traj=other.getTrajectory().subtract(this.getTrajectory());
			
			Vector pos=this.getCenterOfMass().subtract(other.getCenterOfMass());
			double h=pos.getProjection(traj.getPerpendicular()).getLength();
			double r=this.radius+other.radius;
			double deltaX=Math.sqrt(r*r-h*h);
			
			double time=(pos.getProjection(traj).getLength()-deltaX)/traj.getLength();
			//pos=pos.scale(((pos.getLength()-this.radius)-other.radius)/pos.getLength());
			
			
			
			
			//System.out.println("t"+traj);
			//System.out.println("p"+pos);
			//System.out.println("min diff"+pos.getProjection(traj.getPerpendicular()).getLength());
			//double time=pos.getProjection(traj).getLength()/traj.getLength();
			//System.out.println("time"+time);
			//System.out.println();
			if(h<=r){
				
				return time;
			}
			
		}
		return Double.MAX_VALUE;
	}
	
	
	/**************************Movable section *******************************/
	
	@Override
	public Vector getCenterOfMass(){
		return getPos().add(new Vector(this.radius,this.radius));
	}
	@Override
	public void setMass(double m){
		if(m>10000)return;
		super.setMass(m);
	}
	@Override
	public double getArea() {
		return Math.PI*radius*radius;
	}
	@Override
	public Vector getMomentOfInteria() {
		//TODO fix
		return null;
	}
	

	
	
	
	
	

	

	
	
	private void bounce(Sphere other){
		//depth of penetration / difference in trajectories(rate they converge/diverge)
		//double time=(this.radius+other.radius-getCenterOfMass().distance(other.getCenterOfMass()))/this.getTrajectory().distance(other.getTrajectory());		
		double time=this.collideTime(other);
		if(time>3600)return;//if you need to backup more than an hour, I have some bad news for you

		//move time to just before collision
		this.advance(time);
		other.advance(time);
		
		Vector normal=this.getCenterOfMass().subtract(other.getCenterOfMass());
		Movable.bounce(this, other, normal,null);
		
		//move time to its previous location
		this.advance(-time);
		other.advance(-time);
		
	}

	
	
	
	
	
	
	
	
	
	

	
	
	/**************************Renderable section *******************************/
	@Override
	public Sprite getImage() {
		return spriteSet.currentSprite();
	}
	@Override
	public int getImageX() {
		return (int)this.getX();
	}
	@Override
	public int getImageY() {
		return (int)this.getY();
	}	
	@Override
	public int getImageWidth() {
		return (int)(radius*2);
	}
	@Override
	public int getImageHeight() {
		return (int)(radius*2);
	}
	@Override
	public void addSpriteSet(SpriteSet set) {
		this.spriteSet=set;
		
	}

}
