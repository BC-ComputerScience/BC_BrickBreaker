package view;

import java.io.InputStream;
import java.io.PrintStream;

public interface Console {
	public PrintStream out();
	public InputStream in();
	public void addConsoleListener(ConsoleListener cl);
    public void removeConsoleListener(ConsoleListener cl);
	public PrintStream err();
}
