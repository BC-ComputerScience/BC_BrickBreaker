package controller;
import java.io.File;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import model.*;
import resources.LevelReader;
import trigger.*;

import java.util.Arrays;
import java.util.Random;

import mathematics.Vector;
import model.Collision_Simulator;
import model.gameObjects.Brick;
import model.gameObjects.Line;
import model.gameObjects.Sphere;
/**
 * 
 * @author Anthony Klobas
 * 
 * this class is currently used to load test cases into the the game
 * 
 */
public class Tester {
	Collision_Simulator sim;
	Random rand=new Random();
	
	
	
	public Tester(Collision_Simulator sim){
		this.sim=sim;
	}
	
	public void testCase(){
		loadTestLevel();
		/*URL url;
		try {
			url = new File("/Users/prog/git/classes.jar").toURI().toURL();
		// url= new URL("file://Users/prog/git/jar-file.jar");
			URL[] locations=new URL[]{url};
			URLClassLoader loader = new URLClassLoader(locations);
			//loader.
			//System.out.println(Arrays.toString(loader.getURLs()));
			Class cls =loader.loadClass("extensions.Sphere3");
			Constructor[] con=cls.getConstructors();
			Object o=con[0].newInstance(10,10,new Vector(10,10),100);
			
			System.out.println(Arrays.toString(cls.getConstructors()));
			Movable toAdd=(Movable)o;
			sim.addGameObject(toAdd);
			//cl.lo
			//Class cls=cl.loadClass();
		}catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Sphere s=new Sphere(200,570,new Vector(100,-100),30*30);
		sim.addGameObject(s);/**/
		
	}
	public void loadTestLevel(){
		//LevelReader ll=new LevelReader("level/");
		
		
		//GeneralTrigger t= new GeneralTrigger();
		//t.addCondition(new trigger.conditions.BlocksRemaining(sim,310,Relation.LESS));
		//t.addAction(new trigger.actions.SpawnBox(sim, ll.lastBrick));
		//sim.addTrigger(t);
		//lineCollisionTest();
		//boxCollisionTest();
		
	}
	
	
	public void boxCollisionTest(){
		Sphere s=new Sphere(200,570,new Vector(100,-100),30*30);
		//s.advance(-0.7085421901205575);
		//Line l=new Line(400,300,400,400);
		Brick l=new Brick(350,250,100,100,5);
		sim.addGameObject(s);
		sim.addGameObject(l);
	}
	public void lineCollisionTest(){
		sim.addGameObject(new Line(500,405,100,200,true));
		/**/
		//this.addRandomCircles(10);

		/*/sim.addGameObject(new Line(200,100,500,405));
		//sim.addGameObject(new Sphere(435.0,451.0, new Vector(94.0, -79.0),88.0));
>>>>>>> refs/heads/CollisionListFix
		sim.addGameObject(new Line(500,405,100,200));
		//sim.addGameObject(new Line(100,100,200,100));
		//sim.addGameObject(new Sphere(20,300, new Vector(200,0),100));
		//sim.addGameObject(new Sphere(780,300, new Vector(-200,0),100));
		//this.addRandomCircles(200);
		//sim.addGameObject(new Line(200,100,600,500));
		//sim.addGameObject(new Line(200,500,600,100));
		
		
		//sim.addGameObject(new Sphere(206.0, 108.0, new Vector(253,57).subtract(new Vector(206,108)).negate(),100));
		//Vector velocity= new Vector(-210.0, 292.0);
		//Sphere sphere=new Sphere(252.0,36.0,velocity,50.0);
		//sphere.advance(.2);
		//sim.addGameObject(sphere);
		//sim.addGameObject(new Sphere(34.0,234.0, new Vector(132.0, -83.0),84.0));
		/*/
		int offset=100;
		//sim.addGameObject(new Sphere(291.0,94.0+offset, new Vector(9.0, -48.0),82.0));
		int width=50, height=20;
		/*for(int i=width*5;i<800-width*9;i+=width){
			for(int j=height;j<=height;j+=height){
				sim.addGameObject(new Brick(i,j+offset,width,height));
			}
		}/**/
		//sim.addGameObject(new Sphere(776.0,453.0, new Vector(-71.0, -83.0),64.0));
		//sim.addGameObject(new Sphere(797.0,375.0, new Vector(-167.0, -1.0),63.0));
		
		/*for(int i=width;i<800-width;i+=width){
			for(int j=height*5;j<=400;j+=height){
				sim.addGameObject(new Brick(i,j,width,height));
			}
		}
		//sim.addGameObject(new Sphere(42.0,96.0, new Vector(37.0, 98.0),76.0));
		//sim.addGameObject(new Sphere(32.0,441.0, new Vector(106.0, -94.0),79.0));
		//sim.addGameObject(new Sphere(754.0,64.0, new Vector(-15.0, 148.0),98.0));
		
		/**/
	}
	
	
	
	
	
	
	
	
	
	public void placeSphere(int x, int y,boolean isImmovable){
		Sphere s=new Sphere(x,y,new Vector(0,0),rand.nextInt(50)+50);
		//s.setImmovable(isImmovable);
		sim.addGameObject(s);
	}
	public void placeSphere(int x, int y, int xvel, int yvel,boolean isImmovable){
		Sphere s=new Sphere(x,y,new Vector(xvel,yvel),rand.nextInt(50)+50);
		//s.setImmovable(isImmovable);
		sim.addGameObject(s);
	}
	
	
	public void addShpere(){
		this.addRandomCircles(1);
	}
	public void addNewtonsCradle(){
		Sphere s=new Sphere(50,100,new Vector(500,0),900);
		sim.addGameObject(s);
		
		
		for(int i=0;i<4;i++){
			s=new Sphere(200+120*i,100,new Vector(0,0),900);
			sim.addGameObject(s);
		}
	}
	
	public void addRandomCircles(int n){
		for(int i=0;i<n;i++){
			Sphere s=new Sphere(0,0,new Vector(rand.nextInt(200)-100,rand.nextInt(200)-100),rand.nextInt(200)+50 );
			int width=s.getBoundingWidth();
			int height=s.getBoundingHeight();
			s.setPos(new Vector(rand.nextInt(sim.getWidth()-width),rand.nextInt(sim.getHeight()-height)));
			sim.addGameObject(s);
		}
	}

}
