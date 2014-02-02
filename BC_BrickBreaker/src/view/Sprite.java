package view;

import resources.Savable;


public abstract class Sprite implements Savable{
	public abstract String getResourceName();
	public abstract int getResourceID();
	
	public abstract int SheetX();
	public abstract int SheetY();
	public abstract int SheetWidth();
	public abstract int SheetHeight();
	
	//sorry for  this looking so crazy;
	public String toXml(){
		StringBuilder ret=new StringBuilder("<bc:sprite resource_name=\"");
		ret.append(this.getResourceName());
		ret.append("\" resource_id=\"");
		ret.append(this.getResourceID());
		ret.append("\"> <bc:x>");
		ret.append(this.SheetX());
		ret.append("</bc:x> <bc:y>");
		ret.append(this.SheetY());
		ret.append("</bc:y> <bc:width>");
		ret.append(this.SheetWidth());
		ret.append("</bc:width> <bc:height>");
		ret.append(this.SheetHeight());
		ret.append("</bc:height> </bc:sprite>");
		return ret.toString();
		
	}
}
