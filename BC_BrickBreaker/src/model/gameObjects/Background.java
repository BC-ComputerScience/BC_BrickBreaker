package model.gameObjects;

import mathematics.Vector;
import resources.SpriteSet;
import view.Renderable;
import view.Sprite;

public class Background implements Renderable{
	private SpriteSet spriteSet=new SpriteSet();
	int width, height,x,y;
	private Background(){}
	
	public Background(int width, int height) {
		this.width=width;
		this.height=height;
		this.x=0;
		this.y=0;
	}

	@Override
	public int getImageX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getImageY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Sprite getImage() {
		// TODO Auto-generated method stub
		return spriteSet.currentSprite();
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

	@Override
	public void addSpriteSet(SpriteSet set) {
		this.spriteSet=set;
		
	}

	public GameObject cloneAt(int x, int y) {
		Background ret=new Background();
		ret.width=width;
		ret.height=height;
		ret.x=x;
		ret.y=y;
		ret.spriteSet=this.spriteSet.clone();
		return ret;
	}

	public GameObject cloneAt(Vector v) {
		return cloneAt((int)v.getElement(0),(int)v.getElement(1));
	}

	public void translate(int deltaX, int deltaY) {
		this.x+=deltaX;
		this.y+=deltaY;
		// TODO Auto-generated method stub
		
	}

	public void translate(Vector v) {
		translate((int)v.getElement(0),(int)v.getElement(1));
		// TODO Auto-generated method stub
		
	}

}
