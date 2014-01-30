package consoletools;

import java.io.File;

public class mkdir implements ShellProgram {

	@Override
	public int Execute(Shell s, String... args) {
		if(args[0].startsWith("/")){
			new File(args[0]).mkdirs();
		}else{
			new File(s.currentDirectory().getPath()+"/"+args[0]).mkdirs();
		}
		
		return 0;
	}

}
