package pool;

import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Pot {
	
	Shape oval;
	Point2D center;

	public Pot(double x, double y, double width, double height, double angle, Point2D center){
		
		this.center = center;
		Ellipse2D ov = new Ellipse2D.Double(x,y,width,height);
		AffineTransform af = new AffineTransform();
		
		af.setToRotation(Math.toRadians(angle),x,y);
	
		oval = af.createTransformedShape(ov);
	}

}
