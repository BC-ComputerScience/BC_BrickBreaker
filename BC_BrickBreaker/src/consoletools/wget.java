package consoletools;

public class wget implements ShellProgram {

	@Override
	public int Execute(Shell s, String... args) {
		s.out.println("this does nothing, and may never do anything...maybe, but hopefully it will");
		return 0;
	}

}
