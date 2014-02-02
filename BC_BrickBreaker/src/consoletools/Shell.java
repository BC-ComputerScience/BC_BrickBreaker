package consoletools;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.LogicalSystem;
import view.Console;

public class Shell {
	
	private HashMap<String,ShellProgram> commands=new HashMap<String,ShellProgram>();
    public PrintStream out;//textDidsplay
    private InputStream in;
    @SuppressWarnings("unused")
	private Console c;
    //regex:("[^"\\]*(?:\\.[^"\\]*)*")
	private Pattern string=Pattern.compile("([\"])((?:\\\\\\1|.)*?)\\1|([^\\s\"]+)");
	
	LogicalSystem system=null;
    //private Pattern delimeter=Pattern.compile("");
    
    private File loc;
    public Shell(Console c, LogicalSystem lc) {
    	this(c);
    	system=lc;
    }
	public Shell(Console c) {
		List<String> list=null;

		this.c=c;
		this.out=c.out();
		in=c.in();
		loc=new File(".");//current directory
		out.println("Starting shell...");
		
		ls ls=new ls();
		
		commands.put("ls", ls);//list files in dir
		commands.put("dir", ls);//list files in dir
		commands.put("cd", new cd());//change dir
		commands.put("load", new load());//load a level
		commands.put("save", new save());//save a level
		commands.put("pwd", new pwd());//print present directory
		commands.put("mkdir", new mkdir());//make a directory with name
		commands.put("quit", new quit());//exit application
		commands.put("wget", new wget());//download 
		commands.put("echo", new echo());//echo to console.
		commands.put("vTest", new VectorTest());
		new Thread(new Runnable(){public void run() {listen();}}).start();
	}
	
	private void listen(){
		Scanner console=new Scanner(in);
		out.print("$: ");
		
		;
		//c.out.flush();
		while(console.hasNextLine()&&console.hasNext()){
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
		
		Matcher parser=string.matcher(s);
		
		//Scanner scanner=new Scanner(s);
		//scanner.useDelimiter(pattern)
		ArrayList<String> arglist= new ArrayList<String>();
		//System.err.println(scanner.delimiter());
		if(parser.find()){
			String command=parser.group();
			while(parser.find()){
				arglist.add(parser.group());
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
