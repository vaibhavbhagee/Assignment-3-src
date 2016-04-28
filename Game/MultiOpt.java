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

public class MultiOpt extends JPanel implements MouseMotionListener, MouseListener{

	private static JFrame MultiOpt_frame = new JFrame("MultiOpt");
	private BlankArea blankArea = new BlankArea(new Color(20,11,231,188));
    private static MultiOpt newContentPane;
    private int sel=0;
	//private StartLoad sl = new StartLoad();
    //private EnterIP ei = new EnterIP();
    //MouseClickOrMotion m = new MouseClickOrMotion();

	Rectangle MultiOptButton;
	int MultiOptButton_x1,MultiOptButton_x2,MultiOptButton_y1,MultiOptButton_y2;

	Rectangle MultiOptButtonMulti;
	int MultiOptButton_x1m,MultiOptButton_x2m,MultiOptButton_y1m,MultiOptButton_y2m;

	MultiOpt(){
		super(new GridBagLayout());
        GridBagLayout gridbag = (GridBagLayout)getLayout();
        GridBagConstraints c = new GridBagConstraints();
        this.setBackground(new Color(0,0,123,255));

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridheight = GridBagConstraints.REMAINDER;

        c.insets = new Insets(1, 1, 1, 1);
        gridbag.setConstraints(blankArea, c);
        blankArea.setBackground(new Color(20,11,231,188));
        add(blankArea);
      
        c.insets = new Insets(0, 0, 0, 0);
        c.gridheight = 200;
        
        blankArea.addMouseMotionListener(this);
        blankArea.addMouseListener(this);
 
        setPreferredSize(new Dimension(960, 700));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        MultiOpt_frame.pack();

        MultiOptButton = new Rectangle((int)(MultiOpt_frame.getSize().getWidth()/2),(int)(MultiOpt_frame.getSize().getHeight()/3),(int)(MultiOpt_frame.getSize().getWidth()/5),(int)(MultiOpt_frame.getSize().getHeight()/8),0 );
        MultiOptButton.overridecolor = 1;
        MultiOptButton.overridecolorwith = new Color(33,200,200,243);

		MultiOptButtonMulti = new Rectangle((int)(MultiOpt_frame.getSize().getWidth()/2),(int)(2*MultiOpt_frame.getSize().getHeight()/3),(int)(MultiOpt_frame.getSize().getWidth()/5),(int)(MultiOpt_frame.getSize().getHeight()/8),0 );
        MultiOptButtonMulti.overridecolor = 1;
        MultiOptButtonMulti.overridecolorwith = new Color(33,200,200,243);
		blankArea.newRect(MultiOptButton,MultiOptButtonMulti,null,null,null);

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
    		case 1: (new StartLoad()).launch(); MultiOpt_frame.dispose(); break;
    		case 2: (new EnterIP()).launch(); MultiOpt_frame.dispose(); break;
    		default: break;
    	}
    }

    public void mouseMoved(MouseEvent e) {
    	if(e.getX()>MultiOptButton_x1 && e.getX()<MultiOptButton_x2 && e.getY()>MultiOptButton_y1 && e.getY()<MultiOptButton_y2)
    	{
    		MultiOptButton.overridecolorwith = new Color(33,23,200,243);
        	blankArea.newRect(MultiOptButton,MultiOptButtonMulti,null,null,null);
    		sel = 1;
            blankArea.reDraw();
    	}
    	else if (e.getX()>MultiOptButton_x1m && e.getX()<MultiOptButton_x2m && e.getY()>MultiOptButton_y1m && e.getY()<MultiOptButton_y2m)
    	{
    		MultiOptButtonMulti.overridecolorwith = new Color(33,23,200,243);
        	blankArea.newRect(MultiOptButton,MultiOptButtonMulti,null,null,null);
    		sel = 2;	
            blankArea.reDraw();
    	}
    	else
    	{
    		MultiOptButton.overridecolorwith = new Color(33,200,200,243);
    		MultiOptButtonMulti.overridecolorwith = new Color(33,200,200,243);
        	blankArea.newRect(MultiOptButton,MultiOptButtonMulti,null,null,null);
    		sel = 0;
            blankArea.reDraw();
    	}
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void reInitBoard() {

        MultiOpt_frame.pack();

        MultiOptButton = new Rectangle((int)(MultiOpt_frame.getSize().getWidth()/2),(int)(MultiOpt_frame.getSize().getHeight()/3),(int)(MultiOpt_frame.getSize().getWidth()/5),(int)(MultiOpt_frame.getSize().getHeight()/8),0 );
        MultiOptButton.overridecolor = 1;
        MultiOptButton.overridecolorwith = new Color(33,200,200,243);

        MultiOptButtonMulti = new Rectangle((int)(MultiOpt_frame.getSize().getWidth()/2),(int)(2*MultiOpt_frame.getSize().getHeight()/3),(int)(MultiOpt_frame.getSize().getWidth()/5),(int)(MultiOpt_frame.getSize().getHeight()/8),0 );
        MultiOptButtonMulti.overridecolor = 1;
        MultiOptButtonMulti.overridecolorwith = new Color(33,200,200,243);

        blankArea.newRect(MultiOptButton,MultiOptButtonMulti,null,null,null);
   		blankArea.addString(new ShowString("START A GAME",(int)(MultiOpt_frame.getSize().getWidth()/2),(int)(MultiOpt_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
   		blankArea.addString(new ShowString("JOIN A GAME",(int)(MultiOpt_frame.getSize().getWidth()/2),(int)(2*MultiOpt_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));

        MultiOptButton_x1 = (int)(MultiOpt_frame.getSize().getWidth()/2) - (int)(MultiOpt_frame.getSize().getWidth()/5)/2;
        MultiOptButton_x2 = (int)(MultiOpt_frame.getSize().getWidth()/2) + (int)(MultiOpt_frame.getSize().getWidth()/5)/2; 
        MultiOptButton_y1 = (int)(MultiOpt_frame.getSize().getHeight()/3)-(int)(MultiOpt_frame.getSize().getHeight()/8)/2;
        MultiOptButton_y2 = (int)(MultiOpt_frame.getSize().getHeight()/3)+(int)(MultiOpt_frame.getSize().getHeight()/8)/2;

        MultiOptButton_x1m = (int)(MultiOpt_frame.getSize().getWidth()/2) - (int)(MultiOpt_frame.getSize().getWidth()/5)/2;
        MultiOptButton_x2m = (int)(MultiOpt_frame.getSize().getWidth()/2) + (int)(MultiOpt_frame.getSize().getWidth()/5)/2; 
        MultiOptButton_y1m = (int)(2*MultiOpt_frame.getSize().getHeight()/3)-(int)(MultiOpt_frame.getSize().getHeight()/8)/2;
        MultiOptButton_y2m = (int)(2*MultiOpt_frame.getSize().getHeight()/3)+(int)(MultiOpt_frame.getSize().getHeight()/8)/2;

    }

    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        MultiOpt_frame.setResizable(false);

        //Create and set up the content pane.
        newContentPane = new MultiOpt();
        newContentPane.setOpaque(true); //content panes must be opaque


        //Create and set up the window.
        MultiOpt_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MultiOpt_frame.addComponentListener(new ComponentListener() {
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

        MultiOpt_frame.add(newContentPane);

        //Display the window.
        MultiOpt_frame.pack();
        MultiOpt_frame.setVisible(true);
    }

    public static void launch(){
        createAndShowGUI();
    }
}