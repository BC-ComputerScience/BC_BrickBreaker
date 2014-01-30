package consoletools;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import view.Console;

public class Shell {
	private HashMap<String,ShellProgram> commands=new HashMap<String,ShellProgram>();
    public PrintStream out;//textDidsplay
    private InputStream in;
    @SuppressWarnings("unused")
	private Console c;
    
    private File loc;
	public Shell(Console c) {
		this.c=c;
		this.out=c.out();
		in=c.in();
		loc=new File(".");//current directory
		out.println("Starting shell...");
		ls ls=new ls();
		commands.put("ls", ls);
		commands.put("dir", ls);
		commands.put("cd", new cd());
		commands.put("load", new load());
		commands.put("save", new save());
		commands.put("pwd", new pwd());
		commands.put("mkdir", new mkdir());
		commands.put("quit", new quit());
		commands.put("wget", new wget());
		commands.put("echo", new echo());
		new Thread(new Runnable(){public void run() {listen();}}).start();
	}
	
	private void listen(){
		Scanner console=new Scanner(in);
		out.print("$: ");
		//c.out.flush();
		while(console.hasNextLine()){
			parseCommand(console.nextLine());
			
		}
	}
	private void parseCommand(String s){
		if(s.equals("?")){
			out.println("Avaliable Commands:");
			for(String cmd:commands.keySet()){
				out.println(cmd);
			}
		}
		
		
		Scanner scanner=new Scanner(s);
		ArrayList<String> arglist= new ArrayList<String>();
		if(scanner.hasNext()){
			String command=scanner.next();
			while(scanner.hasNext()){
				arglist.add(scanner.next());
			}
			ShellProgram p=commands.get(command);
			String[] args=new String[arglist.size()];
			if(p!=null){
				p.Execute(this, arglist.toArray(args));
				out.print("$: ");
				//c.out.flush();
			}else{
				out.println("Command Not Found!");
				out.print("$: ");
			}
		}//command was empty
		
	}
	public File currentDirectory(){
		return this.loc;
	}
	public void setDirectory(File f){
		if(f.isDirectory()){
			this.loc=f;
		}else{
			out.println("Not a directory!");
		}
	}

}
