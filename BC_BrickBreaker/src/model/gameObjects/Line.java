package model.gameObjects;


import resources.SpriteSet;
import view.Renderable;
import view.Sprite;
import mathematics.Matrix;
import mathematics.Vector;

/**
 * A line that spheres can bounce off of
 * 
 * @author Anthony Klobas
 * 
 */
public class Line extends Collidable implements Renderable {
	protected Vector normal, p1, p2;
	private int x, y, width, height;
	private SpriteSet spriteSet=new SpriteSet();
	protected double length;
	private boolean isOneWay;

	public Line(Vector p1, Vector p2,boolean isOneWay){
		this(p1.getElement(0), p1.getElement(1), p2.getElement(0), p2
				.getElement(1), isOneWay);
	}
	public Line(Vector p1, Vector p2) {
		this(p1,p2,false);
	}
	public Line(double x1, double y1, double x2, double y2){
		this(x1,y1,x2,y2,false);
	}
	public Line(double x1, double y1, double x2, double y2, boolean isOneWay) {
		//super(null,null,null,null);
		super(null, 1,1);
		this.isOneWay=isOneWay;
		p1 = new Vector(x1, y1);
		p2 = new Vector(x2, y2);
		length = p1.distance(p2);

		x = (int) Math.min(x1, x2);
		y = (int) Math.min(y1, y2);
		width = (int) Math.max(x1, x2) - x;
		height = (int) Math.max(y1, y2) - y;
		this.normal = new Vector(y1 - y2, -1 * (x1 - x2)).getUnitVector();
	}

	@Override
	public void advance(double seconds) {
		// TODO add code for updating graphics

	}

	@Override
	public boolean canCollideWith(Collidable c) {
		// lines only know how to collide with spheres
		return (c instanceof Sphere);
	}

	@Override
	public boolean collide(Collidable c) {
		return collide(c, false);

	}
	@Override
	public double collideTime(Collidable c) {
		if (c instanceof Sphere) {
			Sphere s= (Sphere)c;
			Matrix toBase = Matrix.createOrthonormal(normal);
			double height = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double traj = toBase.apply(s.getTrajectory()).getElement(0);
			
			
			//double linetime=(Math.min(Math.abs((s.getRadius() - height) / traj),Math.abs(( -height-s.getRadius()) / traj) ));
			
			double p1time=Integer.MAX_VALUE,p2time=Integer.MAX_VALUE;
			Matrix pointBasis = Matrix.createOrthonormal(s.getTrajectory().getPerpendicular());
			
			double p1height = pointBasis.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double p2height = pointBasis.apply(s.getCenter().subtract(this.p2))
					.getElement(0);
			double radius=s.getRadius();
			
			if(Math.abs(p1height) < s.getRadius()){
				double h=(pointBasis.apply(s.getCenter().subtract(this.p1))
						.getElement(1));
				double y=0;
				double k=pointBasis.apply(s.getCenter().subtract(this.p1))
						.getElement(0);
				double r=radius;
				double det=Math.sqrt(r*r-(y-k)*(y-k));
				p1time=((det<0?det:-det)+h)/s.getTrajectory().getLength();
				
			}
			if(Math.abs(p2height) < s.getRadius()){
				double h=(pointBasis.apply(s.getCenter().subtract(this.p2))
						.getElement(1));
				double y=0;
				double k=pointBasis.apply(s.getCenter().subtract(this.p2))
						.getElement(0);
				double r=radius;
				double det=Math.sqrt(r*r-(y-k)*(y-k));
				
				p2time=((det<0?det:-det)+h)/s.getTrajectory().getLength();
			}
			
			//double time =0;
			//System.out.println(""+linetime+","+p1time+","+p2time);
			
			//linetime<
			
			//double Time= Math.min( (s.getRadius()-s.getCenter().distance(p1))/s.getTrajectory().distance(new Vector(0,0)));
			//System.out.println("time="+Math.max(Math.max((p1time), (p2time)), (linetime)));
			double linetime=(Math.min(((s.getRadius() - height) / traj),(( -height-s.getRadius()) / traj) ));
			
			
			if(linetime<Integer.MAX_VALUE&&linetime>Integer.MIN_VALUE){
				s.advance(linetime);
				double x = toBase.apply(s.getCenter().subtract(this.p1))
						.getElement(1);
				s.advance(-linetime);
				if(!(x <= 0 && -length <= x)){
					linetime=Integer.MAX_VALUE;
				}
			}
			
			
			
			double time=Math.min(Math.min((p1time), (p2time)), (linetime));
			
			//System.out.println("selected:"+time+"\tp1: "+p1time+"\tp2: "+p2time+"\tline: "+linetime);
			//System.out.println("height"+height+",\t"+p1+""+p2);

			return time;
			
		}
		return Integer.MAX_VALUE;
	}

