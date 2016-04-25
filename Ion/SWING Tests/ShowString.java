import java.awt.Color;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Graphics;

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
		g.drawString(str,x-width/2,y);
	}

	public void setstr(String s){str = s;}

	public String retstr(){return str;}
}