package consoletools;

public class echo implements ShellProgram {

	@Override
	public int Execute(Shell s, String... args) {
		// TODO Auto-generated method stub
		s.out.println(args[0]);
		return 0;
	}

}
