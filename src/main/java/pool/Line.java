package pool;

import java.awt.Point;

public class Line {
int a,b,c;


public Line(int x1,int y1,int x2,int y2)
	{
	a = y2-y1;
	b = x1-x2;
	c = x2*y1 - y2*x1;
	}

public Line(Point a, Point b)
{
this(a.x,a.y,b.x,b.y);
}

public int xwhenyGiven(int y)
	{
	 return (Math.round((-c-b*y)/a));
	}

public int ywhenxGiven(int x)
	{
	 return (Math.round((-c-a*x)/b));
	}



}
