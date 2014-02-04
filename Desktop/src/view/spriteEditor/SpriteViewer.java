package view.spriteEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import resources.*;
import view.Sprite;
import view.spriteEditor.selection.SelectionBox;
import view.spriteEditor.selection.SelectionList;

public class SpriteViewer extends JPanel{
	BufferedImage image;
	private PC_ImageResource imageres;
//	JPanel viewPort;
	//ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	SelectionList list=new SelectionList();
	public SpriteViewer(File f) throws IOException{
		try{
			imageres=new PC_ImageResource(f.getCanonicalPath());
			image=imageres.getImage();
		}catch (IOException e){
			System.err.println("error loading "+f.getAbsolutePath());
			throw e;
		}
		Dimension imageDim=new Dimension(image.getWidth(),image.getHeight());
		this.setBackground(Color.DARK_GRAY);
		this.setMinimumSize(imageDim);
		this.setPreferredSize(imageDim);
		this.setSize(imageDim);
		
	}
	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(-1, -1, image.getWidth(), image.getHeight());
        g.drawImage(image, 0, 0, this);
        g.setColor(Color.RED);
        for(SelectionBox s:list){
        	s.draw(g);
        	//g.drawRect(s.getSheetX(), s.getSheetY(), s.getSheetWidth(), s.getSheetHeight());
        }
        
    }
	public void addSprite(Sprite s){
		list.add(new SelectionBox(s));
	}
	
	public boolean pressed(int x, int y){
		return list.pressed(x,y);
	}
	public boolean released(int x, int y){
		return list.released(x,y);
	}
	public void dragged(int x, int y) {
		list.dragged(x,y);
		// TODO Auto-generated method stub
		
	}
	public ImageResource getResource(){
		return this.imageres;
	}
	
}
