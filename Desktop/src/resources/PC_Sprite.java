package resources;

import java.awt.Graphics;
import java.awt.Image;

import view.Sprite;

public class PC_Sprite extends Sprite{
	int x,y,width,height;
	Image image;
	PC_ImageResource resource;
	

	public PC_Sprite(int x, int y, int width, int height, PC_ImageResource resource) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		this.resource=resource;
	}

	public void draw(Graphics g,int x, int y) {
		resource.draw(g,this,x,y);
	}
	public void draw(Graphics g,int x, int y, int width,int height) {
		resource.draw(g,this,x,y,width,height);
	}
	public PC_ImageResource getResource(){
		return this.resource;
	}
	
	@Override
	public String getResourceName() {
		return resource.getName();
	}

	@Override
	public int getResourceID() {
		return resource.getID();
	}

	@Override
	public int SheetX() {
		return x;
	}

	@Override
	public int SheetY() {
		return y;
	}

	@Override
	public int SheetWidth() {
		return width;
	}

	@Override
	public int SheetHeight() {
		return height;
	}
	

}
