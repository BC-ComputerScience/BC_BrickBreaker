package consoletools;

public class save implements ShellProgram {

	@Override
	public int Execute(Shell s, String... args) {
		s.out.println("this does nothing for now :P");
		return 0;
	}

}
