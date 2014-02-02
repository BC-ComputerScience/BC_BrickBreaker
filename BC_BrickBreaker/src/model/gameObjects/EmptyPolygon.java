package model.gameObjects;

//TODO REMOVE dependancy on awt.polygon
import java.awt.Polygon;
import java.awt.Rectangle;


import resources.SpriteSet;
import view.Renderable;
import view.Sprite;

public class EmptyPolygon extends Collidable implements Renderable{
	int x[];
	int y[];
	int vertecies;
	Line[] sides;
	Polygon shape;
	private SpriteSet spriteSet=new SpriteSet();
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
	public Sprite getImage() {
		return spriteSet.currentSprite();
	}
	public boolean stillExists() {
		return true;
	}
	public double collideTime(Collidable C) {
		double time = 1;
		for(Line l: this.sides){
			double tempTime=l.collideTime(C);
			if(tempTime<time)time=tempTime;
		}
		return time;
	}

	@Override
	public int getImageWidth() {
		// TODO Auto-generated method stub
		return this.boundingWidth;
	}

	@Override
	public int getImageHeight() {
		// TODO Auto-generated method stub
		return this.boundingHeight;
	}
	public void addSpriteSet(SpriteSet set) {
		this.spriteSet=set;
		
	}

}
