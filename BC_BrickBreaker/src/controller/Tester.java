package controller;

import model.*;

import java.util.Random;

import mathematics.Vector;
import model.Collision_Simulator;
import model.Sphere;
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
		new LevelLoader("TestLevel/", sim);
		//lineCollisionTest();
	}
	
	public void lineCollisionTest(){
		
		/**/
		//this.addRandomCircles(10);
		//sim.addGameObject(new Line(200,100,500,405));
		//sim.addGameObject(new Line(500,405,100,200));
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
		/*int width=50, height=20;
		for(int i=width;i<800-width;i+=width){
			for(int j=height;j<400;j+=height){
				sim.addGameObject(new Brick(i,j,width,height));
			}
		}*/
		
		
		
		/**/
	}
	
	
	
	
	
	
	
	
	
	public void placeSphere(int x, int y,boolean isImmovable){
		Sphere s=new Sphere(x,y,new Vector(0,0),rand.nextInt(50)+50);
		s.setImmovable(isImmovable);
		sim.addGameObject(s);
	}
	public void placeSphere(int x, int y, int xvel, int yvel,boolean isImmovable){
		Sphere s=new Sphere(x,y,new Vector(xvel,yvel),rand.nextInt(50)+500);
		s.setImmovable(isImmovable);
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
