package resources;

import java.io.IOException;

public interface ResourceLoader {

	Resource loadResource(String type, String location)throws  IOException;
	ImageResource LoadImageResource(String location)throws  IOException;
	ClassResource LoadClassResource(String location)throws  IOException;
	
}
