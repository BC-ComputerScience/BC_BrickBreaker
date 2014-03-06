package consoletools;

import mathematics.Vector;
import model.Model;
import model.ObjectLoader;

public class add implements ShellProgram {
	Model m;
	public add(Model m){
		this.m=m;
	}

	@Override
	public int Execute(Shell s, String... args) throws Exception {
		String name=args[0];
		double[] comps=new double[args.length-1];
		for(int i=1;i<args.length;i++){
			comps[i-1]=Double.parseDouble(args[i]);
		}
		try{
			m.addGameObject(ObjectLoader.LOADER.createAt(name, new Vector(comps)));
		}catch(NullPointerException e){
			s.err.println("no object of that name found");
			return 1;
		}
		return 0;
	}

}
