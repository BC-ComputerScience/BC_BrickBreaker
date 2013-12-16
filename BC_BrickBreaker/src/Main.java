import java.util.Random;

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
		new controller.Collision_Controller(800,600);
		//GenerateMap();
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
