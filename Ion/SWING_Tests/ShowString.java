import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class ShowString{
	String str;
	int x,y;
	Color textcolor;
	Font font;

	public ShowString(String strr, int px, int py, Color col, Font fon)
	{
		str = strr;
		x = px;
		y = py;
		textcolor = col;
		font = fon;
	}

	public void draw(Graphics g)
	{
		g.setColor(textcolor);
		g.setFont(font);
        int width = g.getFontMetrics(font).stringWidth(str);
        if(str.length()>6 && (str.charAt(6) == '2' ||str.charAt(6)=='4') )
       	{
       		//System.out.println("rota");
       		//((Graphics2D)g).translate(-(x),-y);
       		Graphics2D g2x = (Graphics2D)g;
       		g2x.rotate(Math.toRadians(-90));
       		g2x.translate(-400,0);
			g2x.drawString(str,x-width/2,y);
			g2x.translate(400,0);
       		g2x.rotate(Math.toRadians(90));
       	}
       	else if(str.length()>6 && (str.charAt(6) == '3'))
       	{
			Graphics2D g2x = (Graphics2D)g;
       		//g2x.translate(-400,0);
			g2x.drawString(str,x-width/2,y);
			//g2x.translate(400,0);
       	}
       	else if(str.length()>6 && (str.charAt(6) == '1'))
       	{
       		g.setFont(new Font("Serif",Font.BOLD,50));
       		if(str.substring(16,17).equals("1"))
       			g.setColor(new Color(255,0,0,255));
       		g.drawString("Lives:"+str.substring(16,17),x,y);
       	}
       	else
		g.drawString(str,x-width/2,y);
	}

	public void setstr(String s){str = s;}

	public String retstr(){return str;}
}