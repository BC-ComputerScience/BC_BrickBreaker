package view;

import resources.Savable;


public abstract class Sprite implements Savable{
	public abstract String getResourceName();
	public abstract int getResourceID();
	
	private int x;
	private int y;
	private int width;
	private int height;
	int offsetX;
	int offsetY;
	
	public Sprite(int x, int y, int width, int height){
		this(x,y,width,height,0,0);
	}
	
	public Sprite(int x, int y, int width, int height, int offsetX, int offsetY){
		this.x=x;
		this.y=y;
		this.height=height;
		this.width=width;
		this.offsetX=offsetX;
		this.offsetY=offsetY;
	}
	
	
	public int getSheetX(){
		return x;
	}
	public int getSheetY(){
		return y;
	}
	public int getSheetWidth(){
		return width;
	}
	public int getSheetHeight(){
		return height;
	}
	public int getOffsetX(){
		return offsetX;
	}
	public int getOffsetY(){
		return offsetY;
	}
	
	public void setSheetX(int x){
		this.x=x;
	}
	public void setSheetY(int y){
		this.y=y;
	}
	public void setSheetWidth(int width){
		this.width=width;
	}
	public void setSheetHeight(int height){
		this.height=height;
	}
	public void setOffsetX(int xOffset){
		this.offsetX=xOffset;
	}
	public void setOffsetY(int yOffset){
		this.setOffsetY(yOffset);
	}
	public abstract double getDuration();
	public abstract void setDuration(double dur);
	
	//sorry for  this looking so crazy;
	public String toXml(){
		StringBuilder ret=new StringBuilder("<bc:sprite resource_name=\"");
		ret.append(this.getResourceName());
		ret.append("\" resource_id=\"");
		ret.append(this.getResourceID());
		ret.append("\"> <bc:x>");
		ret.append(this.getSheetX());
		ret.append("</bc:x> <bc:y>");
		ret.append(this.getSheetY());
		ret.append("</bc:y> <bc:width>");
		ret.append(this.getSheetWidth());
		ret.append("</bc:width> <bc:height>");
		ret.append(this.getSheetHeight());
		ret.append("</bc:height> </bc:sprite>");
		return ret.toString();
		
	}
}
