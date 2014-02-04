package view.spriteEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import resources.PC_ImageResource;
import resources.PC_Sprite;
import view.Sprite;

public class SpriteEditor extends JPanel implements MouseListener, TableModelListener, MouseMotionListener{
	JTabbedPane tabbedPane;
	SpriteProperties sp;
	int imageIndex=1;
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
	HashMap <String, SpriteViewer> map= new HashMap<String, SpriteViewer>();
	
	public SpriteEditor(){
		
		tabbedPane = new JTabbedPane();
		Dimension d=new Dimension(800,600);
		tabbedPane.setMaximumSize(d);
		tabbedPane.setMinimumSize(d);
		tabbedPane.setPreferredSize(d);
		sp=new SpriteProperties();
		sp.addTableModelListener(this);
		this.setLayout(new BorderLayout());
		
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(sp, BorderLayout.SOUTH);
		
		
	}

	public void loadImage(File file) {
		try {
			String key=file.getCanonicalPath();
			if(!map.containsKey(key)){
			SpriteViewer temp=new SpriteViewer(file);
			
			temp.setName(key);
			map.put(key, temp);
			temp.addMouseListener(this);
			temp.addMouseMotionListener(this);
			JPanel center=new JPanel();
			center.setLayout(new FlowLayout());
			center.setBackground(Color.DARK_GRAY);
			center.add(temp);
			tabbedPane.addTab(file.getName(), new JScrollPane(center));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	int x; int y;
	@Override
	public void mouseClicked(MouseEvent m) {
		
		//System.out.println(arg0.getX()+","+arg0.getY());
		
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	boolean selecting=false;
	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		x=m.getX();
		y=m.getY();
		selecting = map.get(m.getComponent().getName()).pressed(x, y);
		
	}
	@Override
	public void mouseReleased(MouseEvent m) {
		
		if(!map.get(m.getComponent().getName()).released(m.getX(), m.getY())){
			SpriteViewer viewer = map.get(m.getComponent().getName());
			
			//make a sprite
			Sprite s=new PC_Sprite(Math.min(x, m.getX()),Math.min(y, m.getY()),
					Math.max(x, m.getX())-Math.min(x, m.getX()),Math.max(y, m.getY())-Math.min(y, m.getY()),(PC_ImageResource)viewer.getResource());
			//System.err.println(s.toXml());
			this.sprites.add(s);
			sp.addSprite(s);
			
			
			map.get(m.getComponent().getName()).addSprite(s);
			
			System.out.println(m.getX()+","+m.getY()+" in "+m.getComponent().getName());
		}else{
		}
		
		this.repaint();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent m) {
		map.get(m.getComponent().getName()).dragged(m.getX(), m.getY());
		this.repaint();
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void dumpXML() {
		for(Sprite s: sprites){
			System.out.println(s.toXml());
		}
		
	}

}