	public double kp(double d){
		return d<0?d:-655;
	}
	//@Override
	public boolean collide(Collidable c, boolean ignorePosition) {
		if (c instanceof Sphere) {
			
			Sphere s = (Sphere) c;

			Matrix toBase = Matrix.createOrthonormal(normal);
			double height = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(0);
			double traj = toBase.apply(s.getTrajectory()).getElement(0);
			double x = toBase.apply(s.getCenter().subtract(this.p1))
					.getElement(1);
			
			if (Math.abs(height) <= s.getRadius()||ignorePosition){
				
				
				Matrix pointBasis = Matrix.createOrthonormal(s.getTrajectory().getPerpendicular());
				
				double p1height = pointBasis.apply(s.getCenter().subtract(this.p1))
						.getElement(0);
				double p2height = pointBasis.apply(s.getCenter().subtract(this.p2))
						.getElement(0);
				double p1time=Integer.MAX_VALUE;
				double p2time=Integer.MAX_VALUE;
				
				double radius=s.getRadius();
				if(Math.abs(p1height) < s.getRadius()){
					double h=(pointBasis.apply(s.getCenter().subtract(this.p1))
							.getElement(1));
					double y=0;
					double k=pointBasis.apply(s.getCenter().subtract(this.p1))
							.getElement(0);
					double r=radius;
					double det=Math.sqrt(r*r-(y-k)*(y-k));
					p1time=((det<0?det:-det)+h)/s.getTrajectory().getLength();
					
				}
				if(Math.abs(p2height) < s.getRadius()){
					double h=(pointBasis.apply(s.getCenter().subtract(this.p2))
							.getElement(1));
					double y=0;
					double k=pointBasis.apply(s.getCenter().subtract(this.p2))
							.getElement(0);
					double r=radius;
					double det=Math.sqrt(r*r-(y-k)*(y-k));
					
					p2time=((det<0?det:-det)+h)/s.getTrajectory().getLength();
				}
				
				
				
				
				double linetime=(Math.min(((s.getRadius() - height) / traj),(( -height-s.getRadius()) / traj) ));
				
				
				
				double time=Math.min((p1time), (p2time));
				
				if(linetime<Integer.MAX_VALUE&&linetime>Integer.MIN_VALUE){
					s.advance(linetime);
					x = toBase.apply(s.getCenter().subtract(this.p1))
							.getElement(1);
					s.advance(-linetime);
					if(!(x <= 0 && -length <= x)){
						linetime=Integer.MAX_VALUE;
					}else{
						time=linetime;
						this.lineBounce(s, traj, time);
						return true;
					}
				}
				
				
				if(time<=0||ignorePosition){
					
					if (p2time==time&&time!=Integer.MAX_VALUE) {
						this.pointBounce(s, p2, time);
						return true;
					}else if (p1time==time&&time!=Integer.MAX_VALUE) {
						this.pointBounce(s, p1, time);
						return true;
					}else {
						System.out.println("!!!!!!!!!!!!nohit!!!!!!!!!!!");
						System.out.println(time+ "times :"+linetime+","+p1time+","+p2time);
						System.out.println("x:"+x+"length"+length);
						System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
						//s.setTrajectory(new Vector(0,0));
						return false;
						//System.out.println("collision not p1:"+p1.distance(s.getCenter())+"    p2:"+p2.distance(s.getCenter()));
					}
					//s.setTrajectory(new Vector(0,0));
					
				}

			}
		}
		
		return false;
	}
	
	private double getPointCollisionTime(){
		return 0;
	}
	
	
	private void lineBounce(Sphere s, double traj, double time){
		//System.out.println("Line Collision");
		s.advance(time);
		if (traj > 0) {
			if(!this.isOneWay)s.reflect(normal.negate());
		} else {
			s.reflect(normal);
		}
		s.advance(-time);
	}
	private void pointBounce(Sphere s,Vector p,double time){
		//System.out.println("Point collision");
		s.advance(time);
		//System.out.println(s.getCenter().subtract(p));
		s.reflect(s.getCenter().subtract(p));
		s.advance(-time);
		
	}
	
	
	
	

	@Override
	public int getCollisionPrecedence() {
		return 2;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public int getBoundingHeight() {

		return height;
	}

	@Override
	public int getBoundingWidth() {
		return width;
	}

	@Override
	public Sprite getImage() {
		return spriteSet.currentSprite();
	}

	@Override
	public int getImageX() {
		return (int) this.x;
	}

	@Override
	public int getImageY() {
		return (int) this.y;
	}
	public boolean stillExists() {
		return true;
	}
	public String toString(){
		return this.getClass().getName()+p1+p2;
	}
	@Override
	public int getImageWidth() {
		// TODO Auto-generated method stub
		return this.width;
	}
	@Override
	public int getImageHeight() {
		// TODO Auto-generated method stub
		return this.height;
	}
	public void addSpriteSet(SpriteSet set) {
		this.spriteSet=set;
		
	}
	@Override
	public Line cloneAt(int x, int y) {
		return cloneAt(new Vector(x,y));
	}
	@Override
	public Line cloneAt(Vector v) {
		Line ret=new Line(p1.add(v),p2.add(v),this.isOneWay);
		ret.addSpriteSet(spriteSet.clone());
		return ret;
	}
	@Override
	public void translate(int deltaX, int deltaY) {
		translate(new Vector(deltaX, deltaY));
		
	}
	@Override
	public void translate(Vector v) {
		p1=p1.add(v);
		p2=p2.add(v);
		this.x=(int)Math.min(p1.getElement(0), p2.getElement(0));
		this.y=(int)Math.min(p1.getElement(1), p2.getElement(1));		
	}

}
