package resources;

import java.io.IOException;

public class PC_ResourceLoader implements resources.ResourceLoader{

	public PC_ResourceLoader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Resource loadResource(String type, String location)
			throws IOException {
		if(type.compareToIgnoreCase("image")==0){
			return this.LoadImageResource(location);
		}else if(type.compareToIgnoreCase("jar")==0){
			return this.LoadClassResource(location);
		}else{
			return null;
		}
		
		
		
		

	}

	@Override
	public ImageResource LoadImageResource(String location) throws IOException {
		return new PC_ImageResource(location.trim());
	}

	@Override
	public ClassResource LoadClassResource(String location) throws IOException {
		// TODO Auto-generated method stub
		return new PC_ClassResource(location.trim());
	}

}
