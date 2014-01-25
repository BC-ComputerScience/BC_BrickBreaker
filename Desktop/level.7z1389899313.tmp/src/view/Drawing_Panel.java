package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.JPanel;

import resources.PC_Sprite;

public class Drawing_Panel extends JPanel{
	private int width,height;
	
	private BufferedImage[] image;
	private int imageLength=10;
	private int currentImage=1;
	int offset=60;
	int barHeight=0;
	int x;int y;
	public Drawing_Panel(int x, int y){
		this.x=x;
		this.y=y;
		y+=barHeight;
		this.width=x;
		this.height=y;
		this.setPreferredSize(new Dimension(x,y));
		image=new BufferedImage[imageLength];
		for(int i=0;i<imageLength;i++){
			image[i]=new BufferedImage(x+200,y+200,BufferedImage.TYPE_INT_ARGB);
		}
		
	}
	
	public void drawComponents(List<Renderable> toRender){
		
		int num=currentImage+1;
		Graphics2D g=image[num%imageLength].createGraphics();
		g.clearRect(0, 0, x+200, y+200);
		boolean firstPass=true;
		for(Renderable r:toRender){
			//XXX in the PC version, the sprites should all be pc sprites
			PC_Sprite s=(PC_Sprite)r.getImage();
			if(s==null&&!(r instanceof model.Background)){
				s=new PC_Sprite(0,0,r.getImageWidth(),r.getImageHeight());
			}
			if(s!=null)
			if(firstPass){
				g.drawImage(s.getImage(), r.getImageX()+offset, r.getImageY()+offset,r.getImageWidth(),r.getImageHeight(), null);
				firstPass=false;
			}else{
				g.drawImage(s.getImage(), r.getImageX()+offset, r.getImageY()+offset+barHeight,r.getImageWidth(),r.getImageHeight(), null);
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
	private Queue<Long> q=new LinkedList<Long>();
	double frameAverage=20;
int objectsRendered=0;
	private int run=0;
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
        long time=System.currentTimeMillis();
        
        //mspf+=time-lastTime;
        q.add(time);
        Long lastTime=time;
        if(q.size()>=frameAverage){
        	
        	lastTime=q.remove();
        }
        g.drawImage(image[currentImage%imageLength], -offset, -offset, null);
        //if(lastTime!=time){
        Date d=new Date(time);
        String toDisplay=d+" ("+Math.round(1/(((time-lastTime)/frameAverage)/10000))/10.+" fps) objects rendered="+this.objectsRendered;   
        g.setFont(new Font(Font.MONOSPACED,Font.PLAIN,15));
	        
	        g.setColor(Color.black);
	        g.fillRect(0, 0, ((int)(toDisplay.length()*9)+10), 20);
	        g.setColor(Color.GREEN);
	        g.drawString(toDisplay,10,15);
    }
	

}
