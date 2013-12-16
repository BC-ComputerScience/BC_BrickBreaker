package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import view.Renderable;
import mathematics.Matrix;
import mathematics.Vector;

public class Paddle extends Collidable implements Renderable{
	private double x1, y1, x2, y2;
	protected Vector normal, p1, p2;
	private int x, y, width, height;
	private BufferedImage image;
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
		image = new BufferedImage(width + 1, height + 1,
				BufferedImage.TYPE_INT_ARGB);
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
	public boolean collide(Collidable c, boolean ignorePosition) {
		
		if (c instanceof Sphere) {
			Sphere s = (Sphere) c;

			Matrix toBase = Matrix.createOrthonormal(normal);
			
			double height = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			
			double traj = toBase.apply(s.getTrajectory()).getElement(0);
			
			double x = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(1);

			//System.out.println("x:"+x+" length:"+length +"height"+height);
			//System.out.println(Math.abs(height) < s.getRadius());
			if (Math.abs(height) < s.getRadius() && x < 0 && -length < x) {
				/*
				 * using traj means even if the center of the ball passes the
				 * line it will stop the ball from passing through line if you
				 * use height
				 */
				// TODO see where ball is at time of collision
				double time=Math.min(Math.abs((s.getRadius() - height) / traj),Math.abs((-1 * s.getRadius() - height) / traj) );
				if(time<1){
					
					s.advance(-time);
					//Vector n=new Vector(x+(width/2),s.getY()).subtract(s.getCenter());
					
					Vector n =new Vector((this.x+(length/2.)-s.getCenter().getElement(0))/length,0).negate().add(normal);
					
					//System.out.println(this.x+(length/2.));
					//System.out.println(s.getX());
					//System.out.println(new Vector(,s.getY()));
					//System.out.println(n);
					//System.out.println(normal);
					if (traj < 0) {
						s.reflect(n.negate());
					} else {
						if(!isOneWay)s.reflect(normal);
					}
					s.advance(time);
				}
				return true;
			} else if (p2.distance(s.getCenter()) < s.getRadius()) {
				// if at end of line, line acts like a point
				//s.bounceOffPoint(p2);
				//return true;
			} else if (p1.distance(s.getCenter()) < s.getRadius()) {
				//s.bounceOffPoint(p1);
				//return true;
			}
			

		}
		return false;

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
	public Image getImage() {
		Graphics2D g = image.createGraphics();
		// g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		// g.setColor(new Color(255,0,255,0));
		g.setColor(Color.RED);
		// g.fillRect(0, 0, width, height);
		g.setColor(Color.RED);
		 //System.out.println(x1+","+y1+","+x2+","+y2);
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
	public double collideTime(Collidable c) {
		if (c instanceof Sphere) {
			Sphere s = (Sphere) c;

			Matrix toBase = Matrix.createOrthonormal(normal);
			double height = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double traj = toBase.apply(s.getTrajectory()).getElement(0);
			double x = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(1);
			return -Math.min(Math.abs((s.getRadius() - height) / traj),Math.abs((-1 * s.getRadius() - height) / traj) );
		}
		return 1;
	}
}
