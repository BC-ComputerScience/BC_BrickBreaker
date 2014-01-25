package resources;

import java.io.IOException;

public class PC_ResourceLoader implements resources.ResourceLoader{

	public PC_ResourceLoader() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Resource loadResource(String type, String location)
			throws IOException {
		System.out.println("Loading Resource: "+location);
		return new PC_Resource(type, location.trim());

	}

}
