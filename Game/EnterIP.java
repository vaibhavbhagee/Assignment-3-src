import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import java.awt.Font;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EnterIP extends JPanel implements MouseMotionListener, MouseListener, KeyListener{

	private static JFrame EnterIP_frame = new JFrame("EnterIP");
	private BlankArea blankArea = new BlankArea(new Color(20,11,231,188));
    private static EnterIP newContentPane;
    private int sel=0;
    private String ip_number_string="";
    //private JoinLoad jl = new JoinLoad();
    private String nameEntered ="";
    int ref;

	Rectangle EnterIPButton;
	int EnterIPButton_x1,EnterIPButton_x2,EnterIPButton_y1,EnterIPButton_y2;

	Rectangle EnterIPButtonMulti;
	int EnterIPButton_x1m,EnterIPButton_x2m,EnterIPButton_y1m,EnterIPButton_y2m;

    Rectangle BackButton;

    List<ShowString> bhagee = new ArrayList<ShowString>(); 

    List<Rectangle> digits = new ArrayList<Rectangle>();

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
        blankArea.addKeyListener(this);
 
        setPreferredSize(new Dimension(960, 700));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        EnterIP_frame.pack();

        EnterIPButton = new Rectangle((int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3),(int)(EnterIP_frame.getSize().getWidth()),(int)(EnterIP_frame.getSize().getHeight()/8),0 );
        EnterIPButton.overridecolor = 1;
        EnterIPButton.overridecolorwith = new Color(33,200,200,243);

		EnterIPButtonMulti = new Rectangle((int)(EnterIP_frame.getSize().getWidth()/2),(int)(2*EnterIP_frame.getSize().getHeight()/3),(int)(EnterIP_frame.getSize().getWidth()/5),(int)(EnterIP_frame.getSize().getHeight()/8),0 );
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
            case 12: break;
    		case 13: break;
    		case 14: (new JoinLoad(ip_number_string, nameEntered)).launch(); EnterIP_frame.dispose(); break;
            case 11: if(ip_number_string.length()>0) 
                        ip_number_string = ip_number_string.substring(0,ip_number_string.length()-1);
                     break;
            case 10: ip_number_string = ip_number_string +"."; break;
            case 30: (new MultiOpt()).launch(); EnterIP_frame.dispose(); break;

    		default: break;
    	}
        blankArea.replaceEnterIP(new ShowString("ENTER IP:"+ip_number_string,(int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
        blankArea.newRect(EnterIPButton,EnterIPButtonMulti,BackButton,null,null);
        blankArea.reDraw();
        blankArea.requestFocus();
    }

    public void mouseMoved(MouseEvent e) {

        if(e.getX()>EnterIPButton_x1 && e.getX()<EnterIPButton_x2 && e.getY()>EnterIPButton_y1 && e.getY()<EnterIPButton_y2)
    	{
    		EnterIPButton.overridecolorwith = new Color(33,23,200,243);
        	blankArea.newRect(EnterIPButton,EnterIPButtonMulti,BackButton,null,null);
    		sel = 13;
    	}
    	else if (e.getX()>EnterIPButton_x1m && e.getX()<EnterIPButton_x2m && e.getY()>EnterIPButton_y1m && e.getY()<EnterIPButton_y2m)
    	{
    		EnterIPButtonMulti.overridecolorwith = new Color(33,23,200,243);
        	blankArea.newRect(EnterIPButton,EnterIPButtonMulti,BackButton,null,null);
    		sel = 14;	
    	}
        else if (e.getX()<60 && e.getY()<60)
        {
            BackButton.overridecolorwith = new Color(33,23,200,243);
            blankArea.newRect(EnterIPButton,EnterIPButtonMulti,BackButton,null,null);
            sel = 30;
            blankArea.reDraw();

        }
    	else
    	{
    		EnterIPButton.overridecolorwith = new Color(33,200,200,243);
    		EnterIPButtonMulti.overridecolorwith = new Color(33,200,200,243);
        	blankArea.newRect(EnterIPButton,EnterIPButtonMulti,BackButton,null,null);
            for(int i=0;i<digits.size();i++)
            {
                digits.get(i).overridecolorwith =new Color(20,11,231,243);
            }
            blankArea.manyNewRects(digits);
    		sel = 12;
    	}
        for(int j=0;j<12;j++)    
            if(e.getY()>EnterIPButton_y2 && e.getY()<EnterIPButton_y1m && e.getX()>(j*ref)/12 && e.getX()<((j+1)*ref)/12)
            {
                sel=j;
                digits.get(j).overridecolorwith =new Color(0,0,0,255);
                blankArea.manyNewRects(digits);
            }    

            blankArea.reDraw();
            blankArea.requestFocus();


    }

    public void mouseDragged(MouseEvent e) {
    }

    /** Handle the key typed event from the text field. */
    public void keyTyped(KeyEvent e) {
    }

    /** Handle the key-pressed event from the text field. */
    public void keyPressed(KeyEvent e) {
        //System.out.println(e);
        if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
        {
            if(nameEntered.length()>0)
                nameEntered = nameEntered.substring(0,nameEntered.length()-1);
        }
        else
            nameEntered = nameEntered + e.getKeyChar();
        blankArea.replaceName(new ShowString("NAME:"+nameEntered,(int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3)+20,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
        blankArea.newRect(EnterIPButton,EnterIPButtonMulti,BackButton,null,null);
        blankArea.reDraw();
    }

    /** Handle the key-released event from the text field. */
    public void keyReleased(KeyEvent e) {
        //System.out.println(e);
    }

    public void reInitBoard() {

        ref = (int)EnterIP_frame.getSize().getWidth();
        EnterIP_frame.pack();

        BackButton = new Rectangle(30,30,60,60,0);
        BackButton.overridecolor = 1;
        BackButton.overridecolorwith = new Color(33,200,200,243);


        EnterIPButton = new Rectangle((int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3),(int)(EnterIP_frame.getSize().getWidth()),(int)(EnterIP_frame.getSize().getHeight()/5),0 );
        EnterIPButton.overridecolor = 1;
        EnterIPButton.overridecolorwith = new Color(33,200,200,243);

        EnterIPButtonMulti = new Rectangle((int)(EnterIP_frame.getSize().getWidth()/2),(int)(2*EnterIP_frame.getSize().getHeight()/3),(int)(EnterIP_frame.getSize().getWidth()/5),(int)(EnterIP_frame.getSize().getHeight()/8),0 );
        EnterIPButtonMulti.overridecolor = 1;
        EnterIPButtonMulti.overridecolorwith = new Color(33,200,200,243);

        blankArea.newRect(EnterIPButton,EnterIPButtonMulti,BackButton,null,null);
   		blankArea.addString(new ShowString("ENTER IP:"+ip_number_string,(int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.BOLD, 30),""));
   		blankArea.addString(new ShowString("JOIN GAME",(int)(EnterIP_frame.getSize().getWidth()/2),(int)(2*EnterIP_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
        blankArea.addString(new ShowString("NAME:",(int)(EnterIP_frame.getSize().getWidth()/2),(int)(EnterIP_frame.getSize().getHeight()/3)+40,new Color(0,255,0,255),new Font("Serif", Font.BOLD, 30),""));
        blankArea.addString(new ShowString("<<",30,30,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));

        EnterIPButton_x1 = (int)(EnterIP_frame.getSize().getWidth()/2) - (int)(EnterIP_frame.getSize().getWidth())/2;
        EnterIPButton_x2 = (int)(EnterIP_frame.getSize().getWidth()/2) + (int)(EnterIP_frame.getSize().getWidth())/2; 
        EnterIPButton_y1 = (int)(EnterIP_frame.getSize().getHeight()/3)-(int)(EnterIP_frame.getSize().getHeight()/8)/2;
        EnterIPButton_y2 = (int)(EnterIP_frame.getSize().getHeight()/3)+(int)(EnterIP_frame.getSize().getHeight()/8)/2;

        EnterIPButton_x1m = (int)(EnterIP_frame.getSize().getWidth()/2) - (int)(EnterIP_frame.getSize().getWidth()/5)/2;
        EnterIPButton_x2m = (int)(EnterIP_frame.getSize().getWidth()/2) + (int)(EnterIP_frame.getSize().getWidth()/5)/2; 
        EnterIPButton_y1m = (int)(2*EnterIP_frame.getSize().getHeight()/3)-(int)(EnterIP_frame.getSize().getHeight()/8)/2;
        EnterIPButton_y2m = (int)(2*EnterIP_frame.getSize().getHeight()/3)+(int)(EnterIP_frame.getSize().getHeight()/8)/2;

        for(int i=0;i<12;i++ )
        {
            int left=(i*ref)/12;
            int right = ((i+1)*ref)/12;
            int top = EnterIPButton_y2;
            int bottom = EnterIPButton_y1m;
            digits.add(new Rectangle((left+right)/2,(top+bottom)/2,right-left,bottom-top,0));
            if(i<10)    
                bhagee.add(new ShowString(Integer.toString(i),(left+right)/2,(top+bottom)/2,new Color(255,255,0,200),new Font("Serif", Font.PLAIN, 50),"" ));
            else if(i==11)
                bhagee.add(new ShowString("<-",(left+right)/2,(top+bottom)/2,new Color(255,255,0,200),new Font("Serif", Font.PLAIN, 50),"" ));
            else if(i==10)
                bhagee.add(new ShowString(".",(left+right)/2,(top+bottom)/2,new Color(255,255,0,200),new Font("Serif", Font.PLAIN, 50) ,""));

            digits.get(i).overridecolor = 1;
            digits.get(i).overridecolorwith =new Color(20,11,231,243);
        }
        blankArea.manyNewRects(digits);
        blankArea.manyNewerRects(bhagee);
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
