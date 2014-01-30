package consoletools;

import java.io.File;

public class cd implements ShellProgram{

	@Override
	public int Execute(Shell s, String... args) {
		
		if(args.length>0){
			String path=args[0];
			File location;
			if(path.startsWith("/")){
				location=new File(path); 
			}else if(path.startsWith("~")){
				location=new File(path);
			}else{
				location=new File(s.currentDirectory().getPath()+"/"+path);
				//System.out.println(s.currentDirectory().getPath()+"/"+path);
			}
			if(location.exists()){
				s.setDirectory(location);
				return 0;
			}else{
				s.out.println("File does not exist!");
			}
		}
		return 1;
	}


}
