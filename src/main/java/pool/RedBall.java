package pool;

import java.awt.Component;

public class RedBall extends Ball {

	int points;
	public RedBall(int id, double cenx, double ceny, Component b) {
		super(id, cenx, ceny, b, 7);
		// TODO Auto-generated constructor stub
		points = 1;
	}

}
