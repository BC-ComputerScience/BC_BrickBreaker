package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import resources.SpriteSet;
import view.Renderable;
import view.Sprite;
import mathematics.Matrix;
import mathematics.Vector;

public class Paddle extends Collidable implements Renderable{
	private double x1, y1, x2, y2;
	protected Vector normal, p1, p2;
	private int x, y, width, height;
	private SpriteSet spriteSet=new SpriteSet();
	protected double length;
	private boolean isOneWay=true;

	public Paddle(int x, int y, int width){
		synchronized(this){
		this.x1=x+width;
		this.y1=y;
		this.x2=x;
		this.y2=y;
		this.isOneWay=true;
		p1 = new Vector(x1, y1);
		p2 = new Vector(x2, y2);
		length = width;

		this.x = (int) Math.min(x1, x2);
		this.y = (int) Math.min(y1, y2);
		this.width = (int) Math.max(x1, x2) - x+1;
		//System.out.println(width);
		height = (int) Math.max(y1, y2) - y+1;
		this.normal = new Vector(y1 - y2, -1 * (x1 - x2)).getUnitVector();
		}
	}
	public void move(double delta){
		synchronized(this){
			//System.out.println("x"+x+"x1"+x1+"x2"+x2+"width"+width);
		this.x+=delta;
		this.x1+=delta;
		this.y1=y;
		this.x2+=delta;
		this.y2=y;
		p1 = new Vector(x1, y1);
		p2 = new Vector(x2, y2);
		}
	}

	@Override
	public void advance(double seconds) {
		// TODO add code for updating graphics

	}
	@Override
	public int getCollisionPrecedence() {
		return 2;
	}

	@Override
	public boolean canCollideWith(Collidable c) {
		// lines only know how to collide with spheres
		return (c instanceof Sphere);
	}

