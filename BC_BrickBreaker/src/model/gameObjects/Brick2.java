package model.gameObjects;


import resources.SpriteSet;
import resources.SpriteSheet;
import view.Renderable;
import view.Sprite;

public class Brick2 extends Collidable implements Renderable{
	private int width,height;
	private Line[] lines=new Line[4];
	int hitPoints;
	int x, y;
	private SpriteSet spriteSet=new SpriteSet();
	/**
	 * creates a box
	 * @param x top left x
	 * @param y top left right
	 * @param width width of box
	 * @param height height of box
	 */
	public Brick2(double x, double y, int width,int height) {
		this(x,y,width,height,1);
		
	}


	public Brick2(double x, double y, int width, int height, int health) {
		//System.out.println("x:"+x+"y:"+y+"width:"+width+"height:"+height+"health:"+health);
		this.x=(int)x;
		this.y=(int)y;
		this.width=width;
		this.height=height;
		lines[0]=new Line(x,y,x,y+height,true);
		lines[1]=new Line(x,y+height,x+width,y+height,true);
		lines[2]=new Line(x+width,y+height,x+width,y,true);
		lines[3]=new Line(x+width,y,x,y,true);
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
	public Sprite getImage() {
		return spriteSet.currentSprite();
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
	public double collideTime(Collidable C) {
		double time = Integer.MAX_VALUE;
		
		for(Line l: lines){
			//if(l==lines[1]){
			double tempTime=l.collideTime(C);
			if(tempTime<time)time=tempTime;
			//}
		}
		return time;
	}

	@Override
	public boolean collide(Collidable c, boolean ignorePosition) {
		double time = Integer.MAX_VALUE;
		Line temp=null;
		for(Line l: lines){
			double tempTime=l.collideTime(c);
			//if(l==lines[1]||l==lines[0]||l==lines[2]){
				if(tempTime<=time){
					time=tempTime;
					temp=l;
				}
			//}
		}
		if(time<=0){
//			if(temp==lines[0])System.err.println("left");
//			if(temp==lines[1])System.err.println("bottom");
//			if(temp==lines[2])System.err.println("right");
//			if(temp==lines[3])System.err.println("top");
//			
			if(temp.collide(c)){
//				System.err.println(time);
				
				this.hitPoints--;
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
	public void advance(double seconds) {
		
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
