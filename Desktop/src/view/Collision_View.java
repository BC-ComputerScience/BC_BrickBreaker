package view;

import java.awt.Component;
import java.awt.MenuBar;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.Collision_Controller;


public class Collision_View implements view.View{
	
	JFrame f;
	Drawing_Panel panel;
	int width,height;
	Collision_Controller controller=null;
	private boolean isMade=false;


    public Collision_View(int width, int height, Collision_Controller c) {
		this.width=width;
		this.height=height;
		controller=c;
		
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
		
		while(!isMade){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				
			}
		}

		
	}
    JMenuBar menuBar;
    JMenu fileMenu,editMenu;
    ArrayList<JMenuItem> menuItems=new ArrayList<JMenuItem>();
    

	private void createAndShowGUI() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
        System.out.println("Created GUI on EDT? "+
        SwingUtilities.isEventDispatchThread());
        f = new JFrame("Collision Simulaton");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        panel=new Drawing_Panel(width,height);
        f.add(panel);
       
        menuBar = new JMenuBar();
      //Build the first menu.
        fileMenu = new JMenu("File");
        editMenu = new JMenu("Edit");
        JMenuItem temp;
        String[] fileItems=new String[]{"New", "Open", "Save", "Quit"};
        for(String s:fileItems){
        	temp=new JMenuItem(s);
        	fileMenu.add(temp);
        	menuItems.add(temp);
        	
        }
        
        String[] cutpasteItems=new String[]{"Cut", "Copy", "Paste"};
        for(String s:cutpasteItems){
        	temp=new JMenuItem(s);
        	editMenu.add(temp);
        	menuItems.add(temp);
        	
        }
        editMenu.addSeparator();
        
        String[] editItems=new String[]{"Pause", "Reset","Set MillisPerFrame","Set Gravity","Set COR", "Add Cradle","Add Random","Add Large Random","Add CurrentTest"};
        for(String s:editItems){
        	temp=new JMenuItem(s);
        	if(s=="Pause"){
        		temp.setMnemonic('p');
        	}
        	
        	editMenu.add(temp);
        	menuItems.add(temp);
        	
        }
        
        
        
       
        f.setJMenuBar(menuBar);
        
        /**/
        
        
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        f.pack();
        isMade=true;
        f.setVisible(true);
    } 

	
	public void updateScreen(List<Renderable> toRender){
		if(panel==null)return;
		panel.drawComponents(toRender);
		f.repaint();
	}

	public void addMouseListener(MouseListener controller) {
		//f.addMouseListener(controller);
		panel.addMouseListener(controller);
		
	}

	public void addMouseMotionListener(MouseMotionListener controller) {
		//f.addMouseMotionListener(controller);
		panel.addMouseMotionListener(controller);
	}
	public void addActionListener(ActionListener a){
		for(JMenuItem j:menuItems){
			j.addActionListener(a);
		}
	}
	public void addKeyListener(KeyListener controller){
		f.addKeyListener(controller);
	}


	public void move() {
		if(f!=null)
		f.repaint();
		// TODO Auto-generated method stub
		
	}

}
