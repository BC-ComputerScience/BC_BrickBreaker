package mathematics;

import java.util.Arrays;

/**
 * 
 * @author Anthony Klobas
 * 
 * This class is immutable and may reduce performance if too large
 * all operations (assuming square matrices) have at least a C n^2
 * 
 */
public class Matrix {
	
	
	
	
	
	
	public static Matrix createOrthonormal(Vector v){
		return createOrthoganol(v.getUnitVector());
	}
	public static Matrix createOrthoganol(Vector v){
		Matrix ret=new Matrix(v.getSize());
		int size=v.vector.length;
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				ret.matrix[i][j]=v.vector[(j+i)%size];
			}
		}
		if(size==2){
			ret.matrix[0][0]=v.vector[0];
			ret.matrix[1][0]=v.vector[1];
			ret.matrix[0][1]=-v.vector[1];
			ret.matrix[1][1]=v.vector[0];
		}
		ret.isOrthogonal=true;
		return ret;
		
	}
	//note dimention 1
	private boolean isOrthogonal;
	private double[][] matrix;
	private int rows, columns;
	/*
	 * matrixes are stored as it relates to standard notation m[row][column];
	 * example:
	 * new double[][]
	 * {
	 *  {1,2,3}
	 *  {4,5,6}
	 *  {7,8,9}
	 * }
	 */
	/**
	 * create new identity matrix that is nxn
	 */
	public Matrix(int n){
		matrix=new double[n][n];
		for(int i=0;i<n;i++){
			matrix[i][i]=1;
		}
		this.rows=n;
		this.columns=n;
	}
	
	/**
	 * 
	 * @param matrix a prebuild matrix to be copied
	 */
	public Matrix(double [][] matrix){
		columns=matrix[0].length;
		rows=matrix.length;
		for(int i=0;i<rows;i++){
			System.arraycopy(matrix[i], 0, this.matrix[i], 0, columns);
		}
	}
	/**
	 * 
	 * @param vectors a number of column vectors
	 */
	public Matrix(Vector... vectors){
		columns=vectors.length;
		rows=vectors[0].getSize();
		for(int row=0;row<rows;row++){
			for(int column=0;column<columns;column++){
				matrix[row][column]=vectors[column].vector[row];
			}
		}
		
	}
	/**
	 * applies this matrix as a function to a vector and returns the result
	 *  Vector a, matrix M, return aM
	 * 
	 * @param v
	 * @return
	 */
	public Vector apply(Vector v){
		double[] ret=new double[columns];
		
		for(int row=0;row<rows;row++){
			ret[row]=0;//can never be too safe
			
			for(int column=0;column<columns;column++){
				
				ret[row]+=v.vector[column]*matrix[column][row];
			}
		}
		Vector retr=new Vector(ret);
		return retr;
	}
	public Matrix transpose(){
		Matrix ret=new Matrix(this.columns);
		for(int row=0;row<rows;row++){
			for(int column=0;column<columns;column++){
				ret.matrix[row][column]=this.matrix[column][row];
			}
		}
		return ret;
		
		
	}
	/**
	 * returns an inverse of the matrix, only works with orthoganol matracies 
	 * @return an inverse of this matrix
	 */
	public Matrix invert(){
		if(columns!=rows){
			throw new UnsupportedOperationException("inverse of a "+columns+"by"+rows+"matrix is not defined");
		}else if(this.isOrthogonal||true){
			return this.transpose();
		}/**/else if (columns==2){
			double a=matrix[1][1];
			double b=matrix[1][2];
			double c=matrix[2][1];
			double d=matrix[2][2];
			double det=a*d-b*c;
			if(det==0){
				throw new UnsupportedOperationException("non invertable matrix");
			}
			det=1/det;
			return new Matrix(new double[][]{
					{d*det  , (0-b)*det},
					{0-c*det, a*det    }
			});
		}else{
			throw new UnsupportedOperationException("this cannot do non orthoganol or non 2x2 matracies(yet)");
		}
	}
	@SuppressWarnings("unused")
	private void scaleRow(int row, double scaler){
		throw new UnsupportedOperationException("scale row not yet supported");
	}
	@SuppressWarnings("unused")
	private void scaleAndAdd(int from, int to, double scaler){
		throw new UnsupportedOperationException("scaleandmove row not yet supported");
	}
	@SuppressWarnings("unused")
	private void moveRow(int from, int to){
		throw new UnsupportedOperationException("moverow row not yet supported");
		
	}
	public String toString(){
		String ret="";
		for(double[] str: this.matrix){
			ret+=Arrays.toString(str)+"\n";
		}
		
		return ret.substring(0,ret.length()-1);
	}

}
