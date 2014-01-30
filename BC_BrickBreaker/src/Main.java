import java.io.InputStream;
import java.io.PrintStream;

import consoletools.Shell;
import view.Console;
import view.ConsoleListener;

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

	public static String[] bricks= new String[]{
		"greyBrick1",
		"greyBrick2",
		"greyBrick3",
		"greyBrick4",
		"greyBrick5",
		"greyBrick6",
		"greyBrick7",
		"greyBrick8",
		"greyBrick9",
		//"greyBrick10",
		//"tealBrick1",
		"tealBrick2",
		"tealBrick3",
		//"greenBrick1",
		"greenBrick2",
		"greenBrick3",
		//"brownBrick1",
		"brownBrick2",
		"brownBrick3",
		//"blueBrick1",
		"blueBrick2",
		"blueBrick3",
		//"purpleBrick1",
		"purpleBrick2",
		"purpleBrick3",
		};

	public static void main(String[] args) {
		System.out.println("Starting test program(console only)");
		new Shell(new Console(){

			@Override
			public PrintStream out() {
				// TODO Auto-generated method stub
				return System.out;
			}

			@Override
			public InputStream in() {
				// TODO Auto-generated method stub
				return System.in;
			}

			@Override
			public void addConsoleListener(ConsoleListener cl) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void removeConsoleListener(ConsoleListener cl) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//new controller.Collision_Controller(800,600);
		
		//new resources.LevelReader("/Users/prog/git/BC_BrickBreakerRepo/BC_BrickBreaker/level/");
		//GenerateMap();
		//test();
		
		
	}

}
