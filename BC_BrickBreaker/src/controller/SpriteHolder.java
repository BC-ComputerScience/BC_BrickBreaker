package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import controller.SpriteSet;

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
	
	public BufferedImage getAtIndex(int i){
		return images[i];
	}
	public int length(){
		return images.length;
	}
	public int getFrameCount(int index){
		return this.frameCounts[index];
	}
	
	
}
