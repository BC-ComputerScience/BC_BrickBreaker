package resources;

import java.awt.image.BufferedImage;

import view.Sprite;

public abstract class ImageResource extends Resource {
	public abstract Sprite createSprite(int x, int y, int width, int height);
	@Override
	public String getType(){
		return "image";
	}
	
}
