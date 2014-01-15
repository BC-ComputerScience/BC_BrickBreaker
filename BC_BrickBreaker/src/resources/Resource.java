package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Resource {
	private String name;
	private String type;
	private Class cls;
	
	BufferedImage image;
	public Resource(String dir,String name, String type) throws IOException{
		this.type=type;
		this.name=name;
		image=ImageIO.read(new File(dir+name));
	}
	public Resource(Class cls, String name) {
		this.cls=cls;
		this.name=name;
		this.type="class";
		// TODO Auto-generated constructor stub
	}
	public BufferedImage getAt(int x, int y, int width, int height){
		return image.getSubimage(x, y, width, height);
	}
	public String getType(){
		return type;
	}

}
