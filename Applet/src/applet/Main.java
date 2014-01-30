package applet;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.management.ImmutableDescriptor;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import view.Drawing_Panel;
import view.Renderable;
import mathematics.Vector;
import model.Model;


public class Main extends JApplet implements view.View{

	int width;
	int height;
	Model m;
	Drawing_Panel d;
	//BufferedImage img;
	Timer timer;
	public void init() {
		this.width=this.getWidth();
		this.height=this.getHeight();
		m=new model.Collision_Simulator(width, height, this);
		d=new Drawing_Panel(width,height);
		this.add(d);
		//img =new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Random rand= new Random();
		for(int i=rand.nextInt(20)+20;i>0;i--){
			m.addGameObject(new model.Sphere(25+rand.nextInt(width-25), 25+rand.nextInt(height-25), new Vector(rand.nextInt(200)-100,rand.nextInt(200)-100), 100));
		}
		
		
    }
	private TimerTask getLoopTask(){
		return new TimerTask(){
			public void run() {
				procTimer();}
		};
	}
	long lastTime=0;
	private void procTimer() {
		if(lastTime==0){
			lastTime=System.currentTimeMillis();
		}
		long time =System.currentTimeMillis();
		// TODO Auto-generated method stub
		m.advance((time-lastTime)/1000.);
		lastTime=time;
		m.updateView();
		this.repaint();
	}
	
	public void start(){
		timer=new Timer();
		timer.scheduleAtFixedRate(getLoopTask(),500, 20);
		//start timer for game loop, ect..
		
	}
	public void stop(){
		timer.cancel();
		//pause timer for rendering loop and/or gameloop
	}
	
//	publiv
	 public void paint(Graphics g) {
		 update(g);
		
		 //d.paint(g);
		 //g.drawImage(img, 0, 0, getWidth(), getHeight(),this);
         //g.drawString(""+System.currentTimeMillis(), 5, 15);
     }
	 public void update(Graphics g){
		 d.draw(g);
		 //getToolkit().sync();
//		 paint(g);
	 }

	@Override
	public void updateScreen(List<Renderable> rendered) {
		//System.out.println("updating screen");
		d.drawComponents(rendered);
		/*Graphics g=img.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		for(Renderable r:rendered){
			g.drawRect(r.getImageX(),r.getImageY(), r.getImageWidth(), r.getImageHeight());
		}*/
		
	}
	@Override
	public int getGameWith() {
		// TODO Auto-generated method stub
		return width;
	}
	@Override
	public int getGameHeight() {
		// TODO Auto-generated method stub
		return height;
	}
	
	
	

}
