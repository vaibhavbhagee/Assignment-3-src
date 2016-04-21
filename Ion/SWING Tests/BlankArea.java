/*
 * BlankArea.java is used by:
 *    MouseEventDemo.java.
 *    MouseMotionEventDemo.java
 *    MouseWheelEventDemo.java
 */

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;

public class BlankArea extends JLabel {
    Dimension minSize = new Dimension(100, 100);
    Rectangle r1,r2,r3,r4,r5 = new Rectangle(0,0,0,0);
    Circle c1 = new Circle(0,0,0);
    Circle[] trail = new Circle[10];

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
        //System.out.println("paintComponent");
        super.paintComponent(g);
            if(r1!=null)r1.draw(g);
            if(r2!=null)r2.draw(g);
            if(r3!=null)r3.draw(g);
            if(r4!=null)r4.draw(g);
            if(r5!=null)r5.draw(g);
            if(c1!=null)c1.draw(g);
            if(trail!=null)
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
    }

    public void newCirc(Circle ball)
    {
        c1 = ball;
        //System.out.println("newCirc function");
        repaint();
    }

    public void newRect(Rectangle board,Rectangle bottom, Rectangle left, Rectangle right, Rectangle top) {
        r1 = board;
        r2 = bottom;
        r3 = left;
        r4 = right;
        r5 = top;
        //System.out.println("addRectangle");
        repaint();
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    @Override
    public Dimension getPreferredSize() {
        return minSize;
    }
}