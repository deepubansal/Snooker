package pool;


import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Pool extends JFrame{

	Board board;
	SpinInput spinInput;
	Board stickInput;
	final static double SCALINGFACTOR = 400.0;
	
	public Pool()
	{
		 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 JPanel panel = new JPanel();
		 panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
		 int nonboardwidth = 200;
//		 setLayout(new BorderLayout());
//         setSize((int)(SCALINGFACTOR*3.7), (int)(SCALINGFACTOR*1.8));
        setLocationRelativeTo(null);
         setTitle("Pool");
         try
         {
        	 InputStream stream = this.getClass().getResourceAsStream("Background.jpg");
        	 BufferedImage image = javax.imageio.ImageIO.read(stream);
         	 board = new Board(image,ImagePanel.SCALED);
         	 board.setPreferredSize(new Dimension((int)(SCALINGFACTOR*3.7), (int)(SCALINGFACTOR*1.8)));

         	 InputStream stream2 = this.getClass().getResourceAsStream("lowerbar.jpg");
        	 image = javax.imageio.ImageIO.read(stream2);

         	 InputStream stream3 = this.getClass().getResourceAsStream("stickbar1.png");
        	 BufferedImage stickimage = javax.imageio.ImageIO.read(stream3);

        	 spinInput = new SpinInput(image,ImagePanel.SCALED,stickimage);
             System.out.println(nonboardwidth = image.getHeight()+10);
         	 spinInput.setPreferredSize(new Dimension((int)(SCALINGFACTOR*3.7), 2*nonboardwidth));
         	 
         	 board.setSpinInput(spinInput);

         }catch (IOException e) {e.printStackTrace();}
        
         
         setBounds(0, 0,(int)(200*3.7), (int)(200*1.8)+nonboardwidth);
 	     panel.add(board);
         panel.add(spinInput);
     	System.out.println(getSize());
             
        this.add(panel);
        setVisible(true); 
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   board.initialize();
		   spinInput.drawSpinCircle();
			
        
	}
	
	private static final long serialVersionUID = 8901474120161561887L;
	  public static void main(String args[])
	  {
		  new Pool();
	  }
}
