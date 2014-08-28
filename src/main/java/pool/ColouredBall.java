package pool;

import java.awt.Component;

public class ColouredBall extends Ball {

	public final static int YELLOW = 1;
	public final static int GREEN = 2;
	public final static int BROWN = 3;
	public final static int BLUE = 4;
	public final static int PINK = 5;
	public final static int BLACK = 6;
	
	int points;
	
	MyVector initposition;
	
	public ColouredBall(int id, MyVector initposition, Component b, int color) {
		super(id, initposition.x, initposition.y, b,color);
		// TODO Auto-generated constructor stub
		this.initposition = initposition;
		points = color+1;
	}

}
