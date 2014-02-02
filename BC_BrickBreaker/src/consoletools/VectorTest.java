package consoletools;

import java.util.Random;

import mathematics.Vector;

public class VectorTest implements ShellProgram {
	Random rand=new Random();
	@Override
	public int Execute(Shell s, String... args) {
		validate(s);
		
		int numVectors=10000;
		for(int round=0;round<10;round++){
			
			Vector[] vectors=new Vector[numVectors]; 
			
			for(int i=0;i<numVectors;i++){
				vectors[i]=new Vector(rand.nextInt(100)+1,rand.nextInt(100)+1);
			}
			
			long time1=System.currentTimeMillis();
			for(int i=0;i<numVectors;i++){
				for(int j=i;j<numVectors;j++){
					vectors[i].getProjection(vectors[j]);
				}
			}
			long time2=System.currentTimeMillis();
			for(int i=0;i<numVectors;i++){
				for(int j=i;j<numVectors;j++){
					vectors[i].getProjection2(vectors[j]);
				}
			}
			long time3=System.currentTimeMillis();
			System.out.println("method1: "+(time2-time1)+"\t method2: "+(time3-time2));

		}
		
		return 0;
	}
	
	public void validate(Shell s){
		for(int i=0;i<10;i++){
			Vector v1=new Vector(rand.nextInt(100)+1,rand.nextInt(100)+1);
			Vector v2=new Vector(rand.nextInt(100)+1,rand.nextInt(100)+1);
			Vector res1=v1.getProjection(v2);
			Vector res2=v1.getProjection2(v2);
			s.out.println(res1.equals(res2)+", "+res1+", "+res2);
		}
	}

}
