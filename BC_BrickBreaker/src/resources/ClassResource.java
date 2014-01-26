package resources;

public interface ClassResource extends Resource{
	public Object CreateInstance(String className,Object... args);
	public String[] getClassNames();

}
