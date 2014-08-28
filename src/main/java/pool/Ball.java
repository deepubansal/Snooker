package pool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.ImageIcon;

public class Ball{


	int id;

	MyVector position;

	MyVector prevposition;
	
	Board board;
	
	boolean updateneeded;
	
	MyVector pointofboard;
	
	final double m = 0.5; // in Kg

	final  double k = 10 * Pool.SCALINGFACTOR; // Spring constant (Hook's Law)

//	final  double dt = .01; // delta t in seconds.
	 
	double lt;

	final double impactt = .01; // delta t in seconds.

	final  double ur = .01; // coeff. of rolling friction.

	final  double us = .1; // coeff. of rolling friction.
	
	final  double g = 9.8 * Pool.SCALINGFACTOR; // gravitational coefficient.

	MyVector velocity;
	
	MyVector w; // Angular Velocity

	ImageIcon e1;

	ImageIcon bg;
	
	MyVector R;

	String filename[] = {"white.gif","yellow.png","green.png","brown.png","blue.png","pink.png","black.png","red1.png"};

	public Ball(int id, double cenx, double ceny, Component b, int color)
	{
		this.id = id;
		board =(Board) b;
		velocity = new MyVector(0,0);
		position = new MyVector(cenx,ceny,Board.R);
		prevposition = new MyVector(cenx,ceny,Board.R);
		R = new MyVector(0,0,-Board.R);
		MyVector position_board = board.xyToBoard(position);
		double r = Board.R;
		BufferedImage img = new BufferedImage((int)(2*r),(int)(2*r),BufferedImage.TRANSLUCENT);
		BufferedImage img2= new BufferedImage((int)(2*r),(int)(2*r),BufferedImage.TRANSLUCENT);
		Graphics g = img2.createGraphics();
		g.drawImage(board.getBackgroundimg(),0,0,(int)(2*r),(int)(2*r),(int)(position_board.getX()-r), (int)(position_board.getY()-r),(int)(position_board.getX()+r), (int)(position_board.getY()+r), board);
		g.dispose();
		bg = new ImageIcon(img2);
		updateneeded = true;
		try {
			Graphics2D g2 = img.createGraphics();
			g2.drawImage((javax.imageio.ImageIO.read(this.getClass().getResourceAsStream(filename[color]))), 0, 0, (int)(2*r),(int)(2*r), board);
			g2.dispose();
			e1 = new ImageIcon(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void show(Graphics2D g2) {
//		System.out.println(prevposition);
//		System.out.println("update"+position);
		
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_SPEED);
		rh.put(RenderingHints.KEY_FRACTIONALMETRICS,RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		int r = (int)(Board.R+0.5);
		BufferedImage img2 = new BufferedImage((int) (2 * r)+2, (int) (2 * r)+2,
				BufferedImage.TRANSLUCENT);
		Graphics g = img2.createGraphics();
		MyVector prevposition_board = board.xyToBoard(prevposition);
		g.drawImage(board.getBackgroundimg(), 0, 0,
				(int) (2 * r)+2, (int) (2 * r)+2, (int) (prevposition_board.getX() - r) - 1,
				(int) (prevposition_board.getY() - r) - 1, (int) (prevposition_board.getX() + r) + 1,
				(int) (prevposition_board.getY() + r + 1), board);
		bg.setImage(img2);
		g.dispose();
	
		MyVector position_board = board.xyToBoard(position); 
		bg.paintIcon(board, g2, (int) (prevposition_board.getX() - r -1),
				(int) (prevposition_board.getY() - r)-1);
		e1.paintIcon(board, g2, (int) (position_board.getX() - r), (int) (position_board
				.getY() - r));
//		g2.setColor(Color.red);
//		g2.setStroke(new BasicStroke(2));
//		
//		g2.drawRect((int) (position_board.getX() - r), (int) (position_board.getY() - r),1, 1);
		prevposition = position;
		
		Toolkit.getDefaultToolkit().sync();
	}

	double coeff;
	
	
	
	
	public boolean collide(MyVector position)
	{
	 boolean  rv = false;
	 Point pt = board.xyToBoard(new Point((int)position.x,(int)position.y));
	 int i;
	 for (i =0; i < 6;++i)
	 {
		 if (board.p[i].oval.contains(pt))
			 break;
		 
	 }
	 if (i <= 5)
	 {
		showpotted(i);
		velocity = new MyVector(0,0,0);
		w = new MyVector(0,0,0);
		rv = justpotted=true;
	 }
	 else
	 {
	 if (!board.shape.contains(pt))
	 {
		 for (i = 0; i < 18;++i)
		 if (board.reflections[i].contains(pt))
		 {
			 MyVector newVelocity = new MyVector(0,0,0);
			 double twicetheta = Math.toRadians(2*board.reflectingangles[i]);
			 newVelocity.x = -velocity.x* Math.cos(twicetheta) - velocity.y*Math.sin(twicetheta);
			 newVelocity.y = -velocity.x* Math.sin(twicetheta) + velocity.y*Math.cos(twicetheta);
			 velocity = newVelocity;
		 }
		 rv = hascollided=true;
	 }
	 else
	 {
		 HashSet<Ball> balls = board.balls;
		 Iterator<Ball> it = balls.iterator();
		 while (it.hasNext())
		 {
			 Ball b = (Ball)it.next();
			 if (b != this && b.position.distanceFrom(position) < 2*Board.R)
				{
				 updateVectors(b);
				 rv = true;
				 if (board.firstcontact != 0 && (b.id == 0 || this.id == 0))
					 board.firstcontact = (b.id == 0)?this.id:b.id;
				}
		 }
		 
	 }
	 }
	return rv;
	}
	
	boolean justpotted;
	
	private void showpotted(int i) {
		// TODO Auto-generated method stub
		Point pt = board.boardToxy((Point)board.p[i].center);
		position = new MyVector(pt.getX(),pt.getY());
	}

	private void updateVectors(Ball b) {
		// TODO Auto-generated method stub
		MyVector normal = new MyVector(b.position,position).normalize();
		MyVector oppositenormal = new MyVector(-normal.x,-normal.y,normal.z);
		MyVector vn2 = oppositenormal.dotProduct(b.velocity.dotProduct(oppositenormal));
		MyVector vn1 = normal.dotProduct(velocity.dotProduct(normal));
		MyVector vt1 = velocity.subtract(vn1);
		MyVector vt2 = b.velocity.subtract(vn2);
		velocity = vt1.add(vn2);
		b.velocity = vt2.add(vn1);
		hascollided = true;
		b.hascollided = true;
	}


	boolean hascollided;
	
	public boolean update() {
		boolean rv = false;
		boolean sliding=true;
		updateneeded = false;
		if (!velocity.isZero()) { 
			justpotted = false;
			double dt = (System.currentTimeMillis()-lt)/1000;
			if (!hascollided)
				{
				MyVector dirofshot = pointofboard.normalize();
				velocity = dirofshot.dotProduct(velocity.dotProduct(dirofshot));
				}
			MyVector orthoVelocity = new MyVector(-velocity.y/velocity.magnitude(),velocity.x/velocity.magnitude());
			double walongv = w.dotProduct(orthoVelocity);
			if (sliding == true && isSliding(walongv))
				coeff = us;
			else
			{
				coeff = ur;
				sliding = false;
			}
			MyVector u = velocity;
			if (sliding)
			{
			int dirofForce = 0;
			if (Board.R*walongv-velocity.magnitude() < 0)
				dirofForce = -1;
			else
				dirofForce = 1;
			velocity = velocity.normalize().dotProduct(velocity.magnitude()+dirofForce*coeff*g*dt);
			MyVector wother = w.subtract(orthoVelocity.dotProduct(walongv));
			w = wother.add(orthoVelocity.dotProduct(walongv -dirofForce* 2*coeff*g*dt/Board.R));
			}
			else
			{
				velocity = velocity.normalize().dotProduct(velocity.magnitude()-coeff*g*dt);
				double newwalongv = velocity.magnitude()/Board.R; // making sure that R*walongv and velocity are same after it starts rolling. 
				MyVector wother = w.subtract(orthoVelocity.dotProduct(walongv));
				w = wother.add(orthoVelocity.dotProduct(newwalongv));
			}
			if (w.z >= 0)
			w.z -= 3*dt;
			u = u.add(velocity);
			u = u.dotProduct(dt * 0.5);
			 if (!collide(position.add(u)))
				
			position = position.add(u);
//			System.out.println(position);
			rv = true;
		}
		else
			w = new MyVector(0,0,0);
		lt = System.currentTimeMillis();
		
		rv = rv||justpotted;
		justpotted = false;
		return (updateneeded = rv);
	} 

	private boolean isSliding(double walongv)
	{
	 return Math.abs(velocity.magnitude()-Board.R*walongv) >= 40;
	}
	

}
