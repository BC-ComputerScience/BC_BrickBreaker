package resources;

public abstract class ClassResource extends Resource{
	public abstract Object CreateInstance(String className,Object... args);
	public abstract String[] getClassNames();

}
