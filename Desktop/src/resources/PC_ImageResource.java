package resources;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import view.Sprite;

public class PC_ImageResource extends resources.ImageResource{
	
	private BufferedImage image;
	private String name;
	
	public PC_ImageResource(String location, String name) throws IOException{
		this(location);
		this.name=name;
	}
	public PC_ImageResource(String location) throws IOException{
		File temp=(new File(location));
		image=ImageIO.read(temp);
		name=temp.getName();
	}
	
	@Override
	public Sprite createSprite(int x, int y, int width, int height) {
		return new PC_Sprite(x,y,width,height,this);
	}
	

	@Override
	public String getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return name.hashCode();
	}

	public void draw(Graphics g, PC_Sprite s, int x, int y, int width, int height) {
		//don't even worry about it... really
		g.drawImage(image,s.x,s.y,s.x+s.width,s.y+s.height,x,y,x+width,y+height,null);
		
	}
	public void draw(Graphics g, PC_Sprite s,int x, int y) {
		draw(g,s,x,y,s.width,s.height);
	}




}
