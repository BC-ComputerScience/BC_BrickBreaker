package controller;

import resources.ResourceLoader;
import consoletools.Shell;
import model.Collision_Simulator;
import model.Model;
import view.Console;
import view.View;

public class LogicalSystem {
	private Model model;
	private View view;
	private Controller controller;
	private Console console;
	private Shell shell;
	private ResourceLoader resourceLoader;
	
	public LogicalSystem(View v, Controller c){
		this(v,c,null);
	}
	public LogicalSystem(View v, Controller c, Console console){
		this.model=new Collision_Simulator(v.getGameWith(),v.getGameHeight(),v);
		this.view=v;
		this.controller=c;
		this.console=console;
		if(console!=null){
			shell=new Shell(console,this);
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
	
	public void addKeyEvent(){
		
	}
	public void addPointerEvent(){
		
	}
	

}
