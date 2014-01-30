package controller;

import resources.ResourceLoader;
import consoletools.Shell;
import model.Collision_Simulator;
import model.Model;
import view.Console;
import view.View;

public class LogicalSystem {
	Model model;
	View view;
	Controller controller;
	Console console;
	Shell shell;
	ResourceLoader resourceLoader;
	
	
	public LogicalSystem(View v, Controller c){
		this(v,c,null);
	}
	public LogicalSystem(View v, Controller c, Console console){
		this.model=new Collision_Simulator(v.getGameWith(),v.getGameHeight(),v);
		this.view=v;
		this.controller=c;
		this.console=console;
		if(console!=null){
			shell=new Shell(console);
		}
	}

}
