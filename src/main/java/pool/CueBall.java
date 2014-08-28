package pool;

import java.awt.Component;

public class CueBall extends Ball {

	public CueBall(int id, double cenx, double ceny, Component b) {
		super(id, cenx, ceny, b, 0);
		// TODO Auto-generated constructor stub
	}

	
	public void hit(MyVector pointofboard, MyVector rotation, double pow) {
		MyVector dirofshot = pointofboard.normalize();
		velocity = dirofshot.dotProduct(-pow).dotProduct(k * impactt / m);
		double mul = (2.5*impactt)/(this.m*Board.R*Board.R);
		this.pointofboard = pointofboard;
		w = (rotation.crossProduct(dirofshot.dotProduct(pow).dotProduct(k))).dotProduct(mul);
		lt = System.currentTimeMillis();
//		System.out.println(w);
		coeff = us;
		hascollided = false;
//		moving.start();
	}

}
