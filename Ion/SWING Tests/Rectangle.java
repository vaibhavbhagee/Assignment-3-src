import java.awt.Dimension;
import java.awt.Graphics;


public class Rectangle{

	private int midpoint_x = 200;
	private int midpoint_y = 300;
	private int length = 100;
	private int thickness = 30;

	public Rectangle(int midx, int midy, int l, int t)
	{
		midpoint_y = midy;
		midpoint_x = midx;
		length = l;
		thickness = t;
	}

	public void draw(Graphics g) 
	{
       // System.out.println("drawRectangle");
        g.drawRect(midpoint_x-(length/2), midpoint_y-(thickness/2),length,thickness);
    }

	public int getMidX(){return midpoint_x;}

	public int getMidY(){return midpoint_y;}

	public int getLength(){return length;}

	public int getThickness(){return thickness;}

	public void setMidX(int x){midpoint_x = x;}

	public void setMidY(int y){midpoint_y = y;}

}