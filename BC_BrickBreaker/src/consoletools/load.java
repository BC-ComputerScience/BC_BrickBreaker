package consoletools;

public class load implements ShellProgram {

	public load() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int Execute(Shell s, String... args) {
		s.out.println("this does nothing, ofr now");
		return 0;
	}

}
