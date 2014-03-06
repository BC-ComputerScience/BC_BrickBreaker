package controller;


import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import consoletools.Shell;
import resources.LevelReader;
import resources.PC_ResourceLoader;
import mathematics.Vector;
import model.Collision_Simulator;
import model.ObjectLoader;
import model.gameObjects.Paddle;
import model.gameObjects.Sphere;
import view.Collision_View;
import view.PC_Console;

public class Collision_Controller implements Controller, ActionListener, MouseListener, java.awt.event.MouseMotionListener, java.awt.event.KeyListener{
	
	private Collision_View view;
	private Collision_Simulator model;
	private Tester tester;
	
	private PC_Console console;
	//A time set to proc every 20 millis
	private Timer timer;
	private int millisPerProc=50;
	private double timeDialation=1;
	//timers can be inconsistent, so keep track of time
	private long lastTime=0;
	private TimerTask gameloop;//points the timer at the gameloop
	private boolean isPaused=true;
	
	@SuppressWarnings("unused")
	private int width, height;
	Paddle p;
	private Queue<java.awt.AWTEvent> queue=new LinkedList<java.awt.AWTEvent>();
	
	
	//TODO not really where it should be
	
	/**
	 * creates a Controller which then creates a model and view
	 * 
	 * @param width width of screen
	 * @param height height of screen
	 */
	public Collision_Controller(int width, int height){
		this.width=width;
		this.height=height;
		LevelReader ll=null;
		
		
		
		//create a view which can update the controller(this)
		view = new Collision_View(width,height, this);
		console = view.getConsole();
		//Shell s=new Shell(view.getConsole());
//		//create a model which can update to the view
		LogicalSystem s=new LogicalSystem(view,this,console);
		
		//model = new Collision_Simulator(width,height,view);
		model=(Collision_Simulator) s.getModel();
		ObjectLoader.LOADER.addTemplate("sphere", new Sphere(new Vector(2),new Vector(10,15),100));
		
		//p = new Paddle(width/2,height-20,150);
		//model.addGameObject(p);
		tester=new Tester(model);
//		
//		
//		
//		
//		//make events in the view update this controller
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		view.addActionListener(this);
		view.addKeyListener(this);
//		
//		//String level=SelectLevel();
//		//level=level!=null?level:"first_campaign";
//		//ll=new LevelReader("levels/"+"first_campaign", new PC_ResourceLoader());
//		
//		
//		/*
//		for(int i=0;i<width/64;i++){
//			for(int j=0; j<5; j++){
//				model.addGameObject(ll.createball(33+i*64,33+j*64));
//			}
//		}
//		;/**/
//		
		tester.testCase();
		
		timer=new Timer();
		
		
		gameloop=getLoopTask();
		startTimer();
		
	}
	
	String SelectLevel(){
		File[] files=this.avaliableLevels();
		for(File f:files){
			console.out.println("Avaliable Level: "+f.getName());
		}
		console.out.print("Select a level: ");
		System.out.println("test1");
		Scanner s=new Scanner(console.in);
		while(s.hasNext()){
			System.out.println("test2");
			String next=s.next();
			System.out.println("input: "+next);
			for(File f:files){
				System.out.println("test3");
				if(next.equals(f.getName()))
					return next;
			}
			console.out.println("sorry that is not Avaliable");
		}
		System.out.println("test4");
//		System.out.println("");
	return null;	
	}
	
	File[] avaliableLevels(){
		File f=new File("levels");
		return f.listFiles();
	}
	
	
	
