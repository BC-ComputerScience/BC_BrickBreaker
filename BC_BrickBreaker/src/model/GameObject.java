package model;

import mathematics.Vector;

public interface GameObject {

	public GameObject cloneAt(int x ,int y);
	public GameObject cloneAt(Vector v);
	
	public void translate(int deltaX, int deltaY);
	public void transLate(Vector v);

}
