package pool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Iterator;


public class Board extends ImagePanel implements Runnable, MouseListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 798136389526732775L;
    public static final double R=0.02619375 * Pool.SCALINGFACTOR/1.2; // rad=2.619375 cm... table size = 3.7m x 1.8m
//    public static final double R=0.04619375 * Pool.SCALINGFACTOR; // rad=4.619375 cm... table size = 3.7m x 1.8m
    Pot p[];
    HashSet<Ball> balls;
    BufferedImage backgroundimg;
	Thread refreshing;
	GeneralPath shape;
	GeneralPath reflections[];
	int reflectingangles[];
	SpinInput spinInput;
	
	Player player1;
	Player player2;
	Player currentplayer;
	
	boolean stable;
	
	
	public Board(BufferedImage image)
	{
	super(image, TILED);
	}

	public Board(BufferedImage image, int style)
	{
	super(image,style);
	}
	
	
	public void createShape()
	{
		
		shape = new GeneralPath();
		reflections = new GeneralPath[18];
		
		int boardheight = this.boardheight+8;
		int boardwidth = this.boardwidth +2;
		
		shape.moveTo((float)(cornerboard.x-14+R/2),(float)(cornerboard.y+8-R/2));
		shape.lineTo((float)(cornerboard.x+R),(float)(cornerboard.y+22));
		shape.lineTo((float)(cornerboard.x+R),(float)(cornerboard.y+boardheight-22));
		shape.lineTo((float)(cornerboard.x-14+R/2),(float)(cornerboard.y+boardheight+R/2-8));
	
		
		shape.lineTo((float)(cornerboard.x+8-R/2),(float)(cornerboard.y+boardheight+14-R/2));
	
		
		shape.lineTo((float)(cornerboard.x+22),(float)(cornerboard.y+boardheight-R));
		shape.lineTo((float)(cornerboard.x+boardwidth/2-20),(float)(cornerboard.y+boardheight-R));
		shape.lineTo((float)(cornerboard.x+boardwidth/2-12+R/2),(float)(cornerboard.y+boardheight+12-R/2));
		
		
		
		shape.lineTo((float)(cornerboard.x+boardwidth/2+12-R/2),(float)(cornerboard.y+boardheight+12-R/2));
		
		
		shape.lineTo((float)(cornerboard.x+boardwidth/2+20),(float)(cornerboard.y+boardheight-R));
		shape.lineTo((float)(cornerboard.x+boardwidth-25),(float)(cornerboard.y+boardheight-R));
		shape.lineTo((float)(cornerboard.x+boardwidth-8+R/2),(float)(cornerboard.y+boardheight+14-R/2));
	
		shape.lineTo((float)(cornerboard.x+boardwidth+12-R/2),(float)(cornerboard.y+boardheight-6+R/2));
		
		
		shape.lineTo((float)(cornerboard.x+boardwidth-R),(float)(cornerboard.y+boardheight-19));
		shape.lineTo((float)(cornerboard.x+boardwidth-R),(float)(cornerboard.y+22));
		shape.lineTo((float)(cornerboard.x+boardwidth+14-R/2),(float)(cornerboard.y+8-R/2));
		
		
		shape.lineTo((float)(cornerboard.x+boardwidth-4+R/2),(float)(cornerboard.y-12+R/2));
		
		
		shape.lineTo((float)(cornerboard.x+boardwidth-22),(float)(cornerboard.y+R));
		shape.lineTo((float)(cornerboard.x+boardwidth/2+20),(float)(cornerboard.y+R));
		shape.lineTo((float)(cornerboard.x+boardwidth/2+12-R/2),(float)(cornerboard.y-12+R/2));
		
		
		shape.lineTo((float)(cornerboard.x+boardwidth/2-12+R/2),(float)(cornerboard.y-12+R/2));
		
		
		shape.lineTo((float)(cornerboard.x+boardwidth/2-20),(float)(cornerboard.y+R));
		shape.lineTo((float)(cornerboard.x+22),(float)(cornerboard.y+R));
		shape.lineTo((float)(cornerboard.x+2-R/2),(float)(cornerboard.y-12+R/2));
		
		
		shape.lineTo((float)(cornerboard.x-14+R/2),(float)(cornerboard.y+8-R/2));
	}
	
	public void createReflections()
	{
		
		reflections[0] = new GeneralPath();
		reflectingangles[0] = 45;
				
		reflections[0].moveTo((float)(cornerboard.x-14+R/2+2),(float)(cornerboard.y+8-R/2-2));
		reflections[0].lineTo((float)(cornerboard.x+R+2),(float)(cornerboard.y+22-2));
		reflections[0].lineTo((float)(cornerboard.x-8-R),(float)(cornerboard.y+22-2));
		reflections[0].lineTo((float)(cornerboard.x-14+R/2+2),(float)(cornerboard.y+8-R/2-2));
				

		reflections[1] = new GeneralPath();
		reflectingangles[1] = -45;

		reflections[1].moveTo((float)(cornerboard.x+R+8),(float)(cornerboard.y+boardheight-22+4));
		reflections[1].lineTo((float)(cornerboard.x-14+R/2+6),(float)(cornerboard.y+boardheight+R/2-8+4));
		reflections[1].lineTo((float)(cornerboard.x-2*R),(float)(cornerboard.y+boardheight-22+4));
		reflections[1].lineTo((float)(cornerboard.x+R+8),(float)(cornerboard.y+boardheight-22+4));
		
		reflections[2] = new GeneralPath();
		reflectingangles[2] = 135;

		reflections[2].moveTo((float)(cornerboard.x+8-R/2-4),(float)(cornerboard.y+boardheight+14-R/2+2));
		reflections[2].lineTo((float)(cornerboard.x+22+2),(float)(cornerboard.y+boardheight-R));
		reflections[2].lineTo((float)(cornerboard.x+22+2),(float)(cornerboard.y+boardheight+2*R));
		reflections[2].lineTo((float)(cornerboard.x+8-R/2-4),(float)(cornerboard.y+boardheight+14-R/2+2));
		
		
		reflections[3] = new GeneralPath();
		reflectingangles[3] = 45;

		reflections[3].moveTo((float)(cornerboard.x+boardwidth/2-12+R/2),(float)(cornerboard.y+boardheight+12-R/2+4));
		reflections[3].lineTo((float)(cornerboard.x+boardwidth/2-20+2),(float)(cornerboard.y+boardheight-R-2+5));
		reflections[3].lineTo((float)(cornerboard.x+boardwidth/2-20+2),(float)(cornerboard.y+boardheight+2*R));
	    reflections[3].closePath();	

	    
	    reflections[4] = new GeneralPath();
		reflectingangles[4] = 135;

	    reflections[4].moveTo((float)(cornerboard.x+boardwidth/2+12-R/2-2),(float)(cornerboard.y+boardheight+12-R/2+8));
	    reflections[4].lineTo((float)(cornerboard.x+boardwidth/2+20),(float)(cornerboard.y+boardheight-R+2));
	    reflections[4].lineTo((float)(cornerboard.x+boardwidth/2+20),(float)(cornerboard.y+boardheight+2*R+2));
	    reflections[4].lineTo((float)(cornerboard.x+boardwidth/2+12-R/2-2),(float)(cornerboard.y+boardheight+12-R/2+8));
	  
	    
	    
	    
	    
	    reflections[5] = new GeneralPath();
		reflectingangles[5] = 45;
	    
	    reflections[5].moveTo((float)(cornerboard.x+boardwidth-25),(float)(cornerboard.y+boardheight-R-1));
		reflections[5].lineTo((float)(cornerboard.x+boardwidth-8+R/2+2),(float)(cornerboard.y+boardheight+14-R/2+4));
		reflections[5].lineTo((float)(cornerboard.x+boardwidth-25),(float)(cornerboard.y+boardheight+14+R/2+4));
		reflections[5].lineTo((float)(cornerboard.x+boardwidth-25),(float)(cornerboard.y+boardheight-R-1));
		
	    
	    reflections[6] = new GeneralPath();
		reflectingangles[6] = 225;
	    
	    reflections[6].moveTo((float)(cornerboard.x+boardwidth+12-R/2-2),(float)(cornerboard.y+boardheight-6+R/2+10));
        reflections[6].lineTo((float)(cornerboard.x+boardwidth-R-6),(float)(cornerboard.y+boardheight-19+2));
        reflections[6].lineTo((float)(cornerboard.x+boardwidth+2*R-6),(float)(cornerboard.y+boardheight-19+2));
		reflections[6].closePath();
	    
	    
	    
	    
	    reflections[7] = new GeneralPath();
		reflectingangles[7] = 135;
		
	    reflections[7].moveTo((float)(cornerboard.x+boardwidth-R-4),(float)(cornerboard.y+22));
		reflections[7].lineTo((float)(cornerboard.x+boardwidth+14-R/2),(float)(cornerboard.y+8-R/2));
		reflections[7].lineTo((float)(cornerboard.x+boardwidth+14-R/2),(float)(cornerboard.y+22));
		reflections[7].closePath();
			
		
		
		reflections[8] = new GeneralPath();
		reflectingangles[8] = -45;
		
		reflections[8].moveTo((float)(cornerboard.x+boardwidth-4+R/2+2),(float)(cornerboard.y-12+R/2+4));
		reflections[8].lineTo((float)(cornerboard.x+boardwidth-22-2),(float)(cornerboard.y+R+4));
		reflections[8].lineTo((float)(cornerboard.x+boardwidth-22-2),(float)(cornerboard.y-2*R+4));
		reflections[8].closePath();
		
		
		reflections[9] = new GeneralPath();
		reflectingangles[9] = 225;
		
		reflections[9].moveTo((float)(cornerboard.x+boardwidth/2+20-2),(float)(cornerboard.y+R+2));
		reflections[9].lineTo((float)(cornerboard.x+boardwidth/2+12-R/2-6),(float)(cornerboard.y-12+R/2-5));
		reflections[9].lineTo((float)(cornerboard.x+boardwidth/2+12+R-2),(float)(cornerboard.y-12+R/2-5));
		reflections[9].closePath();
		 
		
		
		reflections[10] = new GeneralPath();
		reflectingangles[10] = -45;
		
		reflections[10].moveTo((float)(cornerboard.x+boardwidth/2-12+R/2+8),(float)(cornerboard.y-12+R/2-6));
		reflections[10].lineTo((float)(cornerboard.x+boardwidth/2-20),(float)(cornerboard.y+R+4));
		reflections[10].lineTo((float)(cornerboard.x+boardwidth/2-20),(float)(cornerboard.y-2*R));
		reflections[10].closePath();
		
	    
		
		reflections[11] = new GeneralPath();
		reflectingangles[11] = 225;
			    
	    reflections[11].moveTo((float)(cornerboard.x+22),(float)(cornerboard.y+R+4));
		reflections[11].lineTo((float)(cornerboard.x+2-R/2-2),(float)(cornerboard.y-12+R/2+2));
		reflections[11].lineTo((float)(cornerboard.x+22),(float)(cornerboard.y-2*R+4));
		reflections[11].closePath();
		
		
		
		
		
		reflections[12] = new GeneralPath();
		reflectingangles[12] = 0;

		reflections[12].moveTo((float)(cornerboard.x+R+2),(float)(cornerboard.y+22-2));
		reflections[12].lineTo((float)(cornerboard.x+R+8),(float)(cornerboard.y+boardheight-22+4));
		reflections[12].lineTo((float)(cornerboard.x-2*R+8),(float)(cornerboard.y+boardheight-22+4));
		reflections[12].lineTo((float)(cornerboard.x-2*R+2),(float)(cornerboard.y+22-2));
		reflections[12].closePath();
		
		
		reflections[13] = new GeneralPath();
		reflectingangles[13] = 90;

		reflections[13].moveTo((float)(cornerboard.x+22+2),(float)(cornerboard.y+boardheight-R));
		reflections[13].lineTo((float)(cornerboard.x+boardwidth/2-20+2),(float)(cornerboard.y+boardheight-R-2+5));
		reflections[13].lineTo((float)(cornerboard.x+boardwidth/2-20+2),(float)(cornerboard.y+boardheight+R-2+5));
		reflections[13].lineTo((float)(cornerboard.x+22+2),(float)(cornerboard.y+boardheight+3+R));
		reflections[13].closePath();
		
		
		
		reflections[14] = new GeneralPath();
		reflectingangles[14] = 90;

		reflections[14].moveTo((float)(cornerboard.x+boardwidth/2+20),(float)(cornerboard.y+boardheight-R+2));
		reflections[14].lineTo((float)(cornerboard.x+boardwidth-25),(float)(cornerboard.y+boardheight-R-1));
		reflections[14].lineTo((float)(cornerboard.x+boardwidth-25),(float)(cornerboard.y+boardheight+R-1));
		reflections[14].lineTo((float)(cornerboard.x+boardwidth/2+20),(float)(cornerboard.y+boardheight+R-1));
		reflections[14].closePath();
		
		
		
		reflections[15] = new GeneralPath();
		reflectingangles[15] = 180;

		reflections[15].moveTo((float)(cornerboard.x+boardwidth-R-6),(float)(cornerboard.y+boardheight-19+2));
	    reflections[15].lineTo((float)(cornerboard.x+boardwidth-R-4),(float)(cornerboard.y+22));
	    reflections[15].lineTo((float)(cornerboard.x+boardwidth+R-4),(float)(cornerboard.y+22));
	    reflections[15].lineTo((float)(cornerboard.x+boardwidth+R-6),(float)(cornerboard.y+boardheight-19+2));
	    reflections[15].closePath();
		   
		
		
		reflections[16] = new GeneralPath();
		reflectingangles[16] = 270;

		reflections[16].moveTo((float)(cornerboard.x+boardwidth-22-2),(float)(cornerboard.y+R+4));
		reflections[16].lineTo((float)(cornerboard.x+boardwidth/2+20-2),(float)(cornerboard.y+R+2));
		reflections[16].lineTo((float)(cornerboard.x+boardwidth/2+20-2),(float)(cornerboard.y-R+2));
		reflections[16].lineTo((float)(cornerboard.x+boardwidth-22-2),(float)(cornerboard.y-R+2));
		reflections[16].closePath();
		
		
		
		reflections[17] = new GeneralPath();
		reflectingangles[17] = 270;

		reflections[17].moveTo((float)(cornerboard.x+boardwidth/2-20),(float)(cornerboard.y+R+4));
		reflections[17].lineTo((float)(cornerboard.x+22),(float)(cornerboard.y+R+4));
		reflections[17].lineTo((float)(cornerboard.x+22),(float)(cornerboard.y-R+4));
		reflections[17].lineTo((float)(cornerboard.x+boardwidth/2-20),(float)(cornerboard.y-R+4));
		reflections[17].closePath();
	}

	public void switchplayer(){
		if (currentplayer == player1)
			currentplayer = player2;
		else
			currentplayer = player1;
	}

	public void initialize() {
		 p = new Pot[6];
 		 reflections = new GeneralPath[18];
 		 reflectingangles = new int[18];
 		 player1 = new Player("ABC");
 		 player2 = new Player("XYZ");
 		 currentplayer = player1;
 		 stable = true;
		 createShape();
		 createReflections();
		 balls = new HashSet<Ball>();
		 resetFrame();
		 showAll();
		 this.addMouseListener(this);
		 refreshing = new Thread(this,"Refreshing");
	     refreshing.start();
		 p[0] = new Pot(cornerboard.x+8,cornerboard.y-20,Board.R + 14,Board.R + 21,70, new Point((int)(cornerboard.x-4),(int)(cornerboard.y-2)));
		 p[1] = new Pot(cornerboard.x+20,cornerboard.y+boardheight+8+3,Board.R + 14,Board.R + 21,135, new Point((int)(cornerboard.x-3),(int)(cornerboard.y+boardheight+4)));
		 p[2] = new Pot(cornerboard.x+boardwidth/2+19,cornerboard.y+boardheight+4+5,Board.R + 8,Board.R + 26,90, new Point((int)(cornerboard.x+boardwidth/2),(int)(cornerboard.y+boardheight+13)));
		 p[3] = new Pot(cornerboard.x+boardwidth-6,cornerboard.y+boardheight+22+5,Board.R + 14,Board.R + 21,245, new Point((int)(cornerboard.x+boardwidth+4),(int)(cornerboard.y+boardheight+6)));
		 p[4] = new Pot(cornerboard.x+boardwidth-17,cornerboard.y,Board.R + 16,Board.R + 21,-60, new Point((int)(cornerboard.x+boardwidth+2),(int)(cornerboard.y)));
		 p[5] = new Pot(cornerboard.x+boardwidth/2-17,cornerboard.y,Board.R + 8,Board.R + 26,270,new Point((int)(cornerboard.x+boardwidth/2),(int)( cornerboard.y-8)));
		 
	}

	public void resetFrame(){
		balls.clear();
		CueBall white = new CueBall(0,getPosition(0).x,getPosition(0).y,this);
		cueball = white;
		ColouredBall yellow = new ColouredBall(1,getPosition(ColouredBall.YELLOW),this,ColouredBall.YELLOW);
		ColouredBall green = new ColouredBall(2,getPosition(ColouredBall.GREEN),this,ColouredBall.GREEN);
		ColouredBall brown = new ColouredBall(3,getPosition(ColouredBall.BROWN),this,ColouredBall.BROWN);
		ColouredBall blue = new ColouredBall(4,getPosition(ColouredBall.BLUE),this,ColouredBall.BLUE);
		ColouredBall pink = new ColouredBall(5,getPosition(ColouredBall.PINK),this,ColouredBall.PINK);
		ColouredBall black = new ColouredBall(6,getPosition(ColouredBall.BLACK),this,ColouredBall.BLACK);
		balls.add(yellow);
		balls.add(green);
		balls.add(brown);
		balls.add(blue);
		balls.add(pink);
		balls.add(black);
		balls.add(white);
		int c = 0;
		for (int i = 0; i < 5; ++i)
			for (int j = 0; j < i+1;++j,c++)
				balls.add(new RedBall((c+7),pink.initposition.x+2*(i+1)*Board.R,pink.initposition.y+i*Board.R-j*2*Board.R,this));
		
		gofor = 10;
		firstcontact = 0;
	}

	public  MyVector getPosition(int color)
	{
		
		MyVector initposition = null;
		int baulklinex = (int)(-boardwidth/2.0+0.2*boardwidth);
		
		switch (color)
		{
		case 0:	initposition = new MyVector(baulklinex-10,-boardheight/12,0);
				break;			
		case 1: initposition = new MyVector(baulklinex,-boardheight/6,0);
				break;
		case 2: initposition = new MyVector(baulklinex,+boardheight/6,0);
				break;
		case 3: initposition = new MyVector(baulklinex,0,0);
				break;
		case 4: initposition = new MyVector(0,0,0);
				break;
		case 5: initposition = new MyVector(0.25*boardwidth,0,0);
				break;
		case 6: initposition = new MyVector(boardwidth/2-boardheight/6,0,0);
				break;
		}
		
		return initposition;
	}
	
	int firstcontact;
	int gofor;
	
	public void showAll()
	{
		
		Iterator it = balls.iterator();
		boolean repaintreq =  false;
		Graphics2D g2 = Sce.createGraphics();
		while (it.hasNext())
		{
		 Ball b = (Ball)it.next();
		 if (b.updateneeded)
			 {
			 b.show(g2);
			 repaintreq = true;
			 }
		}
		if (repaintreq)
			repaint();
		g2.dispose();
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void run() {
		// TODO Auto-generated method stub
		Iterator it;
		while (true)
		{
			stable = true;
			it = balls.iterator();
			boolean needupdate = false;
			while (it.hasNext())
			{
			 Ball b = (Ball)it.next();
			 needupdate = needupdate | b.update();
			}
			if (needupdate)
			{
				showAll();
				stable = false;
			}
//			System.out.println(stable);
			try {
				Thread.sleep(15);
			} catch (Exception e) {
			e.printStackTrace();}
		}
	}

	public final Point cornerboard = new Point(30,27);
	public final int boardwidth = 700-30;
	public final int boardheight = 307-27;
	
	public final Point centerboard = new Point((int)(cornerboard.x+boardwidth/2),(int)(cornerboard.y+boardheight/2));
	
	public CueBall cueball;
	
	public MyVector xyToBoard(MyVector n)
	{
		return new MyVector(n.getX()+centerboard.x,-n.y+centerboard.y,n.z);
	}
	
	public Point xyToBoard(Point n)
	{
		return new Point((int)(n.getX()+centerboard.x),(int)(-n.getY()+centerboard.y));
	}
	
	public MyVector boardToxy(MyVector n)
	{
		return new MyVector(n.getX()-centerboard.x,-n.y+centerboard.y,n.z);
	}
	
	public Point boardToxy(Point n)
	{
		return new Point((int)(n.getX()-centerboard.x),(int)(-n.getY()+centerboard.y));
	}
	

	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		if (stable){
		double pow = spinInput.power/35.0;
		Point p = new Point(spinInput.getHitpoint());
		Point c = spinInput.corner;
		p.translate(-c.x,-c.y);
		
		p.x = p.x -spinInput.radius;
		p.y = spinInput.radius-p.y;
		
		int r = spinInput.radius;
		int z = (int)(Math.sqrt(r*r-p.x*p.x-p.y*p.y));
		
		p.x /= 2;
		p.y /= 2;
		z = 0;
		
		MyVector rotation = new MyVector((p.x*R)/r,(p.y*R)/r,(z*R)/r);
		double xd = -rotation.z;
		double yd = -rotation.x;
		double zd = Board.R +rotation.y;
		Point dir = e.getPoint();
		dir = this.boardToxy(dir);
		if (Math.abs(dir.x) > boardwidth/2 +5 || Math.abs(dir.y) > boardheight/2+5)
			return;
		System.out.println(dir);
		Point ballpos = new Point((int)cueball.position.x,(int)cueball.position.y);
		Point pointofboard = null;
		Line line = new Line(ballpos,dir);
		if (ballpos.x < dir.x)
		{
			if (ballpos.y < dir.y)
			{
				int x = line.xwhenyGiven(boardheight/2);
				if (x < boardwidth/2)
					pointofboard = new Point(x,boardheight/2);
				else
					pointofboard = new Point(boardwidth/2,line.ywhenxGiven(boardwidth/2));
			}
			else
			{
				int x = line.xwhenyGiven(-boardheight/2);
				if (x < boardwidth/2)
					pointofboard = new Point(x,-boardheight/2);
				else
					pointofboard = new Point(boardwidth/2,line.ywhenxGiven(boardwidth/2));
				
			}
		
		}
		else
		{
			
			if (ballpos.y < dir.y)
			{
				int x = line.xwhenyGiven(boardheight/2);
				if (x > -boardwidth/2)
					pointofboard = new Point(x,boardheight/2);
				else
					pointofboard = new Point(-boardwidth/2,line.ywhenxGiven(-boardwidth/2));
			}
			else
			{
				int x = line.xwhenyGiven(-boardheight/2);
				if (x > -boardwidth/2)
					pointofboard = new Point(x,-boardheight/2);
				else
					pointofboard = new Point(-boardwidth/2,line.ywhenxGiven(-boardwidth/2));
				
			}
		}
		
		
		System.out.println(pointofboard);
//		Graphics2D g = Sce.createGraphics();
		
//		g.drawLine(xyToBoard(ballpos).x, xyToBoard(ballpos).y, xyToBoard(pointofboard).x, xyToBoard(pointofboard).y);
//		repaint();

		double theta;
		theta = Math.asin((double)(dir.y-ballpos.y)/ballpos.distance(dir));
		if (ballpos.x > dir.x)
			theta = 3.14-theta;
		if (theta < 0)
			theta += 2*3.14;
		
		double xdd = xd*Math.cos(theta) -  yd * Math.sin(theta);
		double ydd = xd*Math.sin(theta) +  yd * Math.cos(theta) ;
		double zdd= zd-R;
		
		rotation = new MyVector(xdd,ydd,-zdd);
	    cueball.hit(new MyVector(ballpos,pointofboard),rotation,pow);
	    System.out.println(balls.size());
		}
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void setSpinInput(SpinInput spinInput) {
		this.spinInput = spinInput;
	}


	
	
}
