package resources;

import view.Sprite;

public class SpriteSet implements Cloneable{
	double maxprogression=100;
	double progression=0;
	double frames;
	SpriteSheet sprites;
	public SpriteSet(){
		frames=0;
	}
	
	public SpriteSet(SpriteSheet spriteSheet) {
		sprites=spriteSheet;
		frames=sprites.getLength();
		System.err.println(frames);
	}

	public Sprite currentSprite() {
		if(frames==0)return null;
		return sprites.getSprite((int)(progression/maxprogression*frames));
		
		
	}
	public void advance(double d){
		progression+=d;
	}
	public SpriteSet clone(){
		return null;
		dsafjklhadsfhu
		
	}

}
