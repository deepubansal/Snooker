package pool;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;


public class ImagePanel extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 798136389526732775L;
    BufferedImage Sce;
	
	BufferedImage backgroundimg;
	
	
	
	public ImagePanel(BufferedImage image)
	{
	this(image, TILED);
	}

	public ImagePanel(BufferedImage image, int style)
	{
	this.image = image;
	this.style = style;
	setLayout( new BorderLayout() );
	}
	
	
	
	private void createImage() {
        int w = getWidth();
        int h = getHeight();
        Sce = (BufferedImage) createImage(w,h);
        Graphics2D g2 = Sce.createGraphics();
        g2.clearRect(0,0,w,h);
//        System.out.println("In CreateImage"+Sce);
        paintBackground(g2);
        g2.dispose();
    }
    

    public void paint(Graphics g) {
      setDoubleBuffered(true);
//      System.out.println("In Paint");
      int w = getWidth();
      int h = getHeight();
     if ( Sce == null ) {
            createImage();
            backgroundimg = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
            backgroundimg.setData(Sce.getData());
            }
        g.drawImage(Sce, 0, 0, this);
    }
     

	public static final int TILED = 0;
	public static final int SCALED = 1;
	public static final int ACTUAL = 2;

	private BufferedImage image;
	private int style;
	private float alignmentX = 0.5f;
	private float alignmentY = 0.5f;
	

	public void setImageAlignmentX(float alignmentX)
	{
	this.alignmentX = alignmentX > 1.0f ? 1.0f : alignmentX < 0.0f ? 0.0f : alignmentX;
	}

	public void setImageAlignmentY(float alignmentY)
	{
	this.alignmentY = alignmentY > 1.0f ? 1.0f : alignmentY < 0.0f ? 0.0f : alignmentY;

	}

	public void add(JComponent component)
	{
	add(component, null);
	}

	public void add(JComponent component, Object constraints)
	{
	component.setOpaque( false );

	if (component instanceof JScrollPane)
	{
	JScrollPane scrollPane = (JScrollPane)component;
	JViewport viewport = scrollPane.getViewport();
	viewport.setOpaque( false );
	Component c = viewport.getView();

	if (c instanceof JComponent)
	{
	((JComponent)c).setOpaque( false );
	}
	}

	super.add(component, constraints);
	}

	public void paintBackground(Graphics g)
	{
	super.paintComponent(g);

	if (image == null ) return;

	switch (style)
	{
	case TILED :
	drawTiled(g);
	break;

	case SCALED :
	Dimension d = getSize();
	g.drawImage(image, 0, 0, d.width, d.height, null);
	break;

	case ACTUAL :
	drawActual(g);
	break;
	}
	}

	private void drawTiled(Graphics g)
	{
	Dimension d = getSize();
	int width = image.getWidth( null );
	int height = image.getHeight( null );

	for (int x = 0; x < d.width; x += width)
	{
	for (int y = 0; y < d.height; y += height)
	{
	g.drawImage( image, x, y, null, null );
	}
	}
	}

	private void drawActual(Graphics g)
	{
	Dimension d = getSize();
	float x = (d.width - image.getWidth()) * alignmentX;
	float y = (d.height - image.getHeight()) * alignmentY;
	g.drawImage(image, (int)x, (int)y, this);
	}

	public BufferedImage getSce() {
		return Sce;
	}

	public void setSce(BufferedImage sce) {
		Sce = sce;
	}

	public BufferedImage getBackgroundimg() {
		return backgroundimg;
	}

	public void setBackgroundimg(BufferedImage background) {
		this.backgroundimg = background;
	}

	}

