import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SwingUtilities;

import consoletools.Shell;
import consoletools.ShellProgram;
import resources.LevelReader;
import resources.PC_ResourceLoader;
import view.Console;
import view.ConsoleListener;
import view.Renderable;
import view.spriteEditor.SpriteEditorFrame;
import mathematics.Vector;
import model.gameObjects.Brick2;
import model.gameObjects.Line;
import model.gameObjects.Sphere;

 /**
 * A starting point
 * 
 * this class should contain no logic, it should instantiate whatever object controls 
 * the application
 * 
 * @author Anthony Klobas
 *
 */
public class Main {

	public static void main(String[] args) {
		new controller.Collision_Controller(800,600);
		//editorTest();
		//new view.Console();
		//LevelReader ll=new LevelReader("level/", new PC_ResourceLoader());
		
		//new resources.LevelReader("/Users/prog/git/BC_BrickBreakerRepo/BC_BrickBreaker/level/");
		//GenerateMap();
		//test();
		
		
	}
	public static void editorTest(){
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	Shell s=new Shell(new LittleConsole());
        		SpriteEditorFrame frame=new SpriteEditorFrame();
        		s.addCommand("load", new LoadImage(frame));
        		s.addCommand("xml", new dumpXML(frame));
            }
        });
		
		
		
	}
	
	
	
	private static class LoadImage implements ShellProgram{
		SpriteEditorFrame f;
		public LoadImage(SpriteEditorFrame f){
			this.f=f;
		}
		@Override
		public int Execute(Shell s, String... args) {
			if(args.length>0){
				File file=new File(s.currentDirectory().getAbsolutePath()+"/"+args[0]);
				s.out.println("loading"+file.getAbsolutePath());
				f.loadImage(file);
				return 0;
			}
			s.out.println("no file specified");
			return 1;
		}
	}
	private static class dumpXML implements ShellProgram{
		SpriteEditorFrame f;
		public dumpXML(SpriteEditorFrame f){
			this.f=f;
		}
		@Override
		public int Execute(Shell s, String... args) {
			f.dumpXML();
			return 0;
		}

	}
	
	private static class LittleConsole implements Console{
		@Override
		public PrintStream out() {
			return System.out;
		}

		@Override
		public InputStream in() {
			return System.in;
		}
		public PrintStream err(){
			return System.err;
		}
		

		@Override
		public void addConsoleListener(ConsoleListener cl) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void removeConsoleListener(ConsoleListener cl) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
