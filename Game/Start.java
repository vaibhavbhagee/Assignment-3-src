import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import java.awt.Font;

public class Start extends JPanel implements MouseMotionListener, MouseListener{

	private  static JFrame start_frame = new JFrame("Start");
	private BlankArea blankArea = new BlankArea(new Color(20,11,231,188));
    private  static Start newContentPane;
    private int sel=0;
	//MouseClickOrMotion m = new MouseClickOrMotion();
	//MultiOpt mu = new MultiOpt();

	Rectangle startButton;
	int startButton_x1,startButton_x2,startButton_y1,startButton_y2;

    Rectangle startButtonM;
    int startMx1,startMx2,startMy1,startMy2;

    Rectangle startButtonH;
    int startHx1,startHx2,startHy1,startHy2;

	Rectangle startButtonMulti;
	int startButton_x1m,startButton_x2m,startButton_y1m,startButton_y2m;

	Start(){
		super(new GridBagLayout());
        GridBagLayout gridbag = (GridBagLayout)getLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setBackground(new Color(0,0,0,255));

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridheight = GridBagConstraints.REMAINDER;

        c.insets = new Insets(1, 1, 1, 1);
        gridbag.setConstraints(blankArea, c);
        blankArea.setBackground(new Color(0,100,0,188));
        add(blankArea);
      
        c.insets = new Insets(0, 0, 0, 0);
        c.gridheight = 200;
        
        blankArea.addMouseMotionListener(this);
        blankArea.addMouseListener(this);
 
        setPreferredSize(new Dimension(960, 700));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        start_frame.pack();

        startButton = new Rectangle((int)(start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/3),(int)(start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/8),0 );
        startButton.overridecolor = 1;
        startButton.overridecolorwith = new Color(33,200,200,243);

        startButtonM = new Rectangle((int)(2*start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/3),(int)(start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/8),0 );
        startButtonM.overridecolor = 1;
        startButtonM.overridecolorwith = new Color(33,200,200,243);

        startButtonH = new Rectangle((int)(3*start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/3),(int)(start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/8),0 );
        startButtonH.overridecolor = 1;
        startButtonH.overridecolorwith = new Color(33,200,200,243);

		startButtonMulti = new Rectangle((int)(start_frame.getSize().getWidth()/2),(int)(2*start_frame.getSize().getHeight()/3),(int)(start_frame.getSize().getWidth()/5),(int)(start_frame.getSize().getHeight()/8),0 );
        startButtonMulti.overridecolor = 1;
        startButtonMulti.overridecolorwith = new Color(33,200,200,243);
		blankArea.newRect(startButton,startButtonMulti,startButtonM,startButtonH,null);

	}

