package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.Sprite;

public class PC_Resource implements resources.ImageResource{

	private String type;
	
	BufferedImage image;
	
	public PC_Resource(String type, String location) throws IOException{
		this.type=type;
		image=ImageIO.read(new File(location));
	}
	
	public BufferedImage getAt(int x, int y, int width, int height){
		System.out.println("getting image at: "+x+","+y+","+width+","+height);
		System.out.println(image.getWidth()+","+image.getHeight());
		return image.getSubimage(x, y, width, height);
	}
	public String getType(){
		return type;
	}
	@Override
	public Sprite createSprite(int x, int y, int width, int height) {
		return new PC_Sprite(getAt(x,y,width,height));
	}

}
