package resources;

import java.io.IOException;

public interface ResourceLoader {

	Resource loadResource(String type, String location)throws  IOException;
	
}
