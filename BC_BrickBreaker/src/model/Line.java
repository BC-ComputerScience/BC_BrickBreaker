package model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import view.Renderable;
import mathematics.Matrix;
import mathematics.Vector;

/**
 * A line that spheres can bounce off of
 * 
 * @author Anthony Klobas
 * 
 */
public class Line extends Collidable implements Renderable {
	private double x1, y1, x2, y2;
	protected Vector normal, p1, p2;
	private int x, y, width, height;
	private BufferedImage image;
	protected double length;
	private boolean isOneWay;

	public Line(Vector p1, Vector p2,boolean isOneWay){
		this(p1.getElement(0), p1.getElement(1), p2.getElement(0), p2
				.getElement(1), isOneWay);
	}
	public Line(Vector p1, Vector p2) {
		this(p1,p2,false);
	}
	public Line(double x1, double y1, double x2, double y2){
		this(x1,y1,x2,y2,false);
	}
	public Line(double x1, double y1, double x2, double y2, boolean isOneWay) {
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.isOneWay=isOneWay;
		p1 = new Vector(x1, y1);
		p2 = new Vector(x2, y2);
		length = p1.distance(p2);

		x = (int) Math.min(x1, x2);
		y = (int) Math.min(y1, y2);
		width = (int) Math.max(x1, x2) - x;
		height = (int) Math.max(y1, y2) - y;
		image = new BufferedImage(width + 1, height + 1,
				BufferedImage.TYPE_INT_ARGB);
		this.normal = new Vector(y1 - y2, -1 * (x1 - x2)).getUnitVector();
	}

	@Override
	public void advance(double seconds) {
		// TODO add code for updating graphics

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
	public double collideTime(Collidable c) {
		if (c instanceof Sphere) {
			Sphere s= (Sphere)c;
			Matrix toBase = Matrix.createOrthonormal(normal);
			double height = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double traj = toBase.apply(s.getTrajectory()).getElement(0);
			double linetime=kp(-(Math.min(Math.abs((s.getRadius() - height) / traj),Math.abs(( s.getRadius() - height) / traj) )));
			double p1time=-1,p2time=-1;
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
				p1time=-Math.abs(((det<0?det:-det)+h)/s.getTrajectory().getLength());
				if(p1time>0)p1time=-1;
				
			}
			if(Math.abs(p2height) < s.getRadius()){
				double h=(pointBasis.apply(s.getCenter().subtract(this.p2))
						.getElement(1));
				double y=0;
				double k=pointBasis.apply(s.getCenter().subtract(this.p2))
						.getElement(0);
				double r=radius;
				double det=Math.sqrt(r*r-(y-k)*(y-k));
				
				p2time=-Math.abs(((det<0?det:-det)+h)/s.getTrajectory().getLength());
				if(p2time>0)p1time=-1;
			}
			//double time =0;
			//System.out.println(""+linetime+","+p1time+","+p2time);
			
			//linetime<
			
			//double Time= Math.min( (s.getRadius()-s.getCenter().distance(p1))/s.getTrajectory().distance(new Vector(0,0)));
			//System.out.println("time="+Math.max(Math.max((p1time), (p2time)), (linetime)));
			return -Math.max(Math.max((p1time), (p2time)), -(linetime));
			
		}
		return 1;
	}

