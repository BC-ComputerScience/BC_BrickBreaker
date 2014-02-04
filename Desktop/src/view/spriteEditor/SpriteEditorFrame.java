package view.spriteEditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class SpriteEditorFrame extends JFrame{
	SpriteEditor s;
	public SpriteEditorFrame(){
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s=new SpriteEditor();
		this.add(s);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.pack();
		this.setResizable(false);
	}

	public void loadImage(File file) {
		s.loadImage(file);
		this.repaint();
		//this.setSize(s.getSize());
		//this.pack();
	}

	public void dumpXML() {
		s.dumpXML();
		// TODO Auto-generated method stub
		
	}
	

}
