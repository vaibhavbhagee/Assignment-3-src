import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;

public class Rectangle{

	private int midpoint_x = 200;
	private int midpoint_y = 300;
	private int length = 100;
	private int thickness = 30;
	private int fired_up = 0;

	public int min(int a, int b)
	{
		if (a<b)
			return a;
		else
			return b;
	}

	public int max(int a, int b)
	{
		if(a>b)
			return a;
		else
			return b;
	}

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
		g.setColor(new Color(min(233+20,255*fired_up),max(233-50*fired_up,0),max(235-37*fired_up,0),235));
        g.fillRect(midpoint_x-(length/2), midpoint_y-(thickness/2),length,thickness);
    }

    public void fired_up(){fired_up++;}

    public void fired_down(){fired_up--;}

	public int getMidX(){return midpoint_x;}

	public int getMidY(){return midpoint_y;}

	public int getLength(){return length;}

	public int getThickness(){return thickness;}

	public void setMidX(int x){midpoint_x = x;}

	public void setMidY(int y){midpoint_y = y;}

}