	public double kp(double d){
		return d<0?d:-655;
	}
	@Override
	public boolean collide(Collidable c, boolean ignorePosition) {
		System.out.println("colliding");
		if (c instanceof Sphere) {
			Sphere s = (Sphere) c;

			Matrix toBase = Matrix.createOrthonormal(normal);
			double height = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double traj = toBase.apply(s.getTrajectory()).getElement(0);
			double x = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(1);
			
			if (Math.abs(height) <= s.getRadius()){
				double linetime=kp(-(Math.min(Math.abs((s.getRadius() - height) / traj),Math.abs(( s.getRadius() - height) / traj) )));
				
				Matrix pointBasis = Matrix.createOrthonormal(s.getTrajectory().getPerpendicular());
				
				double p1height = pointBasis.apply(s.getCenter().subtract(this.p1))
						.getElement(0);
				double p2height = pointBasis.apply(s.getCenter().subtract(this.p2))
						.getElement(0);
				double p1time=-1;//kp((s.getRadius()-s.getCenter().distance(p1))/s.getTrajectory().distance(new Vector(0,0)));
				
				double p2time=-1;//kp((s.getRadius()-s.getCenter().distance(p2))/s.getTrajectory().distance(new Vector(0,0)));
//				
//				System.out.println("------------------");
//				System.out.println(pointBasis.apply(s.getCenter().subtract(this.p1))+"radius:"+s.getRadius());
				double radius=s.getRadius();
				if(Math.abs(p1height) < s.getRadius()){
					double h=(pointBasis.apply(s.getCenter().subtract(this.p1))
							.getElement(1));
					double y=0;
					double k=pointBasis.apply(s.getCenter().subtract(this.p1))
							.getElement(0);
					double r=radius;
					double det=Math.sqrt(r*r-(y-k)*(y-k));
					p1time=-Math.abs(((det<0?det:-det)+h)/s.getTrajectory().getLength());
					if(p1time>0)p1time=-1;
					
				}
<<<<<<< HEAD
				return true;
			} else if (p2.distance(s.getCenter()) < s.getRadius()) {
				// if at end of line, line acts like a point
				//s.bounceOffPoint(p2);
				//return true;
			} else if (p1.distance(s.getCenter()) < s.getRadius()) {
				//s.bounceOffPoint(p1);
				//return true;
=======
				if(Math.abs(p2height) < s.getRadius()){
					double h=(pointBasis.apply(s.getCenter().subtract(this.p2))
							.getElement(1));
					double y=0;
					double k=pointBasis.apply(s.getCenter().subtract(this.p2))
							.getElement(0);
					double r=radius;
					double det=Math.sqrt(r*r-(y-k)*(y-k));
					
					p2time=-Math.abs(((det<0?det:-det)+h)/s.getTrajectory().getLength());
					if(p2time>0)p1time=-1;
				}
//				System.out.println("------------------");
				
				//double p1time=-1;//kp((s.getRadius()-s.getCenter().distance(p1))/s.getTrajectory().distance(new Vector(0,0)));
				
				//double p2time=-1;//kp((s.getRadius()-s.getCenter().distance(p2))/s.getTrajectory().distance(new Vector(0,0)));
				
				//System.out.println();System.out.println();System.out.println();
				//System.out.println("x:"+x+" y:"+height+" radius"+s.getRadius());
				
				//System.out.println();System.out.println();System.out.println();//double time =0;
				//System.out.println(""+linetime+","+p1time+","+p2time);
				
				//linetime<
				
				//double Time= Math.min( (s.getRadius()-s.getCenter().distance(p1))/s.getTrajectory().distance(new Vector(0,0)));
				double time=-Math.max(Math.max((p1time), (p2time)), (linetime));
//				System.out.println("times :"+linetime+","+p1time+","+p2time);
//				System.out.println(time);
				

				System.out.println("------------------------------");
				System.out.println(time+ "times :"+linetime+","+p1time+","+p2time);
				System.out.println("------------------------------");
				if(x <= 0 && -length <= x) {
					time=-linetime;
					s.advance(-time);
					/*
					 * using traj means even if the center of the ball passes the
					 * line it will stop the ball from passing through line if you
					 * use height
					 */
					// TODO see where ball is at time of collision
					//System.out.println(""+linetime+","+p1time+","+p2time);
					//System.out.println("pre:"+s.getTrajectory());
					System.out.println(s.getTrajectory());
					if(time<.1){
						if (traj > 0) {
							s.reflect(normal.negate());
						} else {
							s.reflect(normal);
						}
					}
					System.out.println(s.getTrajectory());
					
					//System.out.println("post:"+s.getTrajectory());
					//System.out.println("------------------------------");
					System.out.println("line!");
				}else if (-p2time==time) {
					s.advance(-time);
					// if at end of line, line acts like a point
					//System.out.println("collision p2 at:"+time);
					System.out.println("point 2");
//					//System.out.println("x:"+x+"y:"+height +"line length"+length);
//					
//					System.out.println(s.getTrajectory().getUnitVector().negate());
//					System.out.println(s.getCenter().subtract(p1).getUnitVector());
					System.out.println(s.getTrajectory().getUnitVector());
					System.out.println("-_-_-_-_-_");
					System.out.println(p2);
					System.out.println(p1);
					System.out.println(s.getCenter());
					System.out.println(s.getCenter().subtract(p2).getUnitVector());
					System.out.println(s.getImageX()+","+s.getImageY());
					System.out.println("-_-_-_-_-_");
					s.reflect(s.getCenter().subtract(p2));
					//s.bounceOffPoint(p2);
				}else if (-p1time==time) {
					s.advance(-time);
					System.out.println("point 1");
					System.out.println(s.getTrajectory().getUnitVector());
					System.out.println("-_-_-_-_-_");
					System.out.println(p2);
					System.out.println(p1);
					System.out.println(s.getCenter());
					System.out.println(s.getCenter().subtract(p1));
					System.out.println("-_-_-_-_-_");
					//System.out.println("collision p1 at:"+time);
					//System.out.println();System.out.println();System.out.println();
					
					
					//System.out.println();System.out.println();System.out.println();
					s.reflect(s.getCenter().subtract(p1));
					//s.bounceOffPoint(p1);
				}else {
					System.out.println("########nohit########");
					System.out.println(time+ "times :"+linetime+","+p1time+","+p2time);
					System.out.println("x:"+x+"length"+length);
					System.out.println("#######################");
					return false;
					//System.out.println("collision not p1:"+p1.distance(s.getCenter())+"    p2:"+p2.distance(s.getCenter()));
				}
				if(true){
					//s.setTrajectory(new Vector(0,0));
					//return true;
					
				}
				
				s.advance(time);
				
				return true;
>>>>>>> refs/heads/CollisionListFix
			}
			System.out.println("did not collide");
		}
		
		return false;

	}

	@Override
	public int getCollisionPrecedence() {
		return 2;
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

		return height;
	}

	@Override
	public int getBoundingWidth() {
		return width;
	}

	@Override
	public Image getImage() {
		Graphics2D g = image.createGraphics();
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		// g.setColor(new Color(255,0,255,0));
		g.setColor(Color.RED);
		// g.fillRect(0, 0, width, height);
		g.setColor(new Color(0x700070));
		//g.setStroke(new BasicStroke(10));
		// System.out.println(x1+","+y1+","+x2+","+y2);
		g.drawLine((int) x1 - x, (int) y1 - y, (int) x2 - x, (int) y2 - y);
		return image;
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
	public String toString(){
		return this.getClass().getName()+p1+p2;
	}
	

}