	/**
	 * gets a time task pointing at the start of the game logic
	 * @return 
	 */
	private TimerTask getLoopTask(){
		return new TimerTask(){
			public void run() {procTimer();}
		};
	}
	/**
	 * entry point for the timer, should filter out the stack of repeated procs
	 * when there is a frame of lag.
	 */
	public void procTimer(){
		//TODO filter out procs that happen in significantly less than 20 millis
		gameLogic();
	}
	/**
	 * contains all the logic that periodically updates the model
	 */
	public void gameLogic(){
		if(lastTime==0){
			//use first one to get a baring for time, 
			lastTime=System.currentTimeMillis();
			//cant to logic because delta time is undefined(0)
		}else{
			//model modification methods are not thread safe, so do them in the gameloop
			
			interpretCommands();
			
			long thisTime=System.currentTimeMillis();
			if(!isPaused){
				model.advance(timeDialation*(/**/20/*/Math.min(thisTime-lastTime,100)/**/)/1000.0);
			}
			/*for(int i=0;i<100;i++){
				model.advance(0);
			}*/
			model.updateView();
			lastTime=thisTime;
		}
	}
	/**
	 * iterates through the list of commands and performs them. should be called
	 * in gameloop thread as this is not thread safe
	 */
	public void interpretCommands(){
		//p.move(Direction);
		java.awt.AWTEvent event=null;
		while((event=queue.poll())!=null){
			//TODO log time and operation
			if(event instanceof java.awt.event.MouseEvent){
				
				
				java.awt.event.MouseEvent mouse=(java.awt.event.MouseEvent)event;
				if(mouse.isShiftDown()){
					tester.placeSphere(startx, starty,endx-startx,endy-starty,mouse.isAltDown());
					System.out.println(new mathematics.Vector(mouse.getX(),mouse.getY()));
				}else{
					System.out.println("selected: "+model.selectAtPoint(endx, endy));
					System.out.println(endx+","+endy);
				}
				
				
			}else if(event instanceof java.awt.event.ActionEvent){
				java.awt.event.ActionEvent action=(java.awt.event.ActionEvent)event;
				if(action.getActionCommand()=="Reset"){
					model.reset();
				}else if(action.getActionCommand()=="Add Cradle"){
					tester.addNewtonsCradle();
				}else if(action.getActionCommand()=="Add Random"){
					tester.addRandomCircles(10);
				}else if(action.getActionCommand()=="Add CurrentTest"){
					tester.testCase();
				}else if(action.getActionCommand()=="Set MillisPerFrame"){
					gameloop.cancel();
					gameloop=getLoopTask();
					String s = (String)JOptionPane.showInputDialog("Give milliseconds per rendering cycle!");
					try{
						millisPerProc=(Integer.parseInt(s));
					} catch(Exception e){
						System.err.println("invalid input");
					}
					timer.schedule(gameloop, millisPerProc, millisPerProc);
				}else if(action.getActionCommand()=="Set COR"){
					gameloop.cancel();
					gameloop=getLoopTask();
					String s = (String)JOptionPane.showInputDialog("give coefficiant of restitution!");
					try{
						//model.setCOR(Double.parseDouble(s));
					} catch(Exception e){
						System.err.println("invalid input");
					}
					timer.schedule(gameloop, millisPerProc, millisPerProc);
				}else if(action.getActionCommand()=="New"){
					model.reset();
					tester.addNewtonsCradle();
				}else if(action.getActionCommand()=="Quit"){
					System.exit(0);
				}else if(action.getActionCommand()=="Set Gravity"){
					gameloop.cancel();
					gameloop=getLoopTask();
					String s = (String)JOptionPane.showInputDialog("give gravity!!");
					try{
						model.setGravity(Double.parseDouble(s));
					} catch(Exception e){
						System.err.println("invalid input");
					}
					timer.schedule(gameloop, millisPerProc, millisPerProc);
				}else if(action.getActionCommand()=="Add Large Random"){
					tester.addRandomCircles(100);
				}
				System.out.println(action.getActionCommand());
			}
		}
	}
	/**
	 * starts the timer
	 */
	public void startTimer() {
		synchronized(this){
			//model.advance(0.0);
			timer.scheduleAtFixedRate(gameloop, 500, millisPerProc);
			isPaused=false;
		}
		
	}
	@Override
	public void actionPerformed(java.awt.event.ActionEvent action) {
		if(action.getActionCommand()=="Pause"){
			if(isPaused){
				isPaused=false;
			}else{
				isPaused=true;
			}
		}else{
			queue.add(action);
		}
		
		
	}

	@Override
	public void mouseClicked(java.awt.event.MouseEvent event) {
		//System.out.println("mouse clicked");
		
	}

	@Override
	public void mouseEntered(java.awt.event.MouseEvent event) {
		//System.out.println("mouse entered");
		
	}

	@Override
	public void mouseExited(java.awt.event.MouseEvent event) {
		//System.out.println("mouse exited");
		
	}

	private int startx,starty,endx, endy;
	@Override
	public void mousePressed(java.awt.event.MouseEvent event) {
		startx=event.getX();starty=event.getY();
	}

	@Override
	public void mouseReleased(java.awt.event.MouseEvent event) {
		endx=event.getX();endy=event.getY();
		queue.add(event);
		//System.out.println("mouse released");
		
	}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent event) {
		//System.out.println("mouse dragged ("+event.getX()+","+event.getY()+")");
		
	}

	@Override
	public void mouseMoved(java.awt.event.MouseEvent event) {
		//System.out.println("mouse moved");
		
	}
   int Direction=0;
	@Override
	public void keyPressed(java.awt.event.KeyEvent e) {
		if(e.getKeyCode()==java.awt.event.KeyEvent.VK_LEFT){
			Direction=-15;
			//p.move(-20);
		}else if(e.getKeyCode()==java.awt.event.KeyEvent.VK_RIGHT){
			Direction=15;
			//p.move(20);
			
		}else{
			System.out.println(e.getKeyChar());
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(java.awt.event.KeyEvent e) {
		if(e.getKeyCode()==java.awt.event.KeyEvent.VK_LEFT){
			Direction=0;
			//p.move(-20);
		}else if(e.getKeyCode()==java.awt.event.KeyEvent.VK_RIGHT){
			Direction=0;
			//p.move(20);
			
		}
	}

	@Override
	public void keyTyped(java.awt.event.KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}




	
	

}
