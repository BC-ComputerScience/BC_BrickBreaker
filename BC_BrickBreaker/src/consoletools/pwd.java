package consoletools;

public class pwd implements ShellProgram {

	@Override
	public int Execute(Shell s, String... args) {
		s.out.println(s.currentDirectory().getAbsolutePath());
		return 0;
	}

}
