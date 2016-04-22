import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;


public class Circle{

	private int midpoint_x = 200;
	private int midpoint_y = 300;
	private int diameter = 30;

	public Circle(int midx, int midy, int d)
	{
		midpoint_y = midy;
		midpoint_x = midx;
		diameter = d;
	}

	public void draw(Graphics g) 
	{
     //System.out.println("drawCircle:"+midpoint_x+":"+midpoint_y);
        g.fillOval(midpoint_x-(diameter/2), midpoint_y-(diameter/2),diameter,diameter);
        Toolkit.getDefaultToolkit().sync();
     //   System.out.println("drawn");
    }

	public int getMidX(){return midpoint_x;}

	public int getMidY(){return midpoint_y;}

	public int getDiameter(){return diameter;}

	public void setMidX(int x){midpoint_x = x;}

	public void setMidY(int y){midpoint_y = y;}

}