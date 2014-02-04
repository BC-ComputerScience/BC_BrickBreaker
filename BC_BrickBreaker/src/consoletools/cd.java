package consoletools;

import java.io.File;
import java.io.IOException;

public class cd implements ShellProgram{

	@Override
	public int Execute(Shell s, String... args) throws IOException {
		
		if(args.length>0){
			String path=args[0];
			File location;
			if(path.startsWith("/")){
				location=new File(path); 
			}else if(path.startsWith("~")){
				location=new File(s.homeDirectory().getCanonicalPath()+"/"+path.substring(1));
			}else{
				location=new File(s.currentDirectory().getCanonicalPath()+"/"+path);
				//System.out.println(s.currentDirectory().getPath()+"/"+path);
			}
			if(location.exists()){
				s.setDirectory(location.getCanonicalFile());
				return 0;
			}else{
				s.out.println("File does not exist!");
			}
		}
		return 1;
	}


}
