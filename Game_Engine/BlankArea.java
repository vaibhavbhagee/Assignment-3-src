import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BlankArea extends JLabel {
    Dimension minSize = new Dimension(100, 100);
    Rectangle r1,r2,r3,r4,r5 = new Rectangle(0,0,0,0,0);
    Circle c1 = new Circle(0,0,0);
    Circle[] trail = new Circle[10];
    boolean trail_present = false;
    List<ShowString> stringsToDisplay = new ArrayList<ShowString>();
    List<Rectangle> digits = new ArrayList<Rectangle>();

    public void trail_on(){trail_present=true;}

    public void trail_off(){trail_present=false;}

    public void addString(ShowString s)
    {
        stringsToDisplay.add(s);
    }

    public void replaceEnterIP(ShowString s)
    {
        for(int i=0;i<stringsToDisplay.size();i++)
        {
            if ( stringsToDisplay.get(i).retstr().substring(0,9) .equals("ENTER IP:") )
            {
                stringsToDisplay.remove(i);
            }    
        }
        stringsToDisplay.add(s);
    }

    public BlankArea(Color color) {
        setBackground(color);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.black));
        for(int i=0;i<10;i++)
        {
            trail[i] = new Circle(0,0,40-4*i);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

            g.setColor(new Color(233,233,235,235));
            if(r1!=null)r1.draw(g);
            if(r2!=null)r2.draw(g);
            if(r3!=null)r3.draw(g);
            if(r4!=null)r4.draw(g);
            if(r5!=null)r5.draw(g);

            g.setColor(new Color(124,124,124,124));
            if(trail!=null && trail_present)
            {
                for(int i=9; i>0;i--)
                {
                    trail[i].setMidX(trail[i-1].getMidX());
                    trail[i].setMidY(trail[i-1].getMidY());
                    trail[i].draw(g);
                }
                trail[0].setMidX(c1.getMidX());
                trail[0].setMidY(c1.getMidY());
                trail[0].draw(g);
            }
            g.setColor(new Color(23,12,92,232));
            if(c1!=null)c1.draw(g);

            for(ShowString s:stringsToDisplay){
                s.draw(g);
            }

            for(Rectangle r:digits){
                r.draw(g);
            }
    }

    public void newCirc(Circle ball)
    {
        c1 = ball;
        repaint(10);
    }

    public void newRect(Rectangle board,Rectangle bottom, Rectangle left, Rectangle right, Rectangle top) {
        r1 = board;
        r2 = bottom;
        r3 = left;
        r4 = right;
        r5 = top;
        repaint(10);
    }

    public void manyNewRects(List<Rectangle> alr)
    {
        digits = alr;
        repaint(10);
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    @Override
    public Dimension getPreferredSize() {
        return minSize;
    }
}