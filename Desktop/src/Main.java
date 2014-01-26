import java.util.ArrayList;
import java.util.Random;

import resources.LevelReader;
import resources.PC_ResourceLoader;
import view.Renderable;
import mathematics.Vector;
import model.Brick;
import model.Line;
import model.Sphere;

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
		//LevelReader ll=new LevelReader("level/", new PC_ResourceLoader());
		
		//new resources.LevelReader("/Users/prog/git/BC_BrickBreakerRepo/BC_BrickBreaker/level/");
		//GenerateMap();
		//test();
		
		
	}
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

	
	public static void test(){
		//view.Collision_View view =new view.Collision_View(800, 600, null);
		Sphere s=new Sphere(400,570,new Vector(0,-100),30*30);
		//s.advance(-0.7085421901205575);
		//Line l=new Line(400,300,400,400);
		Brick l=new Brick(350,250,100,100,5);
		Brick b=new Brick(0,0,800,600);
		s=new Sphere(200,570,new Vector(100,-100),30*30);
		
		ArrayList<Renderable>torender= new ArrayList<Renderable>();
		torender.add(b);
		
		torender.add(l);
		torender.add(s);
		//view.updateScreen(torender);
		boolean hasCollided=false;
		while(true){
			wait(50);
			
			s.advance(.04);
			double d=l.collideTime(s);
			if(d<0){
				if(!hasCollided){
					hasCollided=l.collide(s);
				}else{
					l.collide(s);
			}
			}
			//view.updateScreen(torender);
		//break;
		}
	}
	
	
	public static void wait(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void GenerateMap(){
		Random rand=new Random();
		int width=32;int height=16;
		int topOffset=50;
		int bottomOffset=300;
		int leftOffset=50;
		int rightOffset=50;
		int screenWidth=800;
		int screenHeight=600;
		
		for(int x=rightOffset;x<screenWidth-leftOffset-width;x+=width){
			for(int y=topOffset;y<screenHeight-bottomOffset-height;y+=height){
				System.out.println("<Brick> <spriteset health=\"0\">" +bricks[rand.nextInt(bricks.length)]+"</spriteset><health>1</health><y>"+y+"</y><x>"+x+"</x><width>32</width><height>16</height></Brick>");	
			}
		}
		
		
	}

}
