package resources;


import java.io.File;
import java.io.IOException;


public abstract class Resource implements Savable{
	public abstract String getLocation();
	public abstract String getName();
	public abstract int getID();
	public abstract String getType();
	public String toXml(){
		StringBuilder ret=new StringBuilder("<bc:link name=\"");
		ret.append(this.getName());
		ret.append("\" type=\"");
		ret.append(this.getType());
		ret.append("\">");
		ret.append(this.getLocation());
		ret.append("</bc:link>");
		return ret.toString();
		
	}
	

}
