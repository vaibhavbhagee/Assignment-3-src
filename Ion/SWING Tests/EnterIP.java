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

public class EnterIP extends JPanel implements MouseMotionListener, MouseListener{

	private static JFrame EnterIP_frame = new JFrame("EnterIP");
	private BlankArea blankArea = new BlankArea(new Color(20,11,231,188));
    private static EnterIP newContentPane;
    private int sel=0;
    private String ip_number_string="";
    private JoinLoad jl = new JoinLoad();
    int ref;

	Rectangle EnterIPButton;
	int EnterIPButton_x1,EnterIPButton_x2,EnterIPButton_y1,EnterIPButton_y2;

	Rectangle EnterIPButtonMulti;
	int EnterIPButton_x1m,EnterIPButton_x2m,EnterIPButton_y1m,EnterIPButton_y2m;

	EnterIP(){
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
 
        setPreferredSize(new Dimension(1920, 1000));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        EnterIP_frame.pack();

        EnterIPButton = new Rectangle((int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3),(int)(EnterIP_frame.getSize().getWidth()/5),(int)(EnterIP_frame.getSize().getHeight()/8) );
        EnterIPButton.overridecolor = 1;
        EnterIPButton.overridecolorwith = new Color(33,200,200,243);

		EnterIPButtonMulti = new Rectangle((int)(EnterIP_frame.getSize().getWidth()/2),(int)(2*EnterIP_frame.getSize().getHeight()/3),(int)(EnterIP_frame.getSize().getWidth()/5),(int)(EnterIP_frame.getSize().getHeight()/8) );
        EnterIPButtonMulti.overridecolor = 1;
        EnterIPButtonMulti.overridecolorwith = new Color(33,200,200,243);
		blankArea.newRect(EnterIPButton,EnterIPButtonMulti,null,null,null);

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
    		case 0: ip_number_string = ip_number_string +"0"; break;
            case 1: ip_number_string = ip_number_string +"1"; break;
            case 2: ip_number_string = ip_number_string +"2"; break;
            case 3: ip_number_string = ip_number_string +"3"; break;
            case 4: ip_number_string = ip_number_string +"4"; break;
            case 5: ip_number_string = ip_number_string +"5"; break;
            case 6: ip_number_string = ip_number_string +"6"; break;
            case 7: ip_number_string = ip_number_string +"7"; break;
            case 8: ip_number_string = ip_number_string +"8"; break;
            case 9: ip_number_string = ip_number_string +"9"; break;
            case 10: break;
    		case 11: break;
    		case 12: jl.launch(); EnterIP_frame.dispose(); break;
            case 13: ip_number_string = ip_number_string.substring(0,ip_number_string.length()-1); break;

    		default: break;
    	}
        blankArea.replaceEnterIP(new ShowString("ENTER IP:"+ip_number_string,(int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20)));
        blankArea.newRect(EnterIPButton,EnterIPButtonMulti,null,null,null);
    }

    public void mouseMoved(MouseEvent e) {

        if(e.getX()>EnterIPButton_x1 && e.getX()<EnterIPButton_x2 && e.getY()>EnterIPButton_y1 && e.getY()<EnterIPButton_y2)
    	{
    		EnterIPButton.overridecolorwith = new Color(33,23,200,243);
        	blankArea.newRect(EnterIPButton,EnterIPButtonMulti,null,null,null);
    		sel = 11;
    	}
    	else if (e.getX()>EnterIPButton_x1m && e.getX()<EnterIPButton_x2m && e.getY()>EnterIPButton_y1m && e.getY()<EnterIPButton_y2m)
    	{
    		EnterIPButtonMulti.overridecolorwith = new Color(33,23,200,243);
        	blankArea.newRect(EnterIPButton,EnterIPButtonMulti,null,null,null);
    		sel = 12;	
    	}
    	else
    	{
    		EnterIPButton.overridecolorwith = new Color(33,200,200,243);
    		EnterIPButtonMulti.overridecolorwith = new Color(33,200,200,243);
        	blankArea.newRect(EnterIPButton,EnterIPButtonMulti,null,null,null);
    		sel = 10;
    	}
        for(int j=0;j<10;j++)    
            if(e.getY()>EnterIPButton_y2 && e.getY()<EnterIPButton_y1m && e.getX()>(j*ref)/11 && e.getX()<((j+1)*ref)/11)
                sel=j;
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void reInitBoard() {

        ref = (int)EnterIP_frame.getSize().getWidth();
        EnterIP_frame.pack();

        EnterIPButton = new Rectangle((int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3),(int)(EnterIP_frame.getSize().getWidth()/5),(int)(EnterIP_frame.getSize().getHeight()/8) );
        EnterIPButton.overridecolor = 1;
        EnterIPButton.overridecolorwith = new Color(33,200,200,243);

        EnterIPButtonMulti = new Rectangle((int)(EnterIP_frame.getSize().getWidth()/2),(int)(2*EnterIP_frame.getSize().getHeight()/3),(int)(EnterIP_frame.getSize().getWidth()/5),(int)(EnterIP_frame.getSize().getHeight()/8) );
        EnterIPButtonMulti.overridecolor = 1;
        EnterIPButtonMulti.overridecolorwith = new Color(33,200,200,243);

        blankArea.newRect(EnterIPButton,EnterIPButtonMulti,null,null,null);
   		blankArea.addString(new ShowString("ENTER IP:"+ip_number_string,(int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20)));
   		blankArea.addString(new ShowString("JOIN GAME",(int)(EnterIP_frame.getSize().getWidth()/2),(int)(2*EnterIP_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20)));

        EnterIPButton_x1 = (int)(EnterIP_frame.getSize().getWidth()/2) - (int)(EnterIP_frame.getSize().getWidth()/5)/2;
        EnterIPButton_x2 = (int)(EnterIP_frame.getSize().getWidth()/2) + (int)(EnterIP_frame.getSize().getWidth()/5)/2; 
        EnterIPButton_y1 = (int)(EnterIP_frame.getSize().getHeight()/3)-(int)(EnterIP_frame.getSize().getHeight()/8)/2;
        EnterIPButton_y2 = (int)(EnterIP_frame.getSize().getHeight()/3)+(int)(EnterIP_frame.getSize().getHeight()/8)/2;

        EnterIPButton_x1m = (int)(EnterIP_frame.getSize().getWidth()/2) - (int)(EnterIP_frame.getSize().getWidth()/5)/2;
        EnterIPButton_x2m = (int)(EnterIP_frame.getSize().getWidth()/2) + (int)(EnterIP_frame.getSize().getWidth()/5)/2; 
        EnterIPButton_y1m = (int)(2*EnterIP_frame.getSize().getHeight()/3)-(int)(EnterIP_frame.getSize().getHeight()/8)/2;
        EnterIPButton_y2m = (int)(2*EnterIP_frame.getSize().getHeight()/3)+(int)(EnterIP_frame.getSize().getHeight()/8)/2;

    }

    private static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        EnterIP_frame.setResizable(false);

        //Create and set up the content pane.
        newContentPane = new EnterIP();
        newContentPane.setOpaque(true); //content panes must be opaque


        //Create and set up the window.
        EnterIP_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EnterIP_frame.addComponentListener(new ComponentListener() {
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

        EnterIP_frame.add(newContentPane);

        //Display the window.
        EnterIP_frame.pack();
        EnterIP_frame.setVisible(true);
    }

    public static void launch(){
        createAndShowGUI();
    }
}