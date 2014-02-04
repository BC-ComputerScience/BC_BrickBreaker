package resources;

import java.awt.Graphics;
import java.awt.Image;

import view.Sprite;

public class PC_Sprite extends Sprite{
	int x,y,width,height;
//	Image image;
	PC_ImageResource resource;
	

	public PC_Sprite(int x, int y, int width, int height, PC_ImageResource resource) {
		
		super(x,y,width,height);
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

	
	public int SheetX() {
		return x;
	}

	
	public int SheetY() {
		return y;
	}

	
	public int SheetWidth() {
		return width;
	}

	
	public int SheetHeight() {
		return height;
	}
	public int offsetX(){
		return 0;
	}
	public int offsetY(){
		return 0;
	}

	@Override
	public double getDuration() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void setDuration(double dur) {
		// TODO Auto-generated method stub
		
	}
	

}
