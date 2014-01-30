package consoletools;

public class quit implements ShellProgram {

	@Override
	public int Execute(Shell s, String... args) {
		System.exit(0);
		return 0;
	}

}
