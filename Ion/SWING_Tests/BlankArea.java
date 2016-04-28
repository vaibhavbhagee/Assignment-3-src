import javax.swing.*;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import java.util.concurrent.CopyOnWriteArrayList;

import java.awt.Font;

import java.util.*;

public class BlankArea extends JLabel {
    Dimension gameOverDim = new Dimension(100, 100);
    int board_side;
    Rectangle r1,r2,r3,r4,r5 = new Rectangle(0,0,0,0,0);
    Circle c1 = new Circle(0,0,0);
    Circle[] trail = new Circle[10];
    boolean trail_present = false;
    List<ShowString> stringsToDisplay = new CopyOnWriteArrayList<ShowString>();
    List<Rectangle> digits = new ArrayList<Rectangle>();
    List<ShowString> numerals = new ArrayList<ShowString>();
    List<Rectangle> lives = new ArrayList<Rectangle>();
    
    ShowString gameOverMessage;
    private boolean gameOverSorry = false;
    public Rectangle redZone1;
    public Rectangle redZone2;
    public Rectangle redZone3;
    public Rectangle redZone4;
    public boolean renderDone=true;
    int i=0;

    public int gWidth=0;
    public int gHeight=0;
    public int bSize=0;

    public void setGameOverDim(Dimension d, int bs)
    {
        gameOverDim = d;
        board_side = bs;
        gameOverMessage = new ShowString("Game Over. Kat Gaya Aapka :(",(int)(gameOverDim.getWidth()/2),(int)(gameOverDim.getHeight()/2),new Color(255,0,0,255),new Font("Serif",Font.BOLD,20),"");
    }

    public void showGameOverLol()
    {
        gameOverSorry = true;
    }

    public void setRed()
    {
        redZone1 = new Rectangle(gWidth/2,gHeight/2,bSize,bSize,0);
        redZone1.setLifeRect(1);
        redZone3 = new Rectangle(gWidth/2,gHeight/2,bSize,bSize,0);
        redZone3.setLifeRect(3);
        redZone2 = new Rectangle(gWidth/2,gHeight/2,bSize,bSize,0);
        redZone2.setLifeRect(2);
        redZone4 = new Rectangle(gWidth/2,gHeight/2,bSize,bSize,0);
        redZone4.setLifeRect(4);
    }

    public void trail_on(){trail_present=true;}

    public void trail_off(){trail_present=false;}

    public void addString(ShowString s)
    {
        stringsToDisplay.add(s);
    }

    public void addRedRectangles(int a, int b, int c, int d)
    {
        redZone4.lostLifeSet(d);
        redZone2.lostLifeSet(b);
        redZone3.lostLifeSet(c);
        redZone1.lostLifeSet(a);
    }

    public void replaceEnterIP(ShowString s)
    {
        for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {   
            ShowString sz = iter.next();
            if(sz.retstr().length()>=9)
                {//System.out.println(sz.retstr().substring(0,9));
                    if(sz.retstr().substring(0,9).equals("ENTER IP:"))
                    sz.setstr(s.retstr());
            }
        }
    }

    public void replaceName(ShowString s)
    {
        for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {   
            ShowString sz = iter.next();
            if(sz.retstr().length()>=5)
                {//System.out.println(sz.retstr().substring(0,5));
                    if(sz.retstr().substring(0,5).equals("NAME:"))
                    sz.setstr(s.retstr());
            }
        }
    }

    public void replaceLives1(ShowString s)
    {
        
        for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {
            ShowString sz = iter.next();
            if(sz.retstr().length()>=16)
                if(sz.retstr().substring(0,16).equals("PLAYER1:: lives="))
                    sz.setstr(s.retstr());
        }
       
    }

    public void replaceLives2(ShowString s)
    {
         for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {
            ShowString sz = iter.next();
            if(sz.retstr().length()>=16)
                if(sz.retstr().substring(0,16).equals("PLAYER2:: lives="))
                    sz.setstr(s.retstr());
        }
       
    }

    public void replaceLives3(ShowString s)
    {
         for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {
            ShowString sz = iter.next();
            if(sz.retstr().length()>=16)
                if(sz.retstr().substring(0,16).equals("PLAYER3:: lives="))
                    sz.setstr(s.retstr());
        }
       
    }

