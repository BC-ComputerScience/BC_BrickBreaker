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
	public boolean collide(Collidable c, boolean ignorePosition) {
		if (c instanceof Sphere) {
			Sphere s = (Sphere) c;

			Matrix toBase = Matrix.createOrthonormal(normal);
			double height = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double traj = toBase.apply(s.getTrajectory()).getElement(0);
			double x = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(1);

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
					if (traj < 0) {
						s.reflect(normal.negate());
					} else {
						if(!isOneWay)s.reflect(normal);
					}
					s.advance(time);
				}
				return true;
			} else if (p2.distance(s.getCenter()) < s.getRadius()) {
				// if at end of line, line acts like a point
				s.bounceOffPoint(p2);
				return true;
			} else if (p1.distance(s.getCenter()) < s.getRadius()) {
				s.bounceOffPoint(p1);
				return true;
			}
			

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

}
