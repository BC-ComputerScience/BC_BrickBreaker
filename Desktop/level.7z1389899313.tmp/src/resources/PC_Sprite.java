package resources;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import resources.Resource;
import view.Sprite;

public class PC_Sprite implements Sprite{
	int x,y,width,height;
	Image image;
	
	public PC_Sprite(int x, int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		image=new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		Graphics g=image.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.CYAN);
		int yoffset=12;
		if(height<yoffset)
			yoffset/=height;
			
		g.drawString("null", 0, yoffset);
	}

	public PC_Sprite(BufferedImage image) {
		this.image=image;
	}

	public Image getImage() {
		return this.image;
	}
	

}
