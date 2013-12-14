package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import view.Renderable;

/**
 * A box that can hold a ball inside of it
 * 
 * @author Anthony Klobas
 *
 */

public class Box extends Collidable implements Renderable{
	private int width,height;
	private Line[] lines=new Line[4];
	private BufferedImage image;
	int x, y;
	/**
	 * creates a box
	 * @param x top left x
	 * @param y top left right
	 * @param width width of box
	 * @param height height of box
	 */
	public Box(double x, double y, int width,int height) {
		this.x=(int)x;
		this.y=(int)y;
		this.width=width;
		this.height=height;
		lines[0]=new Line(x,y,x+width,y);
		lines[1]=new Line(x+width,y,x+width,y+height);
		lines[2]=new Line(x+width,y+height,x,y+height);
		lines[3]=new Line(x,y+height,x,y);
		image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		
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
		Graphics2D g= image.createGraphics();
		g.setColor(Color.RED);
		g.setColor(Color.RED);
		g.drawRect(0, 0, width, height);
		return image;
	}

	@Override
	public int getImageX() {
		return x;
	}

	@Override
	public int getImageY() {
		return y;
	}

	@Override
	public boolean canCollideWith(Collidable C) {
		return C instanceof Sphere;
	}

	@Override
	public boolean collide(Collidable C) {
		return collide(C, false);
		
	}

	@Override
	public boolean collide(Collidable C, boolean ignorePosition) {
		for(Line l: lines){
			if(l.collide(C))return true;
		}
		return false;
		
	}

	@Override
	public int getCollisionPrecedence() {
		return 2;
	}



	@Override
	public void advance(double seconds) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return x;
	}



	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return y;
	}
	public boolean stillExists() {
		return true;
	}

}
