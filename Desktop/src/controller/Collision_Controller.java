package controller;

import java.awt.AWTEvent;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import resources.LevelReader;
import resources.PC_ResourceLoader;
import mathematics.Vector;
import model.Collision_Simulator;
import model.Paddle;
import view.Collision_View;

public class Collision_Controller implements Controller, ActionListener, MouseListener, MouseMotionListener, KeyListener{
	
	private Collision_View view;
	private Collision_Simulator model;
	private Tester tester;
	
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
	
	private Queue<AWTEvent> queue=new LinkedList<AWTEvent>();
	
	
	//TODO not really where it should be
	Paddle p;
	/**
	 * creates a Controller which then creates a model and view
	 * 
	 * @param width width of screen
	 * @param height height of screen
	 */
	public Collision_Controller(int width, int height){
		this.width=width;
		this.height=height;
		
		//create a view which can update the controller(this)
		view = new Collision_View(width,height, this);
		//create a model which can update to the view
		model = new Collision_Simulator(width,height,view);
		
		p = new Paddle(width/2,height-20,150);
		model.addGameObject(p);
		tester=new Tester(model);
		
		
		
		
		//make events in the view update this controller
		view.addMouseListener(this);
		view.addMouseMotionListener(this);
		view.addActionListener(this);
		view.addKeyListener(this);
		LevelReader ll=null;
		try{
			ll=new LevelReader("level/", new PC_ResourceLoader());
		}catch(UnsupportedOperationException e){
			System.err.println("while trying to load level got"+e.getMessage());
		}
		/*
		for(int i=0;i<width/64;i++){
			for(int j=0; j<5; j++){
				model.addGameObject(ll.createball(33+i*64,33+j*64));
			}
		}
		;/**/
		
		tester.testCase();
		
		timer=new Timer();
		
		
		gameloop=getLoopTask();
		startTimer();
		
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
		p.move(Direction);
		AWTEvent event=null;
		while((event=queue.poll())!=null){
			//TODO log time and operation
			if(event instanceof MouseEvent){
				
				
				MouseEvent mouse=(MouseEvent)event;
				if(mouse.isShiftDown()){
					tester.placeSphere(startx, starty,endx-startx,endy-starty,mouse.isAltDown());
					System.out.println(new mathematics.Vector(mouse.getX(),mouse.getY()));
				}else{
					System.out.println("selected: "+model.selectAtPoint(endx, endy));
					System.out.println(endx+","+endy);
				}
				
				
			}else if(event instanceof ActionEvent){
				ActionEvent action=(ActionEvent)event;
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
					tester.addRandomCircles(200);
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
	public void actionPerformed(ActionEvent action) {
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
	public void mouseClicked(MouseEvent event) {
		//System.out.println("mouse clicked");
		
	}

	@Override
	public void mouseEntered(MouseEvent event) {
		//System.out.println("mouse entered");
		
	}

	@Override
	public void mouseExited(MouseEvent event) {
		//System.out.println("mouse exited");
		
	}

	private int startx,starty,endx, endy;
	@Override
	public void mousePressed(MouseEvent event) {
		startx=event.getX();starty=event.getY();
	}

	@Override
	public void mouseReleased(MouseEvent event) {
		endx=event.getX();endy=event.getY();
		queue.add(event);
		//System.out.println("mouse released");
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		//System.out.println("mouse dragged ("+event.getX()+","+event.getY()+")");
		
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		//System.out.println("mouse moved");
		
	}
   int Direction=0;
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			Direction=-15;
			//p.move(-20);
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			Direction=15;
			//p.move(20);
			
		}else{
			System.out.println(e.getKeyChar());
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			Direction=0;
			//p.move(-20);
		}else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			Direction=0;
			//p.move(20);
			
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	
	

}
