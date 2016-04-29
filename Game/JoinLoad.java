import javax.swing.*;

import Game_Engine.*;

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

import java.util.Timer;
import java.util.TimerTask;

public class JoinLoad extends JPanel implements MouseMotionListener, MouseListener{

	private static JFrame JoinLoad_frame; 
	private BlankArea blankArea = new BlankArea(new Color(20,11,231,188));
    private static JoinLoad newContentPane;
    private int sel=0;

    private static String ipAddress = "";
    private static String playerName = "";

    private TimerTask tt = new TimerTask(){
            @Override
            public void run()
            {
                board_b.periodic_network();
                board_b.getConnectedPlayers();
                blankArea.replacePeer1(new ShowString(board_b.connectedPlayers[0],(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3)+20,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
                blankArea.replacePeer2(new ShowString(board_b.connectedPlayers[1],(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3)+40,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
                blankArea.replacePeer3(new ShowString(board_b.connectedPlayers[2],(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3)+60,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
                blankArea.replacePeer4(new ShowString(board_b.connectedPlayers[3],(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3)+80,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));

                if( board_b.requestForHostApproval() )
                {
                     this.cancel();
                     t1.cancel(); 
                     t1.purge(); 
                     (new MouseClickOrMotion(playerName,1,0,board_b,0)).launch(); 
                     JoinLoad_frame.dispose();
                }
                blankArea.reDraw();
            }
        };

    private Board board_b;
    Timer t1;

	Rectangle JoinLoadButton;
	int JoinLoadButton_x1,JoinLoadButton_x2,JoinLoadButton_y1,JoinLoadButton_y2;

	Rectangle JoinLoadButtonMulti;
	int JoinLoadButton_x1m,JoinLoadButton_x2m,JoinLoadButton_y1m,JoinLoadButton_y2m;

	JoinLoad(String ip, String name){
		super(new GridBagLayout());

        if(JoinLoad_frame==null)
            JoinLoad_frame = new JFrame("JoinLoad");

        ipAddress = ip;
        playerName = name;
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

        JoinLoad_frame.pack();

        JoinLoadButton = new Rectangle((int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3),(int)(JoinLoad_frame.getSize().getWidth()/5),(int)(JoinLoad_frame.getSize().getHeight()/8),0 );
        JoinLoadButton.overridecolor = 1;
        JoinLoadButton.overridecolorwith = new Color(33,200,200,243);

		JoinLoadButtonMulti = new Rectangle((int)(JoinLoad_frame.getSize().getWidth()/2),(int)(2*JoinLoad_frame.getSize().getHeight()/3),(int)(JoinLoad_frame.getSize().getWidth()/5),(int)(JoinLoad_frame.getSize().getHeight()/8),0 );
        JoinLoadButtonMulti.overridecolor = 1;
        JoinLoadButtonMulti.overridecolorwith = new Color(33,200,200,243);
		blankArea.newRect(JoinLoadButton,JoinLoadButtonMulti,null,null,null);

        board_b = new Board(20,20,playerName,1,2);
        board_b.setParams(586,586);


        board_b.acceptIP(ipAddress);

        board_b.setGameMode(1);
        t1 = new Timer();
        t1.scheduleAtFixedRate(tt,0,20);


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
    		case 1: break;
    		case 2: break;
    		default: break;
    	}
    }

    public void mouseMoved(MouseEvent e) {
    	if(e.getX()>JoinLoadButton_x1 && e.getX()<JoinLoadButton_x2 && e.getY()>JoinLoadButton_y1 && e.getY()<JoinLoadButton_y2)
    	{
    		JoinLoadButton.overridecolorwith = new Color(33,23,200,243);
        	blankArea.newRect(JoinLoadButton,JoinLoadButtonMulti,null,null,null);
    		sel = 1;
            //blankArea.reDraw();
    	}
    	else if (e.getX()>JoinLoadButton_x1m && e.getX()<JoinLoadButton_x2m && e.getY()>JoinLoadButton_y1m && e.getY()<JoinLoadButton_y2m)
    	{
    		JoinLoadButtonMulti.overridecolorwith = new Color(33,23,200,243);
        	blankArea.newRect(JoinLoadButton,JoinLoadButtonMulti,null,null,null);
    		sel = 2;	
            //blankArea.reDraw();
    	}
    	else
    	{
    		JoinLoadButton.overridecolorwith = new Color(33,200,200,243);
    		JoinLoadButtonMulti.overridecolorwith = new Color(33,200,200,243);
        	blankArea.newRect(JoinLoadButton,JoinLoadButtonMulti,null,null,null);
    		sel = 0;
            //blankArea.reDraw();
    	}
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void reInitBoard() {

        JoinLoad_frame.pack();

        JoinLoadButton = new Rectangle((int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3),(int)(JoinLoad_frame.getSize().getWidth()/5),(int)(JoinLoad_frame.getSize().getHeight()/8),0 );
        JoinLoadButton.overridecolor = 1;
        JoinLoadButton.overridecolorwith = new Color(33,200,200,243);

        JoinLoadButtonMulti = new Rectangle((int)(JoinLoad_frame.getSize().getWidth()/2),(int)(2*JoinLoad_frame.getSize().getHeight()/3),(int)(JoinLoad_frame.getSize().getWidth()/5),(int)(JoinLoad_frame.getSize().getHeight()/8),0 );
        JoinLoadButtonMulti.overridecolor = 1;
        JoinLoadButtonMulti.overridecolorwith = new Color(33,200,200,243);

        blankArea.newRect(JoinLoadButton,JoinLoadButtonMulti,null,null,null);
   		blankArea.addString(new ShowString("WAITING FOR ADMIN:",(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
   		blankArea.addString(new ShowString("PEER1",(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3)+20,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
        blankArea.addString(new ShowString("PEER2",(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3)+40,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
        blankArea.addString(new ShowString("PEER3",(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3)+60,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));
        blankArea.addString(new ShowString("PEER4",(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(JoinLoad_frame.getSize().getHeight()/3)+80,new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));

        blankArea.addString(new ShowString("BE READY",(int)(JoinLoad_frame.getSize().getWidth()/2),(int)(2*JoinLoad_frame.getSize().getHeight()/3),new Color(0,255,0,255),new Font("Serif", Font.PLAIN, 20),""));

        JoinLoadButton_x1 = (int)(JoinLoad_frame.getSize().getWidth()/2) - (int)(JoinLoad_frame.getSize().getWidth()/5)/2;
        JoinLoadButton_x2 = (int)(JoinLoad_frame.getSize().getWidth()/2) + (int)(JoinLoad_frame.getSize().getWidth()/5)/2; 
        JoinLoadButton_y1 = (int)(JoinLoad_frame.getSize().getHeight()/3)-(int)(JoinLoad_frame.getSize().getHeight()/8)/2;
        JoinLoadButton_y2 = (int)(JoinLoad_frame.getSize().getHeight()/3)+(int)(JoinLoad_frame.getSize().getHeight()/8)/2;

        JoinLoadButton_x1m = (int)(JoinLoad_frame.getSize().getWidth()/2) - (int)(JoinLoad_frame.getSize().getWidth()/5)/2;
        JoinLoadButton_x2m = (int)(JoinLoad_frame.getSize().getWidth()/2) + (int)(JoinLoad_frame.getSize().getWidth()/5)/2; 
        JoinLoadButton_y1m = (int)(2*JoinLoad_frame.getSize().getHeight()/3)-(int)(JoinLoad_frame.getSize().getHeight()/8)/2;
        JoinLoadButton_y2m = (int)(2*JoinLoad_frame.getSize().getHeight()/3)+(int)(JoinLoad_frame.getSize().getHeight()/8)/2;

    }

    private /*static*/ void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        JoinLoad_frame = new JFrame("JoinLoad");

        JoinLoad_frame.setResizable(false);

        //Create and set up the content pane.
        newContentPane = this;//new JoinLoad(ipAddress,playerName);
        newContentPane.setOpaque(true); //content panes must be opaque


        //Create and set up the window.
        JoinLoad_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JoinLoad_frame.addComponentListener(new ComponentListener() {
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

        JoinLoad_frame.add(newContentPane);

        //Display the window.
        JoinLoad_frame.pack();
        JoinLoad_frame.setVisible(true);
    }

    public /*static*/  void launch(){
        createAndShowGUI();
    }
}