package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import controller.SpriteHolder;
import controller.SpriteSheet;
import view.Renderable;

public class Brick extends Collidable implements Renderable{
	private int width,height;
	private Line[] lines=new Line[4];
	private BufferedImage image;
	int hitPoints;
	int x, y;
	SpriteSheet s;
	/**
	 * creates a box
	 * @param x top left x
	 * @param y top left right
	 * @param width width of box
	 * @param height height of box
	 */
	public Brick(double x, double y, int width,int height) {
		this(x,y,width,height,1);
		
	}

	public Brick(double x, double y, int width, int height, int health, SpriteSheet sheet) {
		this(x,y,width,height,health);
		s=sheet;
	}


	public Brick(double x, double y, int width, int height, int health) {
		//System.out.println("x:"+x+"y:"+y+"width:"+width+"height:"+height+"health:"+health);
		this.x=(int)x;
		this.y=(int)y;
		this.width=width;
		this.height=height;
		lines[0]=new Line(x,y,x,y+height,true);
		lines[1]=new Line(x,y+height,x+width,y+height,true);
		lines[2]=new Line(x+width,y+height,x+width,y,true);
		lines[3]=new Line(x+width,y,x,y,true);
		image = new BufferedImage(width+1,height+1,BufferedImage.TYPE_INT_ARGB);
		this.hitPoints=health;
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
		
		if(s!=null){
			return s.currentSprite();
		}
		Graphics2D g= image.createGraphics();
		switch(this.hitPoints){
		default://g.setColor(Color.WHITE);break;
		case 5:g.setColor(Color.DARK_GRAY);break;
		case 4:g.setColor(Color.GRAY);break;
		case 3:g.setColor(Color.lightGray);break;
		case 2:g.setColor(Color.YELLOW);break;
		case 1:g.setColor(Color.RED);break;
		
		}
		
		
		g.fillRect(0, 0, width, height);
		//if(hitPoints<5){
		g.setColor(Color.magenta);
		g.drawString(this.x%100+","+(this.y%100), 2, 12);
		g.drawRect(0, 0, width, height);
		//}
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
	public boolean collide(Collidable c, boolean ignorePosition) {
		double time = -1000;
		Line temp=null;
		for(Line l: lines){
			
			double tempTime=l.collideTime(c);
			if(tempTime>time){
				time=tempTime;
				temp=l;
			}
		}
		if(time>-1000){
			temp.collide(c);
			if(temp==lines[0])System.out.println("line 0");
			if(temp==lines[1])System.out.println("line 1");
			if(temp==lines[2])System.out.println("line 2");
			if(temp==lines[3])System.out.println("line 3");
			if(temp!=lines[2]||true){
				
			}
			this.hitPoints--;
			return true;
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
		return hitPoints>0;
	}



	@Override
	public double collideTime(Collidable C) {
		double time = -4000;
		for(Line l: lines){
			double tempTime=l.collideTime(C);
			if(tempTime>time)time=tempTime;
		}
		return time;
	}

}