    public void replaceLives4(ShowString s)
    {
        for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {
            ShowString sz = iter.next();
            if(sz.retstr().length()>=16)
                if(sz.retstr().substring(0,16).equals("PLAYER4:: lives="))
                    sz.setstr(s.retstr());
        }
       
    }

    public void replacePeer1(ShowString s)
    {
         for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {
            ShowString sz = iter.next();
            if(sz!=null&& sz.retstr()!=null)
            {System.out.println(sz.retstr()+":(");
                if(sz.retstr().length()>=5)
                if(sz.retstr().substring(0,5).equals("PEER1"))
                    sz.setstr(s.retstr());}
        }       
    }

    public void replacePeer2(ShowString s)
    {
         for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {
            ShowString sz = iter.next();
            if(sz!=null && sz.retstr()!=null )    
                if(sz.retstr().length()>=5)
                    if(sz.retstr().substring(0,5).equals("PEER2"))
                        sz.setstr(s.retstr());
        }       
    }

        public void replacePeer3(ShowString s)
    {
         for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {
            ShowString sz = iter.next();
            if(sz!=null&& sz.retstr()!=null)
            if(sz.retstr().length()>=5)
                if(sz.retstr().substring(0,5).equals("PEER3"))
                    sz.setstr(s.retstr());
        }       
    }

        public void replacePeer4(ShowString s)
    {
         for(Iterator<ShowString> iter = stringsToDisplay.iterator();iter.hasNext();)
        {
            ShowString sz = iter.next();
            if(sz!=null&& sz.retstr()!=null)
            if(sz.retstr().length()>=5)
                if(sz.retstr().substring(0,5).equals("PEER4"))
                    sz.setstr(s.retstr());
        }       
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

    public void setTrails()
    {
        for(int i=9; i>0;i--)
                {
                    trail[i].setMidX(trail[i-1].getMidX());
                    trail[i].setMidY(trail[i-1].getMidY());
                }
                trail[0].setMidX(c1.getMidX());
                trail[0].setMidY(c1.getMidY());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            long l = System.nanoTime();
            //System.out.println("Step1:"+l);

            g.setColor(new Color(233,233,235,235));
            if(r1!=null)r1.draw(g);
 
            if(redZone1!=null)redZone1.draw(g);
            if(redZone2!=null)redZone2.draw(g);
            if(redZone3!=null)redZone3.draw(g);
            if(redZone4!=null)redZone4.draw(g);

            g.setColor(new Color(233,233,235,235));
            if(r2!=null)r2.draw(g);
            if(r3!=null)r3.draw(g);
            if(r4!=null)r4.draw(g);
            if(r5!=null)r5.draw(g);

            g.setColor(new Color(124,124,124,124));
            if(trail!=null && trail_present)
            {
                for(int i=9; i>=0;i--)
                    trail[i].draw(g);
            }
            g.setColor(new Color(23,12,92,232));
            if(c1!=null)c1.draw(g);

            for(ShowString s:stringsToDisplay){
                s.draw(g);
            }

            for(Rectangle r:digits){
                r.draw(g);
            }
            for(ShowString n:numerals){
                n.draw(g);
                //System.out.println(n.retstr());
            }
            i++;
            
            if(gameOverSorry)
                gameOverMessage.draw(g);

            renderDone = true;
            
    }

    public void newCirc(Circle ball)
    {
        c1 = ball;
        //repaint(10);
    }

    public void newRect(Rectangle board,Rectangle bottom, Rectangle left, Rectangle right, Rectangle top) {
        r1 = board;
        r2 = bottom;
        r3 = left;
        r4 = right;
        r5 = top;
        //repaint(10);
    }

    public void manyNewRects(List<Rectangle> alr)
    {
        digits = alr;
        //repaint(10);
    }

    public void manyNewerRects(List<ShowString> num)
    {
        numerals = num;
    }

/*    public Dimension getMinimumSize() {
        return minSize;
    }

    @Override
    public Dimension getPreferredSize() {
        return minSize;
    }
*/
    public void reDraw()
    {
        //repaint(1,180,0,832,832);
        repaint(1);
    }

    public void reDraw2()
    {
        repaint(1,0,50,180,300);
    }
}