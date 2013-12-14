package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import view.Renderable;

public class EmptyPolygon extends Collidable implements Renderable{
	int x[];
	int y[];
	int vertecies;
	Line[] sides;
	Polygon shape;
	private BufferedImage image;
	int boundingX, boundingY, boundingHeight, boundingWidth;
	
	public EmptyPolygon(int[] x,int[] y){
		vertecies=Math.min(x.length, y.length);
		sides=new Line[vertecies];
		this.x=x;
		this.y=y;
		for(int i=0;i<vertecies;i++){
			sides[i]=new Line(x[i],y[i],x[(i+1)%vertecies],y[(i+1)%vertecies]);
		}
		shape=new Polygon(x,y,vertecies);
		Rectangle rect=shape.getBounds();
		image = new BufferedImage(rect.width,rect.height,BufferedImage.TYPE_INT_ARGB);
		boundingX=rect.x;
		boundingY=rect.y;
		boundingWidth=rect.width;
		boundingHeight=rect.height;
		shape.translate(-rect.x, -rect.y);
		
	}

	@Override
	public int getBoundingWidth() {
		// TODO Auto-generated method stub
		return this.boundingWidth;
	}

	@Override
	public int getBoundingHeight() {
		// TODO Auto-generated method stub
		return this.boundingHeight;
	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return this.boundingX;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return this.boundingY;
	}

	@Override
	public boolean canCollideWith(Collidable C) {
		// TODO Auto-generated method stub
		return C instanceof Sphere;
	}

	@Override
	public boolean collide(Collidable C) {
		return collide(C, false);
		
	}
int temp=0;
	@Override
	public boolean collide(Collidable C, boolean ignorePosition) {
		temp++;
		for(Line l: this.sides){
			if(l.collide(C, ignorePosition)){
				System.out.println("collided"+temp);
				return true;
			}
		}
		return false;
		
	}

	@Override
	public int getCollisionPrecedence() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public void advance(double seconds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getImageX() {
		// TODO Auto-generated method stub
		return this.boundingX;
	}

	@Override
	public int getImageY() {
		// TODO Auto-generated method stub
		return this.boundingY;
	}

	@Override
	public Image getImage() {
		Graphics2D g= image.createGraphics();
		//g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC));
		//g.setColor(new Color(255,0,255,0));
		g.setColor(Color.RED);
		//g.fillRect(0, 0, width, height);
		g.setColor(Color.RED);
//		System.out.println(x1+","+y1+","+x2+","+y2);
		g.fillPolygon(this.shape);
		return image;
	}
	public boolean stillExists() {
		return true;
	}

}
