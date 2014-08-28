package pool;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.event.MouseInputListener;


public class SpinInput extends ImagePanel implements MouseInputListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6274556970818284463L;
	final int radius = 56;
	int power;
	BufferedImage stickimage;

	public SpinInput(BufferedImage image,int style, BufferedImage stickimage)
	{
		super(image,style);
		hitpoint = new Point(0,0);
		power =200;
		addMouseMotionListener(this);
		addMouseListener(this);
		this.stickimage = stickimage;
	}
	public void drawPlus(Point pluspoint,Graphics2D g)
	{
		int len = 15;
		g.drawLine(pluspoint.x, pluspoint.y-len, pluspoint.x, pluspoint.y+len);
		g.drawLine(pluspoint.x-len, pluspoint.y, pluspoint.x+len, pluspoint.y);
		hitpoint = pluspoint;
		drawStickBar(g);
	}

	Ellipse2D el;
	Rectangle2D r;
	Point hitpoint;
	Point center;
	Point corner;
	
	public void drawSpinCircle()
	{
	Graphics2D g = Sce.createGraphics();
	corner = new Point(601,13);
	el = new Ellipse2D.Double(corner.x,corner.y,2*radius,2*radius);
	center = new Point(corner.x+radius,corner.y+radius);
	g.setColor(Color.black);
	g.setStroke(new BasicStroke(2));
	drawPlus(center,g);
    repaint();
	g.dispose();
	}
	
	
	
	public void drawStickBar(Graphics2D g)
	{
		g.setColor(Color.LIGHT_GRAY);
		g.setStroke(new BasicStroke(2));
		int y = 30;
		int xstart = 35;
		int xend = 520;
		int xPoint[] = new int[3];
		int yPoint[] = new int[3];
		xPoint[0] = xstart;
		yPoint[0] = y+30;
		xPoint[1] = xstart;
		yPoint[1] = y+90;
		xPoint[2] = xend;
		yPoint[2] = y+90;
		g.drawPolygon(xPoint, yPoint, 3);
		g.setStroke(new BasicStroke(1));
		this.r= new Rectangle2D.Double(xstart,y, xend-xstart,15);
		g.draw(r);
		g.drawRect(xstart, y+2, 485-power, 5);
		g.drawImage(stickimage, xstart, y, xend-power , y+stickimage.getHeight(), power, 0, stickimage.getWidth(),stickimage.getHeight(),this);
		fillR(xstart,xend,y,g);
	}
		
	public void fillR(int xstart,int xend,int y,Graphics2D g)
	{
		int half = (xend-xstart+30)/2;
		int red = 0,gr = 220,bl=40;
		for (int i = xend; i >= xend-power;--i)
		{
			if (i > half)
			{
				if (red < 255) red++; 
			}
			else
				if (gr > 0) gr--;
			g.setColor(new Color(red,gr,bl));
			g.drawLine(i,y+90, i, y+90-(int)((60.0*(xend-i)/(xend-xstart))));
		}
	}

	public void paintPortion(int x, int y, int width, int height)
	{
		
		
		
	}
	
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("MouseClicked");
		if (el.contains(e.getPoint()))
		{
			Graphics2D g = super.Sce.createGraphics();
			g.setStroke(new BasicStroke(2));
			this.paintBackground(g);
			drawPlus(e.getPoint(),g);
			repaint();
		}
		else
			if (r.contains(e.getPoint()))
			{
				power = 520-e.getPoint().x;
				Graphics2D g = super.Sce.createGraphics();
				g.setStroke(new BasicStroke(2));
				this.paintBackground(g);
				drawPlus(hitpoint,g);
				repaint();
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
		if (el.contains(e.getPoint()))
		{
			Graphics2D g = super.Sce.createGraphics();
			g.setStroke(new BasicStroke(2));
			this.paintBackground(g);
			drawPlus(e.getPoint(),g);
			repaint();
		}
		else
			if (r.contains(e.getPoint()))
			{
				power = 520-e.getPoint().x;
				Graphics2D g = super.Sce.createGraphics();
				g.setStroke(new BasicStroke(2));
				this.paintBackground(g);
				drawPlus(hitpoint,g);
				repaint();
					
			}
		
	
	}
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if (el.contains(e.getPoint()))
		{
			Graphics2D g = super.Sce.createGraphics();
			g.setStroke(new BasicStroke(2));
			this.paintBackground(g);
			drawPlus(e.getPoint(),g);
			repaint();
			
		}
		else
			if (r.contains(e.getPoint()))
			{
				power = 520-e.getPoint().x;
				Graphics2D g = super.Sce.createGraphics();
				g.setStroke(new BasicStroke(2));
				this.paintBackground(g);
				drawPlus(hitpoint,g);
				repaint();
					
			}
			
	}
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		if (el.contains(e.getPoint()))
		{
			Graphics2D g = super.Sce.createGraphics();
			g.setStroke(new BasicStroke(2));
			this.paintBackground(g);
			drawPlus(e.getPoint(),g);
			repaint();
		}
		else
			if (r.contains(e.getPoint()))
			{
				power = 520-e.getPoint().x;
				Graphics2D g = super.Sce.createGraphics();
				g.setStroke(new BasicStroke(2));
				this.paintBackground(g);
				drawPlus(hitpoint,g);
				repaint();
					
			}
	
			
	}
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	public Point getHitpoint() {
		return hitpoint;
	}


}
