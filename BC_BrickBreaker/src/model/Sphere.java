package model;

import java.util.Random;

import resources.SpriteHolder;
import resources.SpriteSet;
import resources.SpriteSheet;
import view.Renderable;
import view.Sprite;
import mathematics.Matrix;
import mathematics.Vector;

public class Sphere extends Movable implements Renderable{
	private double radius;
	private int width,height;
	public static int number=0;
	private int thisnum;
	int dimention;
	int color;
	private SpriteSet spriteSet=new SpriteSet();
	
	
	
	public Sphere(double x, double y, Vector trajectory,double mass){
		super(x,y,trajectory,mass);
		System.out.println("new Sphere("+x+","+y+", new Vector("+trajectory.coordinateList()+"),"+mass+");");
		color=(new Random()).nextInt();
		thisnum=number++;
		this.radius=Math.sqrt(mass);
		dimention=(int)(radius*2);
		this.setPos(new Vector(x-radius,y-radius));
		width=dimention;
		height=dimention;
		
	}
	public Sprite getImage() {
		return spriteSet.currentSprite();
	}
	@Override
	public int getBoundingHeight() {
		return dimention;
	}
	@Override
	public int getBoundingWidth() {
		// TODO Auto-generated method stub
		return dimention;
	}
	public double getRadius(){
		return radius;
	}
	public Vector getCenter(){
		return getPos().add(new Vector(this.radius,this.radius));
	}
	public void setMass(double m){
		if(m>10000)return;
		super.setMass(m);
		radius=Math.sqrt(m);
		dimention=(int)radius*2;
		
		
	}
	@Override
	public int getImageX() {
		// TODO Auto-generated method stub
		return (int)this.getX();
	}
	@Override
	public int getImageY() {
		// TODO Auto-generated method stub
		return (int)this.getY();
	}
	/*public void bounce(Movable m) {
		if(m instanceof Sphere){

			Sphere sphere=(Sphere)m;
			//Vector normal =this.getPos().add(new Vector(radius,radius)).subtract(sphere.getPos().add(new Vector(sphere.radius,sphere.radius)));
			Vector normal =this.getPos().add(new Vector(radius,radius)).subtract(sphere.getPos().add(new Vector(sphere.radius,sphere.radius)));
			
			double depth=normal.getLength();
			double neededLength=this.radius+sphere.radius;
			
			//a b c make a triangle, when c==neededLength, the balls have just collided
			double c=sphere.getTrajectory().distance(this.getTrajectory());
			double a=sphere.getTrajectory().getLength();
			double b=this.getTrajectory().getLength();
			//
			double time=(neededLength-depth)/c;
			if(time>Double.MAX_VALUE)return;
			
			sphere.advance(-time);
			this.advance(-time);
			normal=this.getPos().add(new Vector(radius,radius)).subtract(sphere.getPos().add(new Vector(sphere.radius,sphere.radius)));
			
			if(normal.getLength()==0){
				return;
			}
			Matrix toBase= Matrix.createOrthonormal(normal);
			Matrix fromBase=toBase.invert();
			setTrajectory(toBase.apply(getTrajectory()));
			m.setTrajectory(toBase.apply(m.getTrajectory()));
			this.bounceX(m);
			this.setTrajectory(fromBase.apply(getTrajectory()));
			m.setTrajectory(fromBase.apply(m.getTrajectory()));
			
			sphere.advance(time);
			this.advance(time);
			
			
		}
	}/**/
	@Override
	public boolean canCollideWith(Collidable C) {
		//spheres can only know how to collide with other spheres
		return C instanceof Sphere;
	}
	@Override
	public boolean collide(Collidable C) {
		return collide(C, false);
	}
	@Override
	public boolean collide(Collidable C, boolean ignorePosition) {
		if(C instanceof Sphere){//never trust, always check
			Sphere other=(Sphere)C;
			if (ignorePosition || getCenter().distance(other.getCenter())<this.radius+other.radius){
				bounce (other);
				return true;
				
			}
		}
		return false;
	}
	
	private void bounce(Sphere other){
		//depth of penetration / difference in trajectories(rate they converge/diverge)
		double time=(this.radius+other.radius-getCenter().distance(other.getCenter()))/this.getTrajectory().distance(other.getTrajectory());		
		if(time>3600)return;//if you need to backup more than an hour, I have some bad news for you

		//move time to just before collision
		this.advance(-time);
		other.advance(-time);
		
		Vector normal=this.getCenter().subtract(other.getCenter());
		Movable.centerOfMassBounce(this, other, normal);
		
		//move time to its previous location
		this.advance(time);
		other.advance(time);
		
	}
	public void advance(double time){
		super.advance(time);
		//this.spriteSet.advance(2*time*this.getTrajectory().getLength());
		this.spriteSet.advance(time*100);
		// 50 200
	}
	
	
	
	
	@Override
	public int getCollisionPrecedence() {
		return 1;
	}
	public void bounceOffPoint(Vector p2) {
		//double time=(this.radius-getCenter().distance(p2))/this.getTrajectory().distance(new Vector(0,0));		
		//if(time>3600)return;//if you need to backup more than an hour, I have some bad news for you

		//move time to just before collision
		//this.advance(-time);
		
		Vector normal=this.getCenter().subtract(p2);
		
		this.reflect(normal.negate());
		
		//move time to its previous location
		//this.advance(time);
		
	}
	@Override
	public boolean stillExists() {
		return true;
	}
	@Override
	public double collideTime(Collidable C) {
		// TODO Auto-generated method stub
		Sphere other=(Sphere)C;
		return -(this.radius+other.radius-getCenter().distance(other.getCenter()))/this.getTrajectory().distance(other.getTrajectory());
	}
	@Override
	public int getImageWidth() {
		// TODO Auto-generated method stub
		return this.dimention;
	}
	@Override
	public int getImageHeight() {
		// TODO Auto-generated method stub
		return this.dimention;
	}
	public void addSpriteSet(SpriteSet set) {
		this.spriteSet=set;
		
	}
	

}
