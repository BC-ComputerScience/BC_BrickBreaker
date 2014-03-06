package model.gameObjects;

import resources.SpriteSet;
import view.Renderable;
import view.Sprite;
import mathematics.Vector;

public class Rectangle extends Movable implements Renderable{
	
	private int width;
	private int height;
	private SpriteSet spriteSet=new SpriteSet();
	
	public Rectangle(Vector pos, int width, int height, Vector trajectory, double mass) {
		super(pos, width, height, trajectory, mass);
		this.width=width;
		this.height=height;
	}
	public int getWidth(){
		return width;
	}
	public int getHeight(){
		return height;
	}
	
	/******************Game Object********************/
	@Override
	public GameObject cloneAt(Vector v) {
		Rectangle ret=new Rectangle(v,width,height,this.getTrajectory(),this.getMass());
		ret.addSpriteSet(this.spriteSet);
		return ret;
	}
	/******************Collidable********************/
	@Override
	public boolean canCollideWith(Collidable c) {
		return (c instanceof Sphere||c instanceof Rectangle);
	}
	
	@Override
	public double collideTime(Collidable C) {
		if(C instanceof Rectangle){
			if(C.checkBoundingCollision(this)){
				return 0;
			}
		}else if(C instanceof Sphere){
			if(C.checkBoundingCollision(this)){
				return 0;
			}
			
		}
		return Double.MAX_VALUE;
	}
	@Override
	public boolean collide(Collidable C) {
		if(C instanceof Movable){
			Vector orient= ((Movable) C).getCenterOfMass().subtract(this.getCenterOfMass());
			double x=orient.getElement(0);
			double y=orient.getElement(1);
			if(Math.abs(x)>Math.abs(y)){
				Movable.bounce(this, (Movable)C, new Vector(x,0), null);
			}else{
				Movable.bounce(this, (Movable)C, new Vector(0,y), null);
			}
		}
		
		return false;
	}
	@Override
	public int getCollisionPrecedence() {
		return 1;
	}
	@Override
	public boolean stillExists() {
		return true;
	}
	/******************Movable********************/
	@Override
	public Vector getCenterOfMass() {
		return this.getPos().add(new Vector(width/2.,height/2.));
	}
	@Override
	public double getArea() {
		return width*height;
	}
	@Override
	public Vector getMomentOfInteria() {
		return null;
	}
	/******************Renderable********************/
	@Override
	public int getImageX() {
		return (int)this.getPos().getElement(0);
	}
	@Override
	public int getImageY() {
		return (int)this.getPos().getElement(1);
	}
	@Override
	public int getImageWidth() {
		return width;
	}
	@Override
	public int getImageHeight() {
		return height;
	}
	@Override
	public Sprite getImage() {
		return this.spriteSet.currentSprite();
	}
	@Override
	public void addSpriteSet(SpriteSet set) {
		this.spriteSet=set;
	}

}
