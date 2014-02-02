package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;

public class PC_ClassResource extends ClassResource {
	HashMap <String, Class<?>> classes=new HashMap<String, Class<?>>();
	
	public PC_ClassResource(String loc) throws IOException{
		URL url;
		File fileloc;
		JarInputStream jin=null;
		try {
			
			fileloc=new File(loc);
			url = fileloc.toURI().toURL();
			
			URLClassLoader loader = new URLClassLoader(new URL[]{url});
			
			
			
			jin= new JarInputStream(new FileInputStream(fileloc));
			ArrayList<String> classNames = new ArrayList<String>();
			
			while(jin.available()!=0){
				ZipEntry ze =jin.getNextEntry();
				if(ze!=null){
					String name=ze.getName();
					if(name.startsWith("extensions")&&name.endsWith(".class")){
						name=name.substring(0,name.length()-6).replace('/', '.');
						classNames.add(name);
					}
				}
			}
			for(String s:classNames){
				classes.put(s, loader.loadClass(s));
			}
			
		} catch (MalformedURLException e) {
			System.err.println("could not load class resource:"+loc);
		} catch (ClassNotFoundException e) {
			System.err.println("could find specified class resource:"+loc);
		}finally{
			if(jin!=null)jin.close();
		}
		
		
	}

	@Override
	public Object CreateInstance(String className, Object... args) {
		if(!className.startsWith("extensions.")){
			className="extensions."+className;
		}
		Class<?> clas=this.classes.get(className);
		Constructor<?>[] contstructors=clas.getConstructors();
		for(Constructor<?> c: contstructors){
			Class<?>[] parameters=c.getParameterTypes();
			if(args.length==parameters.length){
				if(checkIfArugmentsAcceptable(parameters, args)){
					try {
						return c.newInstance(args);
					} catch (IllegalArgumentException e) {
						System.err.println("Inncorrect Arguemnts, failed after confirm check, recommend: debug");
						e.printStackTrace();
					} catch (InstantiationException e) {
						System.err.println("it seems the class is abstract or an interface");
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						System.err.println("Illegal access, I don't quite know how this is possible... no really... really really");
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						System.err.println("this is a 'wrapper exception', if non other are present, do more research");
						e.printStackTrace();
					}
				}
			}
		}
		throw new IllegalArgumentException("args matched no constructors in length and/or type");
	}
	private boolean checkIfArugmentsAcceptable(Class<?>[] parameters, Object[] arguments){
		for(int i=parameters.length-1;i>=0;i++){
			if(!parameters[i].isInstance(arguments[i]))
				return false;
		}
		return true;
	}

	@Override
	public String[] getClassNames() {
		String[] ret=new String[this.classes.size()];
		return this.classes.keySet().toArray(ret);
	}

	
	@Override
	public String getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

}
