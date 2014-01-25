package model;

import resources.SpriteSet;
import view.Renderable;
import view.Sprite;

public class Background implements Renderable{
	private SpriteSet spriteSet=new SpriteSet();
	int width, height;
	public Background(int width, int height) {
		this.width=width;
		this.height=height;
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

}
