package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.Sprite;

public class PC_ImageResource implements resources.ImageResource{
	
	private BufferedImage image;
	
	public PC_ImageResource(String location) throws IOException{
		image=ImageIO.read(new File(location));
	}
	public BufferedImage getAt(int x, int y, int width, int height){
		return image.getSubimage(x, y, width, height);
	}
	@Override
	public Sprite createSprite(int x, int y, int width, int height) {
		return new PC_Sprite(getAt(x,y,width,height));
	}

}
