package consoletools;

import java.io.IOException;

public class pwd implements ShellProgram {

	@Override
	public int Execute(Shell s, String... args) throws IOException {
		s.out.println(s.currentDirectory().getCanonicalPath());
		return 0;
	}

}
