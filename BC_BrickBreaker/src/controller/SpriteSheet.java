package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SpriteSheet {
	//ArrayList<Double>times;
	//ArrayList<BufferedImage>images;
	BufferedImage[] images;
	int[] frameCounts;
	int currentFrame;
	double time=0;
	double highestTime;
	int lastAdded=0;
	int totalFrames;
	public SpriteSheet(int frames){
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
	int current=0;
	public BufferedImage currentSprite(){
		return images[(current++/2)%totalFrames];
	}
}
