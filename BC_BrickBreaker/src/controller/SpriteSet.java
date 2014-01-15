package controller;

import java.awt.image.BufferedImage;

public class SpriteSet implements SpriteSheet{
	int current=0;
	int currentSet=0;
	
	int count=0;
	int totalFrames;
	SpriteHolder[] Sprites;
	
	
	public SpriteSet(int numSets){
		Sprites = new SpriteHolder[numSets];
	}
	public void addSpriteSet
	
	
	
	public BufferedImage currentSprite(){
		
		return Sprites[currentSet].getAtIndex(current%totalFrames);
	}
	
	
	
	@Override
	public void advance() {
		
		if(count++>Sprites[currentSet].getFrameCount(current%totalFrames)){
			count=0;
			current++;
		}
		
	}
	@Override
	public void advance(double time) {
		//currently doesn't take time into account
		this.advance();
		
	}
	@Override
	public void updateHealth(int health) {
		// TODO Auto-generated method stub
		
	}
}
