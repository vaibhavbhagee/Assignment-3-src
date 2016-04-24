import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;

public class Rectangle{

	private int midpoint_x = 200;
	private int midpoint_y = 300;
	private int length = 100;
	private int thickness = 30;
	private int fired_up = 0;

	private int paddleorientation = 0;
	private double thetaf = 0.0;
	private double rf = 0.0;

	private double thetas = 0.0;
	private double rs = 0.0;

	public int overridecolor = 0;
	public Color overridecolorwith;

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

	public Rectangle(int midx, int midy, int l, int t, int orien)
	{
		midpoint_y = midy;
		midpoint_x = midx;
		length = l;
		thickness = t;
		paddleorientation = orien;
		thetaf = t!=0?(4*l)/t:0.0;
		rf = t!=0?(l*l)/(8*t):0.0; 
		thetas = l!=0?(4*t)/l:0.0;
		rs = l!=0?(t*t)/(8*l):0.0;
	}

	public void draw(Graphics g) 
	{
		if(overridecolor == 0)
			g.setColor(new Color(min(233+20,255*fired_up),max(233-50*fired_up,0),max(235-37*fired_up,0),235));
		else
			g.setColor(overridecolorwith);
        
		if(paddleorientation == 0)
        	g.fillRect(midpoint_x-(length/2), midpoint_y-(thickness/2),length,thickness);
        else if(paddleorientation == 1)
        {
            Graphics2D g2 = (Graphics2D)g;
        	Arc2D.Float arc1 = new Arc2D.Float(Arc2D.CHORD);         
		    arc1.setFrame(midpoint_x-rf,midpoint_y-(thickness/2), 2*rf,2*rf );  
		    arc1.setAngleStart(90-thetaf*2.3); 
		    arc1.setAngleExtent( thetaf*4.6 ); 

		    g2.fill(arc1);  
        }
        else if(paddleorientation == 3)
        {
            Graphics2D g2 = (Graphics2D)g;
        	Arc2D.Float arc1 = new Arc2D.Float(Arc2D.CHORD);         
		    arc1.setFrame(midpoint_x-rf,midpoint_y-(2*rf)+(thickness/2), 2*rf,2*rf );  
		    arc1.setAngleStart(270-thetaf*2.3); 
		    arc1.setAngleExtent( thetaf*4.6 ); 
	
		    g2.fill(arc1);  
        }
        else if(paddleorientation == 2)
        {
            Graphics2D g2 = (Graphics2D)g;
        	Arc2D.Float arc1 = new Arc2D.Float(Arc2D.CHORD);         
		    arc1.setFrame(midpoint_x-(length/2),midpoint_y-rs, 2*rs,2*rs );  
		    arc1.setAngleStart(180-thetas*2.3); 
		    arc1.setAngleExtent( thetas*4.6 ); 
	
		    g2.fill(arc1);  
        }
        else if(paddleorientation == 4)
        {
            Graphics2D g2 = (Graphics2D)g;
        	Arc2D.Float arc1 = new Arc2D.Float(Arc2D.CHORD);         
		    arc1.setFrame(midpoint_x-(2*rs)+(length/2),midpoint_y-rs, 2*rs,2*rs );  
		    arc1.setAngleStart(360-thetas*2.3); 
		    arc1.setAngleExtent( thetas*4.6 ); 
	
		    g2.fill(arc1);  
        }
    }

    public void fired_up(){fired_up++;}

    public void fired_down(){fired_up--;}

    public void setFired(int f){fired_up=f;}

    public int getFired(){return min(fired_up,5);}

	public int getMidX(){return midpoint_x;}

	public int getMidY(){return midpoint_y;}

	public int getLength(){return length;}

	public int getThickness(){return thickness;}

	public void setMidX(int x){midpoint_x = x;}

	public void setMidY(int y){midpoint_y = y;}

}