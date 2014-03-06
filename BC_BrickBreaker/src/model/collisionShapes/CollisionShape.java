package model.collisionShapes;

import mathematics.Vector;

public abstract class CollisionShape {
	public abstract Vector getCenterOfMass();
	public abstract double getMomentOfInteria();
	public abstract double getMass();

}
