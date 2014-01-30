package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JPanel;

import resources.PC_Sprite;

public class Drawing_Panel extends JPanel implements MouseListener{
	private int width,height;
	
	private BufferedImage[] image;
	private int imageLength=10;
	private int currentImage=1;
	int offset=60;
	int barHeight=0;
	//int x;int y;
	public Drawing_Panel(int x, int y){
		this.addMouseListener(this);
		//this.x=x;
		//this.y=y;
		y+=barHeight;
		this.width=x;
		this.height=y;
		this.setPreferredSize(new Dimension(x,y));
		image=new BufferedImage[imageLength];
		for(int i=0;i<imageLength;i++){
			image[i]=new BufferedImage(x+2*offset,y+2*offset,BufferedImage.TYPE_INT_ARGB);
		}
		
	}
	
	public void drawComponents(List<Renderable> toRender){
		
		int num=currentImage+1;
		Graphics2D g=image[num%imageLength].createGraphics();
		g.clearRect(0, 0, width+2*offset, height+2*offset);
		boolean firstPass=true;
		//this.addTimeStamp(g, false);
		for(Renderable r:toRender){
			//XXX in the PC version, the sprites should all be pc sprites
			PC_Sprite s=(PC_Sprite)r.getImage();
			if(s==null&&!(r instanceof model.Background)){
				s=new PC_Sprite(0,0,r.getImageWidth(),r.getImageHeight());
			}
			if(s!=null)
			if(firstPass){
				g.drawImage(s.getImage(), r.getImageX()+offset, r.getImageY()+offset,r.getImageWidth(),r.getImageHeight(),/**/ null);
				firstPass=false;
			}else{
				g.drawImage(s.getImage(), r.getImageX()+offset, r.getImageY()+offset+barHeight,r.getImageWidth(),r.getImageHeight(),/**/ null);
			}
			
			
		}
		
		currentImage=num;
		objectsRendered=toRender.size();
	}
	
	
	
	/*private double x,y;
	private double xv=100,yv=150;
	private int bs=21;
	private long lastProc=System.currentTimeMillis();
	*/
	private Queue<Long> q=new LinkedBlockingQueue<Long>();
	double frameAverage=10;
int objectsRendered=0;
	private int run=0;
	
	public void draw(Graphics g){
		 
	        
	        //mspf+=time-lastTime;
			boolean resized=(this.getWidth()!=width||this.getHeight()!=height);
	        this.addTimeStamp(image[currentImage%imageLength].createGraphics(), resized);
	        
	        if(resized){
	        	g.drawImage(image[currentImage%imageLength], 0, 0,this.getWidth(),this.getHeight(),offset,offset,width+offset,height+offset, null);
	        
	        }else{
	        	g.drawImage(image[currentImage%imageLength],-offset,-offset, this);
	        }
	        
	}
	private void addTimeStamp(Graphics g,boolean resized){
		long time=System.currentTimeMillis();
		q.add(time);
        Long lastTime=time;
        if(q.size()>frameAverage){
        	
        	lastTime=q.remove();
        }
		//if(lastTime!=time){
        Date d=new Date(time);
        String toDisplay;
        try{
        	//System.out.println("time:"+time+"\tlasttime"+lastTime+"\tdiff"+(time-lastTime)+"\tsize:"+q.size());
        	
            toDisplay=time+" ("+Math.round(1/(((time-lastTime)/frameAverage)/10000.))/10.+" fps) objects rendered="+this.objectsRendered;   
        }catch (NullPointerException e){
        	System.err.println("time "+time);
        	System.err.println("lastTime "+lastTime);
        	System.err.println("this.objectsRendered "+this.objectsRendered);
        	System.err.println("frameAverage "+frameAverage);
        	throw e;
        }
        
        if(resized){
        	toDisplay+=" (resized)";
        }
        g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
        g.setColor(Color.black);
        g.fillRect(0, 0, ((int)(toDisplay.length()*9)+10+offset), 20+offset);
        g.setColor(Color.GREEN);
        g.drawString(toDisplay,5+offset,15+offset);
		
	}
	
	protected void paintComponent(Graphics g) {
		//System.out.println(Thread.currentThread().getName());
        super.paintComponent(g);       
        this.draw(g);
    }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.requestFocus();
		
	}
	

}