	@Override
	public boolean collide(Collidable c) {
		return collide(c, false);

	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public int getBoundingHeight() {

		return height+1;
	}

	@Override
	public int getBoundingWidth() {
		return width+1;
	}

	@Override
	public Sprite getImage() {
		return spriteSet.currentSprite();
	}

	@Override
	public int getImageX() {
		return (int) this.x;
	}

	@Override
	public int getImageY() {
		return (int) this.y;
	}
	public boolean stillExists() {
		return true;
	}
	public double collideTime(Collidable c) {
		if (c instanceof Sphere) {
			Sphere s= (Sphere)c;
			Matrix toBase = Matrix.createOrthonormal(normal);
			double height = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double traj = toBase.apply(s.getTrajectory()).getElement(0);
			
			
			//double linetime=(Math.min(Math.abs((s.getRadius() - height) / traj),Math.abs(( -height-s.getRadius()) / traj) ));
			
			double p1time=Integer.MAX_VALUE,p2time=Integer.MAX_VALUE;
			Matrix pointBasis = Matrix.createOrthonormal(s.getTrajectory().getPerpendicular());
			
			double p1height = pointBasis.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double p2height = pointBasis.apply(s.getCenter().subtract(this.p2))
					.getElement(0);
			double radius=s.getRadius();
			
			if(Math.abs(p1height) < s.getRadius()){
				double h=(pointBasis.apply(s.getCenter().subtract(this.p1))
						.getElement(1));
				double y=0;
				double k=pointBasis.apply(s.getCenter().subtract(this.p1))
						.getElement(0);
				double r=radius;
				double det=Math.sqrt(r*r-(y-k)*(y-k));
				p1time=((det<0?det:-det)+h)/s.getTrajectory().getLength();
				
			}
			if(Math.abs(p2height) < s.getRadius()){
				double h=(pointBasis.apply(s.getCenter().subtract(this.p2))
						.getElement(1));
				double y=0;
				double k=pointBasis.apply(s.getCenter().subtract(this.p2))
						.getElement(0);
				double r=radius;
				double det=Math.sqrt(r*r-(y-k)*(y-k));
				
				p2time=((det<0?det:-det)+h)/s.getTrajectory().getLength();
			}
			
			//double time =0;
			//System.out.println(""+linetime+","+p1time+","+p2time);
			
			//linetime<
			
			//double Time= Math.min( (s.getRadius()-s.getCenter().distance(p1))/s.getTrajectory().distance(new Vector(0,0)));
			//System.out.println("time="+Math.max(Math.max((p1time), (p2time)), (linetime)));
			double linetime=(Math.min(((s.getRadius() - height) / traj),(( -height-s.getRadius()) / traj) ));
			
			
			if(linetime<Integer.MAX_VALUE&&linetime>Integer.MIN_VALUE){
				s.advance(linetime);
				double x = toBase.apply(s.getCenter().subtract(this.p1))
						.getElement(1);
				s.advance(-linetime);
				if(!(x <= 0 && -length <= x)){
					linetime=Integer.MAX_VALUE;
				}
			}
			
			
			
			double time=Math.min(Math.min((p1time), (p2time)), (linetime));
			
			//System.out.println("selected:"+time+"\tp1: "+p1time+"\tp2: "+p2time+"\tline: "+linetime);
			//System.out.println("height"+height+",\t"+p1+""+p2);

			return time;
			
		}
		return Integer.MAX_VALUE;
	}

	public double kp(double d){
		return d<0?d:-655;
	}
	@Override
	public boolean collide(Collidable c, boolean ignorePosition) {
		if (c instanceof Sphere) {
			Sphere s = (Sphere) c;

			Matrix toBase = Matrix.createOrthonormal(normal);
			double height = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double traj = toBase.apply(s.getTrajectory()).getElement(0);
			double x = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(1);
			
			if (Math.abs(height) <= s.getRadius()||ignorePosition){
				
				
				Matrix pointBasis = Matrix.createOrthonormal(s.getTrajectory().getPerpendicular());
				
				double p1height = pointBasis.apply(s.getCenter().subtract(this.p1))
						.getElement(0);
				double p2height = pointBasis.apply(s.getCenter().subtract(this.p2))
						.getElement(0);
				double p1time=Integer.MAX_VALUE;
				double p2time=Integer.MAX_VALUE;
				
				double radius=s.getRadius();
				if(Math.abs(p1height) < s.getRadius()){
					double h=(pointBasis.apply(s.getCenter().subtract(this.p1))
							.getElement(1));
					double y=0;
					double k=pointBasis.apply(s.getCenter().subtract(this.p1))
							.getElement(0);
					double r=radius;
					double det=Math.sqrt(r*r-(y-k)*(y-k));
					p1time=((det<0?det:-det)+h)/s.getTrajectory().getLength();
					
				}
				if(Math.abs(p2height) < s.getRadius()){
					double h=(pointBasis.apply(s.getCenter().subtract(this.p2))
							.getElement(1));
					double y=0;
					double k=pointBasis.apply(s.getCenter().subtract(this.p2))
							.getElement(0);
					double r=radius;
					double det=Math.sqrt(r*r-(y-k)*(y-k));
					
					p2time=((det<0?det:-det)+h)/s.getTrajectory().getLength();
				}
				
				
				
				
				double linetime=(Math.min(((s.getRadius() - height) / traj),(( -height-s.getRadius()) / traj) ));
				
				
				
				double time=Math.min((p1time), (p2time));
//				System.out.println("selected:"+time+"\tp1: "+p1time+"\tp2: "+p2time+"\tline: "+linetime);
//				System.out.println("height"+height+",\t"+p1+""+p2);
//				
//				System.out.println("#########################");
//				System.out.println("p1: "+p1time+"\tp2: "+p2time+"\tline: "+linetime);
//				System.out.println("#########################");
//				
				//&&linetime>=p1time&&linetime>=p2time
				
				if(linetime<Integer.MAX_VALUE&&linetime>Integer.MIN_VALUE){
					s.advance(linetime);
					x = toBase.apply(s.getCenter().subtract(this.p1))
							.getElement(1);
					s.advance(-linetime);
					if(!(x <= 0 && -length <= x)){
						linetime=Integer.MAX_VALUE;
					}else{
						time=linetime;
						this.lineBounce(s, traj, time);
						return true;
					}
				}
				
				
				/*if((x <= 0 && -length <= x)&&linetime<=time) {
					time=linetime;
					this.lineBounce(s, traj, time);
					return true;
					
				} */
				
				if(time<=0||ignorePosition){
					
					if (p2time==time&&time!=Integer.MAX_VALUE) {
//						System.out.println("p2");
						this.pointBounce(s, p2, time);
						return true;
					}else if (p1time==time&&time!=Integer.MAX_VALUE) {
						this.pointBounce(s, p1, time);
						return true;
					}else {
						System.out.println("!!!!!!!!!!!!nohit!!!!!!!!!!!");
						System.out.println(time+ "times :"+linetime+","+p1time+","+p2time);
						System.out.println("x:"+x+"length"+length);
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						//s.setTrajectory(new Vector(0,0));
						return false;
						//System.out.println("collision not p1:"+p1.distance(s.getCenter())+"    p2:"+p2.distance(s.getCenter()));
					}
					//s.setTrajectory(new Vector(0,0));
					
				}

			}
		}
		
		return false;
	}
	
	private double getPointCollisionTime(){
		return 0;
	}
	
	
	private void lineBounce(Sphere s, double traj, double time){
		//System.out.println("Line Collision");
		s.advance(time);
		Vector n =new Vector((this.x+(length/2.)-s.getCenter().getElement(0))/length,0).negate().add(normal);
		
		if (traj > 0) {
			if(!this.isOneWay)s.reflect(n.negate());
		} else {
			s.reflect(n);
		}
		s.advance(-time);
	}
	private void pointBounce(Sphere s,Vector p,double time){
		//System.out.println("Point collision");
		s.advance(time);
		//System.out.println(s.getCenter().subtract(p));
		s.reflect(s.getCenter().subtract(p));
		s.advance(-time);
		
	}
	@Override
	public int getImageWidth() {
		// TODO Auto-generated method stub
		return width;
	}
	@Override
	public int getImageHeight() {
		// TODO Auto-generated method stub
		return height;
	}
	public void addSpriteSet(SpriteSet set) {
		this.spriteSet=set;
		
	}
}
