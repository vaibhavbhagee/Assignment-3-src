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
    Circle b1 = new Circle(0,0,0);
    Graphics gg = getGraphics();

    public BlankArea(Color color) {
        setBackground(color);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    protected void paintComponent(Graphics g) {
        //System.out.println("paintComponent");
        super.paintComponent(g);
            if(b1!=null)b1.draw(g);
    }

    public void newCirc(Circle shreyan_da_ball) {
        b1 = shreyan_da_ball;
        //System.out.println("newCircle:" + b1.getMidX() + ":" + b1.getMidY());
        repaint();
        //update(gg);
        //paint(gg);
    }

    public Dimension getMinimumSize() {
        return minSize;
    }

    @Override
    public Dimension getPreferredSize() {
        return minSize;
    }
}