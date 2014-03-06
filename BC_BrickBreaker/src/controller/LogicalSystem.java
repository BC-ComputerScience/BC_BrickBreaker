package controller;

import java.io.PrintStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import resources.ResourceLoader;
import consoletools.Shell;
import consoletools.add;
import model.Collision_Simulator;
import model.Model;
import view.Console;
import view.View;

public class LogicalSystem {
	public static LogicalSystem system;
	
	private Model model;
	private View view;
	private Controller controller;
	private Console console;
	private Shell shell;
	private ResourceLoader resourceLoader;
	
	private PrintStream out;
	private PrintStream err;
	
	
	
	private BlockingQueue<Event> eventQueue=new LinkedBlockingQueue<Event>();
	
	public LogicalSystem(View v, Controller c){
		this(v,c,null);
	}
	public LogicalSystem(View v, Controller c, Console console){
		this.model=new Collision_Simulator(v.getGameWith(),v.getGameHeight(),v);
		this.view=v;
		this.controller=c;
		this.console=console;
		if(console!=null){
			out=console.out();
			err=console.err();
			shell=new Shell(console,this);
			shell.addCommand("add", new add(this.model));
			
		}else{
			out=System.out;
			err=System.err;
		}
	}
	public Model getModel() {
		return model;
	}
	public View getView() {
		return view;
	}
	public Controller getController() {
		return controller;
	}
	public Shell getShell() {
		return shell;
	}
	public ResourceLoader getResourceLoader() {
		return resourceLoader;
	}
	public void fireEvent(Event e){
		try {
			this.eventQueue.put(e);
		} catch (InterruptedException e1) {
			err.println("Could not put: "+e+" into queue");
			err.println(e1.getMessage());
		}
	}
	
	private class eventDispach implements Runnable{
		public void run() {
			while(true){
				try {
					Event e=eventQueue.take();
					
					
				} catch (InterruptedException e) {
					err.println("Something intereupted the 'our' event dispach while it was waiting");
				}
			}
		}
		
	}
	

}
