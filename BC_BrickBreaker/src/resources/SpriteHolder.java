package resources;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SpriteHolder {
	//ArrayList<Double>times;
	//ArrayList<BufferedImage>images;
	BufferedImage[] images;
	int[] frameCounts;
	int currentFrame;
	double time=0;
	double highestTime;
	int lastAdded=0;
	int totalFrames;
	public SpriteHolder(int frames){
		images=new BufferedImage[frames];
		this.frameCounts=new int[frames];
		this.totalFrames=frames;
		//System.out.println("Making a SpriteSheet with "+frames+" frames");
	}
	
	public void addSprite(BufferedImage image, int frames){
		images[lastAdded]=image;
		frameCounts[lastAdded]=frames;
		lastAdded++;

	}
	public boolean isUsable(){
		return images[0]!=null;
	}
	
	public SpriteSet getSpriteSheet(){
		return new SpriteSet();
		
	}
	
	private class SpriteSet implements SpriteSheet{
		int current=0;
		int count=0;
		public BufferedImage currentSprite(){
			
			return images[(current)%totalFrames];
		}
		@Override
		public void advance() {
			if(count++>frameCounts[current%totalFrames]){
				count=0;
				current++;
			}
			
		}
		
	}
}
