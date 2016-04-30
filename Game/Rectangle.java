import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.GradientPaint;

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

	private int lives = 0;
	private int lostlives = 0;
	private Color Color_0 = new Color(20,11,231,0);
	private Color Color_1 = new Color(255,0,0,20);
	private Color Color_2 = new Color(255,0,0,40);
	private Color Color_3 = new Color(255,0,0,60);
	private Color Color_4 = new Color(255,0,0,80);
	private Color Color_f = new Color(255,0,0,100);

	private int maxDabbaWidth = 40;
	private int playerDeadOpenDabba = 0;
	private double playerDeadFallThePaddleInTheHole = 1.00000000000;
	private int waitTillPaddleFallInTheHole = 0;
	private int deadFlag = 0;
//Functions for special uses
	public void killPaddle()
	{
		playerDeadFallThePaddleInTheHole = 0.98;
	}

	public void setLifeRect(int a)
	{
		lives = a;
	}

	public boolean marJaApproval()
	{
		return (deadFlag == 1);
	}

	public int isLife()
	{
		return lives;
	}

	public void lostLife()
	{
		if(lostlives<5)
			lostlives = lostlives + 1;
	}

	public void lostLifeSet(int lls)
	{
		lostlives = lls;
	}

	public int getLostLives()
	{
		return lostlives;
	}

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
//Draw the rectangle/arc paddle/whatever
	public void draw(Graphics g) 
	{
		rf = playerDeadFallThePaddleInTheHole*rf;
		rs = playerDeadFallThePaddleInTheHole*rs;
		if(overridecolor == 0)
			g.setColor(new Color(min(233+20,255*fired_up),max(233-50*fired_up,0),max(235-37*fired_up,0),235));
		else
			g.setColor(overridecolorwith);
        if(lives==0)
        {
        	g.setColor(overridecolorwith);
			if(paddleorientation == 0)
	        	g.fillRect(midpoint_x-(length/2), midpoint_y-(thickness/2),length,thickness);
	        else if(paddleorientation == 1)
	        {
	            Graphics2D g2 = (Graphics2D)g;
	        	if(overridecolor == 0)
	        	g2.setColor(new Color(min(255,50*fired_up),max(0,255-60*fired_up),0,255));
	        	Arc2D.Float arc1 = new Arc2D.Float(Arc2D.CHORD);         
			    arc1.setFrame(midpoint_x-rf,midpoint_y-(thickness/2), 2*rf,2*rf );  
			    arc1.setAngleStart(90-thetaf*2.3); 
			    arc1.setAngleExtent( thetaf*4.6 ); 

			    g2.fill(arc1);  
	        }
	        else if(paddleorientation == 3)
	        {
	            Graphics2D g2 = (Graphics2D)g;
	        	if(overridecolor == 0)
	        	g2.setColor(new Color(min(255,50*fired_up),max(0,255-60*fired_up),0,255));
	        	Arc2D.Float arc1 = new Arc2D.Float(Arc2D.CHORD);         
			    arc1.setFrame(midpoint_x-rf,midpoint_y-(2*rf)+(thickness/2), 2*rf,2*rf );  
			    arc1.setAngleStart(270-thetaf*2.3); 
			    arc1.setAngleExtent( thetaf*4.6 ); 
		
			    g2.fill(arc1);  
	        }
	        else if(paddleorientation == 2)
	        {
	            Graphics2D g2 = (Graphics2D)g;
	        	if(overridecolor == 0)
	        	g2.setColor(new Color(min(255,50*fired_up),max(0,255-60*fired_up),0,255));
	        	Arc2D.Float arc1 = new Arc2D.Float(Arc2D.CHORD);         
			    arc1.setFrame(midpoint_x-(length/2),midpoint_y-rs, 2*rs,2*rs );  
			    arc1.setAngleStart(180-thetas*2.3); 
			    arc1.setAngleExtent( thetas*4.6 ); 
		
			    g2.fill(arc1);  
	        }
	        else if(paddleorientation == 4)
	        {
	            Graphics2D g2 = (Graphics2D)g;
	        	if(overridecolor == 0)
	        	g2.setColor(new Color(min(255,50*fired_up),max(0,255-30*fired_up),20*fired_up,255));
	        	Arc2D.Float arc1 = new Arc2D.Float(Arc2D.CHORD);         
			    arc1.setFrame(midpoint_x-(2*rs)+(length/2),midpoint_y-rs, 2*rs,2*rs );  
			    arc1.setAngleStart(360-thetas*2.3); 
			    arc1.setAngleExtent( thetas*4.6 ); 
		
			    g2.fill(arc1);  
	        }
	    }
	    else
	    {
	    	Graphics2D g2d = (Graphics2D)g;
	    	GradientPaint gp1;
	    	Color c1;

	    	switch(lostlives){
	    		case 0:
	    		c1= Color_0;
	    		break;
	    		case 1:
	    		c1= Color_1;
	    		break;
	    		case 2:
	    		c1= Color_2;
	    		break;
	    		case 3:
	    		c1= Color_3;
	    		break;
	    		case 4:
	    		c1= Color_4;
	    		break;
	    		case 5:
	    		c1= Color_f;
	    		break;
	    		default: c1 = Color_0;
	    	}

	    	if(lostlives == 5)
	    	{
	    		if(deadFlag == 0)
		    		if (playerDeadOpenDabba < maxDabbaWidth)
		    		{
		    			g2d.setColor(new Color(0,0,0,255));
		    			playerDeadOpenDabba++;
		    			switch(lives){
		    				case 1:
		    				g2d.fillRect(midpoint_x-length/2,-playerDeadOpenDabba+midpoint_y+thickness/2,length,playerDeadOpenDabba);
		    				break;
		    				case 3:
		    				g2d.fillRect(midpoint_x-length/2,midpoint_y-thickness/2,length,playerDeadOpenDabba);
		    				break;
		    				case 2:
		    				g2d.fillRect(midpoint_x-length/2,midpoint_y-thickness/2,playerDeadOpenDabba,thickness);
		    				break;
		    				case 4:
		    				g2d.fillRect(midpoint_x+length/2-playerDeadOpenDabba,midpoint_y-thickness/2,playerDeadOpenDabba,thickness);
		    				break;
		    			}
		    		}
		    		else
		    			deadFlag ++;
	    		else if(deadFlag == 1)
	    		{
	    			if(waitTillPaddleFallInTheHole < 5*maxDabbaWidth)
	    				waitTillPaddleFallInTheHole ++;
	    			else
	    				deadFlag++;
		    		g2d.setColor(new Color(0,0,0,255));
		    		switch(lives){
		    			case 1:
		    			g2d.fillRect(midpoint_x-length/2,-playerDeadOpenDabba+midpoint_y+thickness/2,length,playerDeadOpenDabba);
		    			break;
		    			case 3:
		    			g2d.fillRect(midpoint_x-length/2,midpoint_y-thickness/2,length,playerDeadOpenDabba);
		    			break;
		    			case 2:
		    			g2d.fillRect(midpoint_x-length/2,midpoint_y-thickness/2,playerDeadOpenDabba,thickness);
		    			break;
		    			case 4:
		    			g2d.fillRect(midpoint_x+length/2-playerDeadOpenDabba,midpoint_y-thickness/2,playerDeadOpenDabba,thickness);
		    			break;
		    		}
	    		}
	    		else if(deadFlag == 2)
		    		if (playerDeadOpenDabba > 0)
		    		{
		    			g2d.setColor(new Color(0,0,0,255));
		    			playerDeadOpenDabba--;
		    			switch(lives){
		    				case 1:
		    				g2d.fillRect(midpoint_x-length/2,-playerDeadOpenDabba+midpoint_y+thickness/2,length,playerDeadOpenDabba);
		    				break;
		    				case 3:
		    				g2d.fillRect(midpoint_x-length/2,midpoint_y-thickness/2,length,playerDeadOpenDabba);
		    				break;
		    				case 2:
		    				g2d.fillRect(midpoint_x-length/2,midpoint_y-thickness/2,playerDeadOpenDabba,thickness);
		    				break;
		    				case 4:
		    				g2d.fillRect(midpoint_x+length/2-playerDeadOpenDabba,midpoint_y-thickness/2,playerDeadOpenDabba,thickness);
		    				break;
		    			}
		    		}
		    		else
		    			deadFlag ++;
	    	}

	    	switch(lives){
	    		case 1:
		    	gp1 = new GradientPaint(0,midpoint_y, Color_0, 0, midpoint_y+(thickness/3), c1, false);
		    	break;
		    	case 2:
		    	gp1 = new GradientPaint(midpoint_x,0, Color_0, midpoint_x-(length/3),0, c1, false);
		    	break;
		    	case 3:
		    	gp1 = new GradientPaint(0,midpoint_y, Color_0, 0, midpoint_y-(thickness/3), c1, false);
		    	break;
		    	case 4:
		    	gp1 = new GradientPaint(midpoint_x,0, Color_0, midpoint_x+(length/3),0, c1, false);
		    	break;
		    	default: gp1 = null;
	    	}
	    	if(deadFlag <3)
	    	{
	        	g2d.setPaint(gp1);
				g2d.fillRect(midpoint_x-(length/2), midpoint_y-(thickness/2),length,thickness);
			}

	    }
    }
//Normal functions
    public void fired_up(){fired_up++;}

    public void fired_down(){fired_up=0;}

    public void setFired(int f){fired_up=f;}

    public int getFired(){return min(fired_up,5);}

	public int getMidX(){return midpoint_x;}

	public int getMidY(){return midpoint_y;}

	public int getLength(){return length;}

	public int getThickness(){return thickness;}

	public void setMidX(int x){midpoint_x = x;}

	public void setMidY(int y){midpoint_y = y;}

}