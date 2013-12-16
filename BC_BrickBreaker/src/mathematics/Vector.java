package mathematics;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Anthony Klobas
 * 
 * vector is currently an immutable type.
 *
 */


public class Vector {
	//TODO check if vectors are same length;
	protected double vector[];
	
	public Vector(int length){
		vector= new double[length];
	}
	
	public Vector(double... field){
		this.vector=new double[field.length];
		System.arraycopy(field, 0, this.vector, 0, field.length);
		
	}

	
	private void compareOrThrow(Vector v){
		if(this.vector.length!=v.vector.length){
			throw new IllegalArgumentException("invalid dimensions :"+this.vector.length +","+v.vector.length);
		}
	}
	
	
	public int getDimension(){
		return vector.length;
	}
	public int getSize(){
		return vector.length;
	}
	
	public Vector scale(double s){

		Vector ret=new Vector(this.vector);
		for(int i=vector.length-1;i>=0;i--){
			ret.vector[i]*=s;
		}
		return ret;
	}
	
	public Vector add(Vector v){
		this.compareOrThrow(v);
		Vector ret=new Vector(this.vector);
		for(int i=vector.length-1;i>=0;i--){
			ret.vector[i]+=v.vector[i];
		}
		return ret;
	}
	public Vector subtract(Vector v){
		this.compareOrThrow(v);
		Vector ret=new Vector(this.vector);
		for(int i=vector.length-1;i>=0;i--){
			ret.vector[i]-=v.vector[i];
		}
		return ret;
	}
	public Vector negate(){
		Vector ret=new Vector(this.vector);
		for(int i=vector.length-1;i>=0;i--){
			ret.vector[i]*=-1;
		}
		return ret;
	}
	public double dotProduct(Vector v){
		this.compareOrThrow(v);
		double product=0;
		for(int i=vector.length-1;i>=0;i--){
			product += this.vector[i]*v.vector[i];
		}
		return product;
	}
	public double getLength(){
		return Math.sqrt(dotProduct(this));
	}
	public double getAngle(Vector v){
		this.compareOrThrow(v);
		//dot-product == |a|*|b|*cos(theta)
		return Math.acos(this.dotProduct(v)/(this.getLength()*v.getLength()));
	}
	public Vector getUnitVector(){
		return this.scale(1.0/this.getLength());
	}
	public double getElement(int n){
		return this.vector[n];
	}
	public String toString(){
		return Arrays.toString(vector);
	}
	public String coordinateList(){
		String ret=""+vector[0];
		for(int i=1;i<vector.length;i++){
			ret+=", "+vector[i];
		}
		return ret;
	}
	public double distance(Vector pos) {
		return this.subtract(pos).getLength();
	}
	public static Vector sumVectors(List<Vector> forces) {
		
		if(forces.size()>0){
			int length=forces.get(0).vector.length;
			Vector ret=new Vector(length);
			for(Vector v:forces){
				for(int i=0;i<length;i++){
					ret.vector[i]+=v.vector[i];
				}
				
			}
			return ret;
		}
		return null;
	}
	public Vector getPerpendicular(){
		if(this.getDimension()!=2){
			//only know how to do this for 2d vectors
			throw new UnsupportedOperationException();
		}
		return new Vector(-vector[1],vector[0]); 
	}
	
	
	
	/*public Vector getPerpendicular(Vector v){
		
		double tx=this.x/v.x;
		double ty=
		
		
		
	}*/
	/*
	public double determinant(Vector v){
		return this.x*v.y-this.y*v.x;
	}
	
	
	public Vector Reflect(Vector v){
		Vector i=this.scale(1/this.getLength());
		Vector j=v.scale(1/v.getLength());
		
		//double tempx=this.x
		return i;
	}*/
	
	
	
}