	public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    	switch(sel){
    		case 0: break;
    		case 1: (new MouseClickOrMotion("",0,0,null,1)).launch(); start_frame.dispose(); break;
    		case 2: (new MultiOpt()).launch(); start_frame.dispose(); break;
            case 3: (new MouseClickOrMotion("",0,0,null,2)).launch(); start_frame.dispose(); break;
            case 4: (new MouseClickOrMotion("",0,0,null,3)).launch(); start_frame.dispose(); break;
    		default: break;
    	}
    }

    public void mouseMoved(MouseEvent e) {
    	if(e.getX()>startButton_x1 && e.getX()<startButton_x2 && e.getY()>startButton_y1 && e.getY()<startButton_y2)
    	{
            startButton.overridecolorwith = new Color(33,200,200,243);
            startButtonMulti.overridecolorwith = new Color(33,200,200,243);
            startButtonH.overridecolorwith = new Color(33,200,200,243);
            startButtonM.overridecolorwith = new Color(33,200,200,243);
    		startButton.overridecolorwith = new Color(33,23,200,243);
        blankArea.newRect(startButton,startButtonMulti,startButtonM,startButtonH,null);
    		sel = 1;
            blankArea.reDraw();
    	}
    	else if (e.getX()>startButton_x1m && e.getX()<startButton_x2m && e.getY()>startButton_y1m && e.getY()<startButton_y2m)
    	{
            startButton.overridecolorwith = new Color(33,200,200,243);
            startButtonMulti.overridecolorwith = new Color(33,200,200,243);
            startButtonH.overridecolorwith = new Color(33,200,200,243);
            startButtonM.overridecolorwith = new Color(33,200,200,243);
            startButtonMulti.overridecolorwith = new Color(33,23,200,243);
        blankArea.newRect(startButton,startButtonMulti,startButtonM,startButtonH,null);
    		sel = 2;	
            blankArea.reDraw();
    	}
        else if (e.getX()>startMx1 && e.getX()<startMx2 && e.getY()>startMy1 && e.getY()<startMy2)
        {
            startButton.overridecolorwith = new Color(33,200,200,243);
            startButtonMulti.overridecolorwith = new Color(33,200,200,243);
            startButtonH.overridecolorwith = new Color(33,200,200,243);
            startButtonM.overridecolorwith = new Color(33,200,200,243);
            startButtonM.overridecolorwith = new Color(33,23,200,243);
        blankArea.newRect(startButton,startButtonMulti,startButtonM,startButtonH,null);
            sel = 3;    
            blankArea.reDraw();
        }
        else if (e.getX()>startHx1 && e.getX()<startHx2 && e.getY()>startHy1 && e.getY()<startHy2)
        {
            startButton.overridecolorwith = new Color(33,200,200,243);
            startButtonMulti.overridecolorwith = new Color(33,200,200,243);
            startButtonH.overridecolorwith = new Color(33,200,200,243);
            startButtonM.overridecolorwith = new Color(33,200,200,243);
            startButtonH.overridecolorwith = new Color(33,23,200,243);
        blankArea.newRect(startButton,startButtonMulti,startButtonM,startButtonH,null);
            sel = 4;    
            blankArea.reDraw();
        }
    	else
    	{
    		startButton.overridecolorwith = new Color(33,200,200,243);
    		startButtonMulti.overridecolorwith = new Color(33,200,200,243);
            startButtonH.overridecolorwith = new Color(33,200,200,243);
            startButtonM.overridecolorwith = new Color(33,200,200,243);
        blankArea.newRect(startButton,startButtonMulti,startButtonM,startButtonH,null);
    		sel = 0;
            blankArea.reDraw();
    	}
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void reInitBoard() {

        start_frame.pack();

        startButton = new Rectangle((int)(start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/3),(int)(start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/8),0 );
        startButton.overridecolor = 1;
        startButton.overridecolorwith = new Color(33,200,200,243);

        startButtonM = new Rectangle((int)(2*start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/3),(int)(start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/8),0 );
        startButtonM.overridecolor = 1;
        startButtonM.overridecolorwith = new Color(33,200,200,243);

        startButtonH = new Rectangle((int)(3*start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/3),(int)(start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/8),0 );
        startButtonH.overridecolor = 1;
        startButtonH.overridecolorwith = new Color(33,200,200,243);

        startButtonMulti = new Rectangle((int)(start_frame.getSize().getWidth()/2),(int)(2*start_frame.getSize().getHeight()/3),(int)(start_frame.getSize().getWidth()/3),(int)(start_frame.getSize().getHeight()/8),0 );
        startButtonMulti.overridecolor = 1;
        startButtonMulti.overridecolorwith = new Color(33,200,200,243);

   		blankArea.addString(new ShowString("START EASY GAME",(int)(start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
   		blankArea.addString(new ShowString("START MULTIPLAYER GAME",(int)(start_frame.getSize().getWidth()/2),(int)(2*start_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
        blankArea.addString(new ShowString("START MEDIUM GAME",(int)(2*start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
        blankArea.addString(new ShowString("START HARD GAME",(int)(3*start_frame.getSize().getWidth()/4),(int)(start_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));

        startButton_x1 = (int)(start_frame.getSize().getWidth()/4) - (int)(start_frame.getSize().getWidth()/4)/2;
        startButton_x2 = (int)(start_frame.getSize().getWidth()/4) + (int)(start_frame.getSize().getWidth()/4)/2; 
        startButton_y1 = (int)(start_frame.getSize().getHeight()/3)-(int)(start_frame.getSize().getHeight()/8)/2;
        startButton_y2 = (int)(start_frame.getSize().getHeight()/3)+(int)(start_frame.getSize().getHeight()/8)/2;

        startButton_x1m = (int)(start_frame.getSize().getWidth()/2) - (int)(start_frame.getSize().getWidth()/5)/2;
        startButton_x2m = (int)(start_frame.getSize().getWidth()/2) + (int)(start_frame.getSize().getWidth()/5)/2; 
        startButton_y1m = (int)(2*start_frame.getSize().getHeight()/3)-(int)(start_frame.getSize().getHeight()/8)/2;
        startButton_y2m = (int)(2*start_frame.getSize().getHeight()/3)+(int)(start_frame.getSize().getHeight()/8)/2;

        startMx1 = (int)(2*start_frame.getSize().getWidth()/4) - (int)(start_frame.getSize().getWidth()/4)/2;
        startMx2 = (int)(2*start_frame.getSize().getWidth()/4) + (int)(start_frame.getSize().getWidth()/4)/2; 
        startMy1 = (int)(start_frame.getSize().getHeight()/3)-(int)(start_frame.getSize().getHeight()/8)/2;
        startMy2 = (int)(start_frame.getSize().getHeight()/3)+(int)(start_frame.getSize().getHeight()/8)/2;

        startHx1 = (int)(3*start_frame.getSize().getWidth()/4) - (int)(start_frame.getSize().getWidth()/4)/2;
        startHx2 = (int)(3*start_frame.getSize().getWidth()/4) + (int)(start_frame.getSize().getWidth()/4)/2; 
        startHy1 = (int)(start_frame.getSize().getHeight()/3)-(int)(start_frame.getSize().getHeight()/8)/2;
        startHy2 = (int)(start_frame.getSize().getHeight()/3)+(int)(start_frame.getSize().getHeight()/8)/2;

        blankArea.newRect(startButton,startButtonMulti,startButtonM,startButtonH,null);

    }

    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        start_frame.setResizable(false);

        //Create and set up the content pane.
        newContentPane = new Start();
        newContentPane.setOpaque(true); //content panes must be opaque


        //Create and set up the window.
        start_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        start_frame.addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                newContentPane.reInitBoard();    
            }
            public void componentHidden(ComponentEvent e) {
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentShown(ComponentEvent e) {
            }
        });

        start_frame.add(newContentPane);

        //Display the window.
        start_frame.pack();
        start_frame.setVisible(true);
    }

    public static void launch()
    {
        createAndShowGUI();
    }

	public static void main(String args[])
	{
		//Start s = new Start();
        createAndShowGUI();
	}
}