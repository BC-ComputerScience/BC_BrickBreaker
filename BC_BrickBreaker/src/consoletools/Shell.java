package consoletools;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import controller.LogicalSystem;
import view.Console;

public class Shell {
	
	private HashMap<String,ShellProgram> commands=new HashMap<String,ShellProgram>();
    public PrintStream out;//textDidsplay
    public PrintStream err;
    private InputStream in;
    @SuppressWarnings("unused")
	private Console c;
    //regex:("[^"\\]*(?:\\.[^"\\]*)*")
	private Pattern string=Pattern.compile("([\"])((?:\\\\\\1|.)*?)\\1|([^\\s\"]+)");
	
	LogicalSystem system=null;
    //private Pattern delimeter=Pattern.compile("");
    
    private File loc;
    private File home;
    public Shell(Console c, LogicalSystem lc) {
    	this(c);
    	system=lc;
    }
	public Shell(Console c) {
		this.c=c;
		this.out=c.out();
		this.err=c.err();
		in=c.in();
		try {
			loc=home=new File(".").getCanonicalFile();
		} catch (IOException e) {
			System.err.println("woah! I didn't know that could happen: there is no current directory!");
			e.printStackTrace();
		}//current directory
		
		out.println("Starting shell...");
		
		ls ls=new ls();
		
		addCommand("ls", ls);//list files in dir
		addCommand("dir", ls);//list files in dir
		addCommand("cd", new cd());//change dir
		addCommand("load", new load());//load a level
		addCommand("save", new save());//save a level
		addCommand("pwd", new pwd());//print present directory
		addCommand("mkdir", new mkdir());//make a directory with name
		addCommand("quit", new quit());//exit application
		addCommand("wget", new wget());//download 
		addCommand("echo", new echo());//echo to console.
		addCommand("vTest", new VectorTest());
		new Thread(new Runnable(){public void run() {listen();}}).start();
	}
	
	private void listen(){
		Scanner console=new Scanner(in);
		prompt();
		
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
				try{
					p.Execute(this, arglist.toArray(args));
				}catch(Exception e){
					out.println("Error while executing command: "+e.getMessage());
				}
				prompt();
				//c.out.flush();
			}else{
				out.println("Command Not Found!");
				prompt();
			}
		}//command was empty
		
	}
	public File currentDirectory(){
		return this.loc;
	}
	public File homeDirectory(){
		return this.home;
	}
	public void setDirectory(File f){
		if(f.isDirectory()){
			
			this.loc=f;
		}else{
			out.println("Not a directory!");
		}
	}
	public void addCommand(String name, ShellProgram porg){
		commands.put(name, porg);
	}

	private void prompt(){
		try {
			if(loc.getCanonicalFile().equals(home.getCanonicalFile())){
				out.print("~$: ");
			}else{
				out.print(loc.getName()+"$: ");
			}
		} catch (IOException e) {
			System.err.println("contained file is not valid! "+e.getMessage());
			e.printStackTrace();
		}
	}
}
